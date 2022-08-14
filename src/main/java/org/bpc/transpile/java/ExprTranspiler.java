package org.bpc.transpile.java;

import org.bpc.ast.*;

import static org.bpc.transpile.java.Common.identifier;

public class ExprTranspiler {
    public void transpile(StringBuilder dest, Expr expr) {
        if (expr instanceof StringExpr str) {
            dest.append("\"");
            dest.append(str.value());
            dest.append("\"");
        }
        if (expr instanceof NumberExpr num) {
            dest.append(num.value().toString());
        }
        if (expr instanceof BoolExpr bol) {
            dest.append(String.valueOf(bol.value()));
        }
        if (expr instanceof Identifier id) {
            // @TODO support procs
            dest.append("__v__");
            dest.append(identifier(id.name()));
        }
        // @TODO throw error
    }
}
