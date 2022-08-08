package org.bpc.ast;

import java.util.List;

public record ReturnStatement(ExprRef expr) implements Statement, Assignable {
    public ReturnStatement(Expr expr) {
        this(new ExprRef(expr));
    }

    @Override
    public void assign(Expr expr) {
        this.expr.assign(expr);
    }

    @Override
    public List<Type> getTypesUsed() {
        return this.expr.getTypesUsed();
    }
}
