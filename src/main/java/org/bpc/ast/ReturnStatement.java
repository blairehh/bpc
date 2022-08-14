package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;

public record ReturnStatement(ExprRef expr) implements Statement, ExprStack {
    public ReturnStatement(Expr expr) {
        this(new ExprRef(expr));
    }

    @Override
    public void push(Expr expr) {
        this.expr.push(expr);
    }

    @Override
    public List<Type> getTypesUsed() {
        return this.expr.getTypesUsed();
    }

    @Override
    public Statement canonicalize(Registry registry) {
        return new ReturnStatement(this.expr.canonicalize(registry));
    }
}
