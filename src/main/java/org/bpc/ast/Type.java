package org.bpc.ast;

import org.bpc.compile.Identifier;

public record Type(String name, Namespace namespace) implements Identifier {
    public Type(String name) {
        this(name, new Namespace());
    }
}
