package org.bpc.ast;

import java.util.ArrayList;
import java.util.List;

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
}
