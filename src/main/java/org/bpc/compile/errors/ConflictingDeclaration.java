package org.bpc.compile.errors;

import org.bpc.compile.Identifier;

public record ConflictingDeclaration(Identifier identifier) implements CompilationError {
}
