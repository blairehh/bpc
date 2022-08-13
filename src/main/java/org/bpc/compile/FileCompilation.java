package org.bpc.compile;

import org.bpc.compile.errors.CompilationError;
import org.bpc.transpile.TranspileFile;

import java.util.Set;

public sealed interface FileCompilation {
    record Ok(TranspileFile file) implements FileCompilation {}
    record Errors(Set<CompilationError> errors) implements FileCompilation {}
}
