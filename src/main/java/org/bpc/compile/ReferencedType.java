package org.bpc.compile;

import org.bpc.ast.Identifier;

public record ReferencedType(Identifier referenced, Identifier canonical) {
}
