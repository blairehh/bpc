package org.bpc.transpile.java;

import org.bpc.ast.Type;

public class Common {
    public static String identifier(String value) {
        return value.replace("-", "_");
    }

    public static String transpileType(Type type) {
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
