package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

// @TODO replace with record
public final class VariableDeclaration implements Statement, Assignable {
    private final String name;
    private final Type type;
    private Expr expr;

    public VariableDeclaration(String name, Type type) {
        this.name = name;
        this.type = type;
        this.expr = null;
    }

    public VariableDeclaration(String name, Type type, Expr expr) {
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

    public Type type() {
        return type;
    }

    public Expr expr() {
        return expr;
    }

    @Override
    public List<Type> getTypesUsed() {
        return Stream.concat(Stream.of(this.type), this.expr.getTypesUsed().stream())
            .toList();
    }

    @Override
    public Statement canonicalizeStatement(Registry registry) {
        return new VariableDeclaration(
            this.name,
            registry.getCanonicalTypeFromReference(this.type).orElseThrow(),
            this.expr.canonicalize(registry)
        );
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
