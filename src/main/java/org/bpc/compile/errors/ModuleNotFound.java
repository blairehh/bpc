package org.bpc.compile.errors;

import org.bpc.ast.Namespace;

public record ModuleNotFound(Namespace namespace) implements CompilationError {
}
