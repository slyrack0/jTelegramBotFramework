package org.slyrack.telegrambots.state;

import org.slyrack.telegrambots.Model;
import org.springframework.lang.NonNull;

public interface State {
    long userId();
    @NonNull
    String name();
    @NonNull
    Model model();
}
