package org.slyrack.telegrambots.session;

import org.slyrack.telegrambots.exceptions.SessionExistsException;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySessionManager implements SessionManager {

    private final ConcurrentHashMap<String, Session> sessionMap;
    private final EnumMap<EventType, List<SessionEventListener>> listeners;
    private final long sessionTtlMillis;

    public InMemorySessionManager(final long sessionTtlMillis) {
        this.sessionMap = new ConcurrentHashMap<>();
        this.listeners = new EnumMap<>(EventType.class);
        this.sessionTtlMillis = sessionTtlMillis;
    }

    @NonNull
    @Override
    public Session newSession(@NonNull final String sessionId) {
        final Session session = sessionMap.compute(sessionId, (key, currentVal) -> {
            if (currentVal != null)
                throw new SessionExistsException(
                        String.format("Session with id [%s] already exists", key)
                );

            return new SimpleSession(key, sessionTtlMillis);
        });

        invokeListener(EventType.SESSION_NEW, session);
        return session;
    }

    @Override
    public Optional<Session> getSession(@NonNull final String sessionId) {
        final Optional<Session> sessionOpt = Optional.ofNullable(sessionMap.get(sessionId));

        sessionOpt.ifPresent(session -> invokeListener(EventType.SESSION_REQUEST, session));
        return sessionOpt;
    }

    @Override
    public void update(@NonNull final Session session) {
        sessionMap.put(session.id(), session);
        invokeListener(EventType.SESSION_UPDATE, session);
    }

    @Override
    public void delete(@NonNull final Session session) {
        sessionMap.remove(session.id());
        invokeListener(EventType.SESSION_DELETE, session);
    }

    @Override
    public void addEventListener(@NonNull final EventType eventType, @NonNull final SessionEventListener listener) {
        synchronized (this) {
            this.listeners.compute(
                    eventType,
                    (ignored, listeners) -> {
                        if (listeners == null)
                            listeners = new ArrayList<>();

                        listeners.add(listener);
                        return listeners;
                    }
            );
        }
    }


    private void invokeListener(@NonNull final EventType eventType, @NonNull final Session session) {
        final List<SessionEventListener> listeners = this.listeners.get(eventType);
        if(listeners == null || listeners.isEmpty())
            return;

        listeners.forEach(listener -> listener.event(eventType, session));
    }
}
