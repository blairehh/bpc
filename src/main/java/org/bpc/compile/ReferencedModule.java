package org.bpc.compile;

import org.bpc.ast.Namespace;

import java.util.List;

public record ReferencedModule(
    Namespace reference,
    Namespace canonical,
    List<ReferencedProcedure> procedures,
    List<ReferencedType> types
) {
}
