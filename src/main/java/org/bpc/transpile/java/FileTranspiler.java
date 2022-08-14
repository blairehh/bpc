package org.bpc.transpile.java;

import org.bpc.ast.Parameter;
import org.bpc.ast.Procedure;
import org.bpc.ast.Statement;
import org.bpc.transpile.TranspileFile;

import java.util.Base64;

import static org.bpc.transpile.java.Common.identifier;
import static org.bpc.transpile.java.Common.transpileType;

public class FileTranspiler {

    private final Base64.Encoder b64 = Base64.getEncoder();
    private StatementTranspiler statementTranspiler = new StatementTranspiler();

    public String transpile(TranspileFile file) {
        StringBuilder builder = new StringBuilder();
        builder.append("public static class __f__");
        builder.append(b64(file.path()));
        builder.append(" {\n");
        for (Procedure procedure : file.procedures()) {
            builder.append("public static ");
            builder.append(transpileType(procedure.returnType()));
            builder.append(" __p__");
            builder.append(identifier(procedure.name()));
            builder.append("(");
            for (int i = 0; i < procedure.parameters().size(); i++) {
                final Parameter parameter = procedure.parameters().get(i);
                builder.append(transpileType(parameter.type()));
                builder.append(" __v__");
                builder.append(identifier(parameter.name()));
                if (i < procedure.parameters().size() - 1) {
                    builder.append(",");
                }
            }
            builder.append(") {\n");
            for (Statement statement : procedure.block().statements()) {
                statementTranspiler.transpile(builder, statement);
            }
            builder.append("}\n");
        }
        builder.append("}");
        return builder.toString().trim();
    }

    private String b64(String value) {
        return b64.encodeToString(value.getBytes()).replace("=", "_");
    }
}
