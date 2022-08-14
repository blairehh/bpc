package org.bpc.compile.errors;

import org.bpc.ast.Identifier;

public record ConflictingDeclaration(Identifier identifier) implements CompilationError {
}
