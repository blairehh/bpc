package org.bpc.transpile.java;

import org.bpc.transpile.TranspileFile;

import java.util.Base64;

public class TranspileFileToJavaClass {

    private final Base64.Encoder b64 = Base64.getEncoder();

    public String toJavaClass(TranspileFile file) {
        StringBuilder builder = new StringBuilder();
        builder.append("public static class _f_");
        builder.append(b64.encodeToString(file.path().getBytes()));
        builder.append(" {\n");
        builder.append("}");
        return builder.toString().trim();
    }
}
