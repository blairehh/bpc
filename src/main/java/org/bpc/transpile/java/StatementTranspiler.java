package org.bpc.transpile.java;

import org.bpc.ast.Statement;
import org.bpc.ast.VariableDeclaration;

import static org.bpc.transpile.java.Common.transpileType;

public class StatementTranspiler {

    private final ExprTranspiler expr = new ExprTranspiler();

    public void transpile(StringBuilder dest, Statement statement) {
        if (statement instanceof VariableDeclaration vd) {
            dest.append(transpileType(vd.type()));
            dest.append(" __v__");
            dest.append(vd.name());
            dest.append(" = ");
            expr.transpile(dest, vd.expr());
            dest.append(";\n");
        }
        // @TODO throw error
    }
}
