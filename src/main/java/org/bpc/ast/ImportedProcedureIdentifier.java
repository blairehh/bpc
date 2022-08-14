package org.bpc.ast;

public record ImportedProcedureIdentifier(String name, Namespace namespace) implements Identifier {
}
