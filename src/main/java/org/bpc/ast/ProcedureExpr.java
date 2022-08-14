package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;

public record ProcedureExpr(Reference reference, List<Expr> arguments) implements Expr, ExprStack {

    public ProcedureExpr(Reference reference) {
        this(reference, new ArrayList<>());
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
            this.reference,
            this.arguments().stream().map((expr) -> expr.canonicalize(registry)).toList()
        );
    }
}
