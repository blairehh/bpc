package org.bpc.ast;

public record ReturnStatement(ExprRef expr) implements Statement, Assignable {
    public ReturnStatement(Expr expr) {
        this(new ExprRef(expr));
    }

    @Override
    public void assign(Expr expr) {
        this.expr.assign(expr);
    }
}
