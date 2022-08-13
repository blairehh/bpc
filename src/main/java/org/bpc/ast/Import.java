package org.bpc.ast;

public record Import(Namespace namespace) {
    public Import(String value) {
        this(new Namespace(value));
    }
}
