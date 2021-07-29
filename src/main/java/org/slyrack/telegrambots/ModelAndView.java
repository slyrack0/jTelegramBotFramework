package org.slyrack.telegrambots;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ModelAndView {

    @NonNull
    private String viewName;
    @NonNull
    private Model model;

    public ModelAndView(@NonNull final String viewName) {
        this(viewName, new Model());
    }

    public ModelAndView(@NonNull final String viewName,
                        @NonNull final String attributeName,
                        @NonNull final Object attributeValue) {
        this(viewName, new Model(attributeName, attributeValue));
    }
}
