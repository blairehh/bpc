package org.bpc.compile;

import org.bpc.compile.checks.MissingModuleCheck;
import org.bpc.compile.checks.UnknownTypeCheck;
import org.bpc.compile.errors.CompilationError;

import java.util.Set;

public class Compiler {

    public Set<CompilationError> compile(CompileJob job) {
        Set<CompilationError> errors = Set.of();

        errors = new MissingModuleCheck().check(job);
        if (!errors.isEmpty()) {
            return errors;
        }

        errors = new UnknownTypeCheck().check(job);
        if (!errors.isEmpty()) {
            return errors;
        }

        return errors;
    }
}
