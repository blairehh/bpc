package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;

// @TODO should this be an expr and statement
public record ProcedureCall(Identifier name, List<Expr> arguments) implements Statement, Assignable, Expr {
    public ProcedureCall(Identifier name) {
        this(name,  new ArrayList<>());
    }

    @Override
    public void assign(Expr expr) {
        this.arguments.add(expr);
    }

    @Override
    public List<Type> getTypesUsed() {
        return this.arguments
            .stream()
            .flatMap((argument) -> argument.getTypesUsed().stream())
            .toList();
    }

    @Override
    public Statement canonicalizeStatement(Registry registry) {
        return new ProcedureCall(
            this.name,
            this.arguments().stream().map((expr) -> expr.canonicalize(registry)).toList()
        );
    }

    @Override
    public Expr canonicalize(Registry registry) {
        return new ProcedureCall(
            this.name,
            this.arguments().stream().map((expr) -> expr.canonicalize(registry)).toList()
        );
    }
}
