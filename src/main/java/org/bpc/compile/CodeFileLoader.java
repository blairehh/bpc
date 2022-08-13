package org.bpc.compile;

import org.bpc.ast.Use;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ModuleNotFound;

import java.util.Set;

public class CodeFileLoader {
    public Set<CompilationError> load(CodeFile file, ModuleRegistry moduleRegistry, IdentityRegister register) {
        for (Use use : file.uses()) {
            final Module module = moduleRegistry.getModule(use.namespace())
                .orElse(null);
            if (module == null) {
                return Set.of(new ModuleNotFound(use.namespace()));
            }
            new ImportLoader().reference(module, use.namespace(), register);
        }
        return Set.of();
    }
}
