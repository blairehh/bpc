package org.bpc.ast;

import java.util.List;

public record BoolExpr(boolean value) implements Expr {
    @Override
    public List<Type> getTypesUsed() {
        return List.of(new Type("binary"));
    }
}
