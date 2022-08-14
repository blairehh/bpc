package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;

public record BoolExpr(boolean value) implements Expr {
    @Override
    public List<Type> getTypesUsed() {
        return List.of(new Type("bool"));
    }

    @Override
    public Expr canonicalize(Registry registry) {
        return this;
    }
}
