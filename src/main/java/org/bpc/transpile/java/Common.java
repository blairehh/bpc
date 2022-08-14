package org.bpc.transpile.java;

import org.bpc.compile.Identifier;
import org.bpc.ast.Type;

public class Common {
    public static String identifier(String value) {
        return value.replace("-", "_");
    }

    public static String identifier(String kind, Identifier identifier) {
        StringBuilder value = new StringBuilder();
        value.append("__");
        value.append(kind);
        value.append("__");
        for (String segment : identifier.namespace().segments()) {
            value.append(segment);
            value.append("__");
        }
        value.append(identifier(identifier.name()));
        return value.toString();
    }

    public static String transpileType(Type type) {
        return identifier("t", type);
    }
}
