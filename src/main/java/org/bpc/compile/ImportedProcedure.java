package org.bpc.compile;

import org.bpc.ast.Identifier;

import java.util.List;
import java.util.Optional;

// @TODO remove this file?
public record ImportedProcedure(
    Identifier referenced,
    Identifier canonical,
    List<ImportedParameter> parameters,
    Optional<ImportedType> returnType
) {
}
