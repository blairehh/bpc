package org.bpc.compile;

import org.bpc.ast.Identifier;

import java.util.List;
import java.util.Optional;

public record ReferencedProcedure(
    Identifier referenced,
    Identifier canonical,
    List<ReferencedParameter> parameters,
    Optional<ReferencedType> returnType
) {
}
