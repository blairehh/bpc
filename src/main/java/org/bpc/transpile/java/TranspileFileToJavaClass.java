package org.bpc.transpile.java;

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
        return "";
    }
}
