package org.bpc.ast;

import java.util.List;

public record Identifier(String name, List<String> namespace) implements Expr {
    public Identifier(String name) {
        this(name, List.of());
    }
}
