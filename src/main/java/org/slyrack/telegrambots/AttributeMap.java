package org.slyrack.telegrambots;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

public interface AttributeMap {

    /**
     * Overrides old value.
     * @param name name of attribute
     * @param value any object attribute value
     */
    void setAttribute(@NonNull final String name, @NonNull final Object value);

    void removeAttribute(@NonNull final String name);

    boolean containsAttribute(@NonNull final String name);

    @Nullable
    Object getAttribute(@NonNull final String name);

    @NonNull
    @JsonIgnore // idk why jackson use this method
    Collection<String> getAttributeKeys();
}
