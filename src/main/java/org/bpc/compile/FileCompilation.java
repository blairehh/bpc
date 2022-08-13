package org.bpc.compile;

import org.bpc.compile.errors.CompilationError;

import java.util.Set;

public sealed interface FileCompilation {
    record Ok() implements FileCompilation {}
    record Errors(Set<CompilationError> errors) implements FileCompilation {}
}
