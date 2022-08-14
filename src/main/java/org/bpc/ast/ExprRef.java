package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class ExprRef implements ExprStack {
    private Expr expr;

    public ExprRef(Expr expr) {
        this.expr = expr;
    }

    public ExprRef() {
        this.expr = null;
    }

    public Expr getExpr() {
        return expr;
    }

    public List<Type> getTypesUsed() {
        return expr.getTypesUsed();
    }

    public ExprRef canonicalize(Registry registry) {
        return new ExprRef(this.expr.canonicalize(registry));
    }

    @Override
    public void push(Expr expr) {
        this.expr = expr;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        ExprRef exprRef = (ExprRef) o;

        return Objects.equals(this.expr, exprRef.expr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expr);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ExprRef.class.getSimpleName() + "[", "]")
            .add("expr=" + expr)
            .toString();
    }
}
