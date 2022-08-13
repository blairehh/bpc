package org.bpc.transpile;

import org.bpc.ast.Procedure;

import java.util.List;

public record TranspileFile(List<Procedure> procedures) {
}
