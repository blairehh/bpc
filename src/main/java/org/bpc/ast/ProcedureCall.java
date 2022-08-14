package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;

public record ProcedureCall(ProcedureExpr expr) implements Statement {
    public ProcedureCall(String name, Namespace namespace) {
        this(new ProcedureExpr(name,  namespace, new ArrayList<>()));
    }

    @Override
    public List<Type> getTypesUsed() {
        return this.expr().getTypesUsed();
    }

    @Override
    public Statement canonicalize(Registry registry) {
        return new ProcedureCall(this.expr().canonicalize(registry));
    }
}
