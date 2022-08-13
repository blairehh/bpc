package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.ast.Type;
import org.bpc.ast.Import;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ModuleNotFound;
import org.bpc.compile.errors.TypeUnknown;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FileCompiler {
    public FileCompilation load(CodeFile file, Registry register) {
        for (Import _import : file.imports()) {
            final Module module = register.getModule(_import.namespace())
                .orElse(null);
            if (module == null) {
                return new FileCompilation.Errors(Set.of(new ModuleNotFound(_import.namespace())));
            }
            new ImportLoader().load(module, _import.namespace(), register);
        }
        Set<CompilationError> errors = new HashSet<>();
        for (Type type : file.getTypesUsed()) {
            final boolean knownType = register.hasReferencedType(new Identifier(type.name(), type.namespace()));
            if (!knownType) {
                errors.add(new TypeUnknown(type));
            }
        }
        if (!errors.isEmpty()) {
            return new FileCompilation.Errors(errors.stream().collect(Collectors.toSet()));
        }
        return new FileCompilation.Ok();
    }
}
