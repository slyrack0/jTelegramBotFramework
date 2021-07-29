package org.slyrack.telegrambots.state;

import lombok.*;
import org.slyrack.telegrambots.Model;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SimpleState implements State {

    @NonNull
    private Long userId;
    @NonNull
    private String name;
    @NonNull
    private Model model;

    @Override
    public long userId() {
        return userId;
    }

    @NonNull
    @Override
    public String name() {
        return name;
    }

    @NonNull
    @Override
    public Model model() {
        return model;
    }
}
