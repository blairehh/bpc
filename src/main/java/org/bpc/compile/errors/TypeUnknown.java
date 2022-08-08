package org.bpc.compile.errors;

import org.bpc.ast.Type;

public record TypeUnknown(Type type) implements CompilationError {
}
