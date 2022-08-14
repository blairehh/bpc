package org.bpc.ast;

import org.bpc.compile.Identifier;

public record ImportedProcedureIdentifier(String name, Namespace namespace) implements Identifier {
}
