package org.bpc.compile.checks;

import org.bpc.ast.Namespace;
import org.bpc.ast.Use;
import org.bpc.compile.CodeFile;
import org.bpc.compile.CompileJob;
import org.bpc.compile.Module;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ModuleNotFound;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class MissingModuleCheck {
    public List<CompilationError> check(CompileJob job) {
        final Function<CodeFile, Stream<CompilationError>> checkFile = (file) -> file.uses()
            .stream()
            .flatMap((use) -> missingModule(use, job.sdk().modules()).map((ns) -> (CompilationError)new ModuleNotFound(ns)));

        return job.files()
            .stream()
            .flatMap(checkFile)
            .toList();
    }


    private Stream<Namespace> missingModule(Use use, List<Module> modules) {
        for (Module module : modules) {
            if (use.namespace().equals(module.namespace())) {
                return Stream.of();
            }
        }
        return Stream.of(use.namespace());
    }
}
