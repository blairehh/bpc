package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;

public record ProcedureExpr(Identifier name, List<Expr> arguments) implements Expr, ExprStack {

    public ProcedureExpr(Identifier name) {
        this(name,  new ArrayList<>());
    }

    @Override
    public void push(Expr expr) {
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
    public ProcedureExpr canonicalize(Registry registry) {
        return new ProcedureExpr(
            this.name,
            this.arguments().stream().map((expr) -> expr.canonicalize(registry)).toList()
        );
    }
}
