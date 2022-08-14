package org.bpc.ast;

public sealed interface Identifier permits ImportedProcedureIdentifier, Parameter, Reference, Type {
    String name();
    Namespace namespace();
}