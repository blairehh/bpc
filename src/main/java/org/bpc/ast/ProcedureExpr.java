package org.bpc.ast;

import org.bpc.compile.Identifier;
import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;

public record ProcedureExpr(String name, Namespace namespace, List<Expr> arguments) implements Expr, ExprStack, Identifier {

    public ProcedureExpr(String name, List<Expr> arguments) {
        this(name, new Namespace(), arguments);
    }

    public ProcedureExpr(String name) {
        this(name, new Namespace(), new ArrayList<>());
    }

    public ProcedureExpr(String name, Namespace namespace) {
        this(name,  namespace, new ArrayList<>());
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
            this.namespace,
            this.arguments().stream().map((expr) -> expr.canonicalize(registry)).toList()
        );
    }
}
