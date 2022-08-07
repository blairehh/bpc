package org.bpc.compile;

import org.bpc.compile.checks.MissingModuleCheck;
import org.bpc.compile.errors.CompilationError;

import java.util.List;

public class Compiler {

    public List<CompilationError> compile(CompileJob job) {
        List<CompilationError> errors = List.of();

        errors = new MissingModuleCheck().check(job);
        if (!errors.isEmpty()) {
            return errors;
        }

        return errors;
    }
}
