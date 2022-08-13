package org.bpc.ast;

import org.bpc.compile.Registry;

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

    @Override
    public Expr canonicalize(Registry registry) {
        return this;
    }
}
