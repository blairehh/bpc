package org.bpc.compile;

import org.bpc.ast.Namespace;

import java.util.List;

public record Module(Namespace namespace, List<ExportedProcedure> procedures) {
}
