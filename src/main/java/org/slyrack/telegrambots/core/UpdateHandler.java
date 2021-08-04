package org.slyrack.telegrambots.core;

import lombok.extern.slf4j.Slf4j;
import org.slyrack.telegrambots.ModelAndView;
import org.slyrack.telegrambots.StatefulModelAndView;
import org.slyrack.telegrambots.annotations.Command;
import org.slyrack.telegrambots.core.holders.CommandHolder;
import org.slyrack.telegrambots.core.holders.MiddleHandlerHolder;
import org.slyrack.telegrambots.core.holders.ViewHolder;
import org.slyrack.telegrambots.exceptions.HandlerException;
import org.slyrack.telegrambots.session.Session;
import org.slyrack.telegrambots.session.SessionIdGenerator;
import org.slyrack.telegrambots.session.SessionManager;
import org.slyrack.telegrambots.state.State;
import org.slyrack.telegrambots.state.StateManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class UpdateHandler {

    private final List<CommandHolder> commands;
    private final Map<String, ViewHolder> views;
    private final List<MiddleHandlerHolder> middleHandlers;

    private final boolean stateManagement;
    private final boolean sessionManagement;

    private final StateManager stateManager; // may be null if stateManagement is false
    private final SessionIdGenerator sessionIdGenerator; // may be null if sessionManagement is false
    private final SessionManager sessionManager; // may be null if sessionManagement is false

    public UpdateHandler(final List<CommandHolder> commands,
                         final Map<String, ViewHolder> views,
                         final List<MiddleHandlerHolder> middleHandlers,
                         final boolean stateManagement,
                         final boolean sessionManagement,
                         final StateManager stateManager,
                         final SessionIdGenerator sessionIdGenerator,
                         final SessionManager sessionManager) {

        if (stateManagement && stateManager == null)
            throw new IllegalStateException("State management is enabled but not found StateManager bean");
        if (sessionManagement) {
            if (sessionIdGenerator == null)
                throw new IllegalStateException("Session management is enabled but not fount SessionIdGenerator bean");
            if (sessionManager == null)
                throw new IllegalStateException("Session management is enabled but not fount SessionManager bean");
        }

        this.commands = commands;
        this.views = views;
        this.middleHandlers = middleHandlers;
        this.stateManagement = stateManagement;
        this.sessionManagement = sessionManagement;
        this.stateManager = stateManager;
        this.sessionIdGenerator = sessionIdGenerator;
        this.sessionManager = sessionManager;
    }

    public void handleUpdate(@NonNull final Update update,
                             @NonNull final AbsSender absSender) throws HandlerException {

        final Optional<Long> userId = userId(update);

        State state = null;
        if (stateManagement)
            state = userId.map(stateManager::getState)
                    .orElse(null);

        Session session = null;
        if (sessionManagement) {
            final Optional<String> sessionId = sessionIdGenerator.generateId(update);
            if (sessionId.isPresent()) {
                final String id = sessionId.get();
                session = sessionManager.getSession(id)
                        .orElseGet(() -> sessionManager.newSession(id));

                if (!session.isAlive()) {
                    sessionManager.delete(session);
                    session = sessionManager.newSession(id);
                }

                session.touch();
            }
        }


        try {
            // invoke middleware handlers
            for (final MiddleHandlerHolder handler : middleHandlers)
                handler.handle(update, absSender, session, state != null ? state.model() : null);


            final List<CommandHolder> commandsToExecute = collectCommands(update, state);
            for (final CommandHolder command : commandsToExecute) {
                final Optional<ModelAndView> commandResult = command.handle(
                        update, absSender, session,
                        state != null ? state.model() : null
                );

                if (!commandResult.isPresent())
                    continue;

                final ModelAndView modelAndView = commandResult.get();
                final ViewHolder viewHolder = views.get(modelAndView.getViewName());
                if (viewHolder == null)
                    log.warn("View with name {} not exists", modelAndView.getViewName());
                else
                    viewHolder.handle(update, absSender, session, modelAndView.getModel());


                if (modelAndView instanceof StatefulModelAndView) {
                    if (stateManagement && userId.isPresent())
                        stateManager.saveState(
                                userId.get(),
                                ((StatefulModelAndView) modelAndView).getStateName(),
                                modelAndView.getModel()
                        );
                    else if (stateManagement)
                        log.warn("Can't save state because can't get user id");
                } else if(stateManagement && userId.isPresent())
                    stateManager.removeState(userId.get());
            }
        } catch (final Exception e) {
            throw new HandlerException(e);
        } finally {
            if (sessionManagement && session != null)
                sessionManager.update(session);
        }
    }

    private List<CommandHolder> collectCommands(@NonNull final Update update,
                                                @Nullable final State state) {

        Stream<CommandHolder> commandsStream = commands.stream();
        if (stateManagement) { // filter commands by state if stateManagement enabled
            if (state == null) // if state no present - filter by Command.NO_STATE
                commandsStream = commandsStream.filter(command -> command.stateCompatibility(Command.NO_STATE));
            else
                commandsStream = commandsStream.filter(command -> command.stateCompatibility(state.name()));
        }

        // filter by CommandHolder.canHandle() and collect filtered commands
        final List<CommandHolder> filteredCommands = commandsStream.filter(command -> command.canHandle(update))
                .collect(Collectors.toList());

        // collect all exclusive commands
        final List<CommandHolder> exclusiveCommands = filteredCommands.stream()
                .filter(CommandHolder::isExclusive)
                .collect(Collectors.toList());

        // if exclusive commands not found - return all filtered
        if (exclusiveCommands.isEmpty())
            return filteredCommands;

        // if exclusive commands count not equals 1 - throw error
        if (exclusiveCommands.size() != 1)
            throw new IllegalStateException("Exclusive command must be 1 in suitable commands");

        // return only 1 exclusive command
        return exclusiveCommands;
    }

    private static Optional<Long> userId(final Update update) {
        final Optional<User> user;
        if (update.hasMessage())
            user = Optional.ofNullable(update.getMessage().getFrom());
        else if (update.hasCallbackQuery())
            user = Optional.ofNullable(update.getCallbackQuery().getFrom());
        else if (update.hasInlineQuery())
            user = Optional.of(update.getInlineQuery().getFrom());
        else if (update.hasChannelPost())
            user = Optional.ofNullable(update.getChannelPost().getFrom());
        else if (update.hasEditedChannelPost())
            user = Optional.ofNullable(update.getEditedChannelPost().getFrom());
        else if (update.hasEditedMessage())
            user = Optional.ofNullable(update.getEditedMessage().getFrom());
        else if (update.hasChosenInlineQuery())
            user = Optional.of(update.getChosenInlineQuery().getFrom());
        else if (update.hasShippingQuery())
            user = Optional.ofNullable(update.getShippingQuery().getFrom());
        else if (update.hasPreCheckoutQuery())
            user = Optional.ofNullable(update.getPreCheckoutQuery().getFrom());
        else if (update.hasPollAnswer())
            user = Optional.ofNullable(update.getPollAnswer().getUser());
        else
            user = Optional.empty();

        return user.map(User::getId);
    }
}
