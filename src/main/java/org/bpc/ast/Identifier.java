package org.bpc.ast;

import java.util.List;

// @TODO could this me an interface
public record Identifier(String name, Namespace namespace) implements Expr {
    public Identifier(String name) {
        this(name, new Namespace());
    }

    @Override
    public List<Type> getTypesUsed() {
        return List.of();
    }
}
