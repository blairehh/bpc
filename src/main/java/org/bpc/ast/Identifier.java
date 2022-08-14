package org.bpc.ast;

public sealed interface Identifier permits Parameter, Reference, Type {
    String name();
    Namespace namespace();
}