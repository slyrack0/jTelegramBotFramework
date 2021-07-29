package org.slyrack.telegrambots;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Model implements AttributeMap {

    private final Map<String, Object> attributeMap;

    public Model() {
        this.attributeMap = new LinkedHashMap<>();
    }

    public Model(final String attributeName, final Object attributeValue) {
        this();
        setAttribute(attributeName, attributeValue);
    }


    @Override
    public void setAttribute(@NonNull final String name, @NonNull final Object value) {
        attributeMap.put(name, value);
    }

    @Override
    public void removeAttribute(@NonNull final String name) {
        attributeMap.remove(name);
    }

    @Override
    public boolean containsAttribute(@NonNull final String name) {
        return attributeMap.containsKey(name);
    }

    @Nullable
    @Override
    public Object getAttribute(@NonNull final String name) {
        return attributeMap.get(name);
    }

    @NonNull
    @Override
    public Collection<String> getAttributeKeys() {
        return attributeMap.keySet();
    }
}
