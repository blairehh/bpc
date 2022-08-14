package org.bpc.ast;

import org.bpc.compile.Identifier;
import org.bpc.compile.Registry;

import java.util.List;

public record Reference(String name, Namespace namespace) implements Identifier, Expr {

    public Reference(String name) {
        this(name, new Namespace());
    }

    @Override
    public List<Type> getTypesUsed() {
        return List.of();
    }

    @Override
    public Reference canonicalize(Registry registry) {
        return this;
    }
}
