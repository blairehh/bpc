package org.bpc.ast;

public record Identifier(String name, Namespace namespace) implements Expr {
    public Identifier(String name) {
        this(name, new Namespace());
    }
}
