package org.bpc.compile;

import org.bpc.ast.*;

import java.util.List;

public record Module(
    Namespace namespace,
    List<ExportedProcedure> procedures,
    List<Type> types
) {
}
