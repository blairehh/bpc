package org.bpc.compile;

import org.bpc.ast.Identifier;

public record ImportedType(Identifier referenced, Identifier canonical) {
}
