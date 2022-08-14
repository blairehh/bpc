package org.bpc.ast;

public record Type(String name, Namespace namespace) implements Identifier {
    public Type(String name) {
        this(name, new Namespace());
    }
}
