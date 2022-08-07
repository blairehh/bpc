package org.bpc.ast;

import java.util.List;

public record Namespace(List<String> segments) {
    public Namespace(String value) {
        this(List.of(value));
    }

    public Namespace(String... values) {
        this(List.of(values));
    }

    public Namespace() {
        this(List.of());
    }
}
