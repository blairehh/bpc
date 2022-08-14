package org.bpc.transpile.java;

import org.bpc.ast.Parameter;
import org.bpc.ast.Procedure;
import org.bpc.ast.Type;
import org.bpc.transpile.TranspileFile;

import java.util.Base64;

public class TranspileFileToJavaClass {

    private final Base64.Encoder b64 = Base64.getEncoder();

    public String toJavaClass(TranspileFile file) {
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
            builder.append("}\n");
        }
        builder.append("}");
        return builder.toString().trim();
    }

    private String b64(String value) {
        return b64.encodeToString(value.getBytes()).replace("=", "_");
    }

    private String identifier(String value) {
        return value.replace("-", "_");
    }

    private String transpileType(Type type) {
        if (type == null) {
            return "void";
        }
        StringBuilder value = new StringBuilder();
        value.append("__t__");
        for (String segment : type.namespace().segments()) {
            value.append(segment);
            value.append("__");
        }
        value.append(identifier(type.name()));
        return value.toString();
    }
}
