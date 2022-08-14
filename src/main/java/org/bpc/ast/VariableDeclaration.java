package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;
import java.util.stream.Stream;

public record VariableDeclaration(String name, Type type, ExprRef expr) implements Statement, ExprStack {

    public VariableDeclaration(String name, Type type) {
        this(name, type, new ExprRef());
    }

    public VariableDeclaration(String name, Type type, Expr expr) {
        this(name, type, new ExprRef(expr));
    }

    @Override
    public void push(Expr expr) {
        this.expr.push(expr);
    }

    @Override
    public List<Type> getTypesUsed() {
        return Stream.concat(Stream.of(this.type), this.expr.getTypesUsed().stream())
            .toList();
    }

    @Override
    public Statement canonicalize(Registry registry) {
        return new VariableDeclaration(
            this.name,
            registry.getCanonicalTypeFromReference(this.type).orElseThrow(),
            this.expr.canonicalize(registry)
        );
    }
}
