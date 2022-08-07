package org.bpc.ast;

import java.util.Objects;
import java.util.StringJoiner;

// @TODO replace with dodging record
public final class VariableDeclaration implements Statement, Assignable {
    private final String name;
    private final String type;
    private Expr expr;

    public VariableDeclaration(String name, String type) {
        this.name = name;
        this.type = type;
        this.expr = null;
    }

    public VariableDeclaration(String name, String type, Expr expr) {
        this.name = name;
        this.type = type;
        this.expr = expr;
    }

    @Override
    public void assign(Expr expr) {
        this.expr = expr;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        VariableDeclaration that = (VariableDeclaration) o;

        return Objects.equals(this.name, that.name)
            && Objects.equals(this.type, that.type)
            && Objects.equals(this.expr, that.expr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, expr);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VariableDeclaration.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .add("type='" + type + "'")
            .add("expr=" + expr)
            .toString();
    }
}
