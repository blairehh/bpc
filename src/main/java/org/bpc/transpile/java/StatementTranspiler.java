package org.bpc.transpile.java;

import org.bpc.ast.ProcedureCall;
import org.bpc.ast.ReturnStatement;
import org.bpc.ast.Statement;
import org.bpc.ast.VariableDeclaration;

import static org.bpc.transpile.java.Common.identifier;
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
            return;
        }
        if (statement instanceof ReturnStatement rs) {
            dest.append("return");
            if (rs.expr() == null) {
                dest.append(";\n");
                return;
            }
            dest.append(" ");
            expr.transpile(dest, rs.expr().getExpr());
            dest.append(";\n");
            return;
        }
        if (statement instanceof ProcedureCall pc) {
            dest.append(identifier("p", pc.name()));
            dest.append("(");
            for (int i = 0; i < pc.arguments().size(); i++) {
                expr.transpile(dest, pc.arguments().get(i));
                if (i < pc.arguments().size() - 1) {
                    dest.append(",");
                }
            }
            dest.append(");\n");
        }
        // @TODO throw error
    }
}
