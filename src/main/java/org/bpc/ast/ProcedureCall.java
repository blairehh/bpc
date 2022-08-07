package org.bpc.ast;

import java.util.ArrayList;
import java.util.List;

public record ProcedureCall(String name, List<Expr> arguments) implements Statement, Assignable, Expr {
    public ProcedureCall(String name) {
        this(name, new ArrayList<>());
    }

    @Override
    public void assign(Expr expr) {
        this.arguments.add(expr);
    }
}
