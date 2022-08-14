package org.bpc.ast;

public sealed interface Identifier permits ImportedProcedureIdentifier, Parameter, ProcedureExpr, Reference, Type {
    String name();
    Namespace namespace();
}