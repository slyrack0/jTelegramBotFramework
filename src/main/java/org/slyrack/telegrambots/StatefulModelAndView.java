package org.slyrack.telegrambots;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StatefulModelAndView extends ModelAndView {

    @NonNull // intellij warn but all ok
    private String stateName;

    public StatefulModelAndView(@NonNull final String stateName,
                                @NonNull final String viewName,
                                @NonNull final Model model) {
        super(viewName, model);
        this.stateName = stateName;
    }

    public StatefulModelAndView(@NonNull final String stateName,
                                @NonNull final String viewName,
                                @NonNull final String attributeName,
                                @NonNull final Object attributeValue) {
        this(stateName, viewName, new Model(attributeName, attributeValue));
    }

    public StatefulModelAndView(@NonNull final String stateName,
                                @NonNull final String viewName) {
        this(stateName, viewName, new Model());
    }
}
