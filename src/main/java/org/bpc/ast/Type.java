package org.bpc.ast;

import java.util.List;

public record Type(String name, List<String> namespace) {
    public Type(String name) {
        this(name, List.of());
    }
}
