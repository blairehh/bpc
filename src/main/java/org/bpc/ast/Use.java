package org.bpc.ast;

public record Use(Namespace namespace) {
    public Use(String value) {
        this(new Namespace(value));
    }
}
