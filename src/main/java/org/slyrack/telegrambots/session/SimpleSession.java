package org.slyrack.telegrambots.session;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSession implements Session {

    private String sessionId;
    private Instant lastAccessTime;
    private long ttlMillis;
    private boolean stopped;
    private Map<String, Object> attributeMap;

    public SimpleSession(final String sessionId, final long ttlMillis) {
        this.sessionId = sessionId;
        this.lastAccessTime = Instant.now();
        this.ttlMillis = ttlMillis;
        this.stopped = false;
        this.attributeMap = new LinkedHashMap<>();
    }

    @NonNull
    @Override
    public String id() {
        return sessionId;
    }

    @Override
    public boolean isAlive() {
        if (stopped)
            return false;

        return (System.currentTimeMillis() - lastAccessTime.toEpochMilli()) < ttlMillis;
    }

    @Override
    public void stop() {
        this.stopped = true;
    }

    @Override
    public void touch() {
        this.lastAccessTime = Instant.now();
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
