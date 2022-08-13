package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.Optional;

// @TODO how to hande conflicts or not founds
public class ImportLoader {
    public void reference(Module module, Namespace referencedAs, Registry register) {
        module.procedures().forEach((proc) -> this.procedure(module, referencedAs, proc, register));
        module.types().forEach((type) -> this.type(referencedAs, type, register));
    }

    private boolean isModuleType(Module module, Type type) {
        return module.types().stream()
            .anyMatch((moduleType) -> moduleType.equals(type));
    }


    private ImportedType type(Namespace referencedAs, Type type, Registry register) {
        final Identifier referenced = new Identifier(type.name(), referencedAs);
        final Identifier canonical = new Identifier(type.name(), type.namespace());
        register.referenceCanonicalTypeAs(canonical, referenced);
        return new ImportedType(referenced, canonical);
    }

    private ImportedParameter parameter(Module module, Namespace referencedAs, Parameter parameter, Registry register) {
        final Identifier canonical = new Identifier(parameter.type().name(), parameter.type().namespace());
        if (isModuleType(module, parameter.type())) {
            final Identifier referenced = new Identifier(parameter.type().name(), referencedAs);
            return new ImportedParameter(parameter.name(), new ImportedType(referenced, canonical));
        }
        final Identifier referenced = register.getReferencedTypeFromCanonical(canonical)
            .orElseThrow(); // @TODO better error handling
        return new ImportedParameter(parameter.name(), new ImportedType(referenced, canonical));
    }

    private ImportedProcedure procedure(Module module, Namespace referencedAs, ExportedProcedure proc, Registry register) {
        final Identifier referenced = new Identifier(proc.name(), referencedAs);
        final Identifier canonical = new Identifier(proc.name(), module.namespace());

        final ImportedProcedure procedure = new ImportedProcedure(
            referenced,
            canonical,
            proc.parameters().stream().map((param) -> parameter(module, referencedAs, param, register)).toList(),
            Optional.ofNullable(proc.returnType())
                .map((type) -> procedureReturn(module, referencedAs, type, register))
        );
        register.referenceCanonicalProcedureAs(canonical, referenced, procedure);
        return procedure;
    }

    private ImportedType procedureReturn(Module module, Namespace referencedAs, Type type, Registry register) {
        final Identifier canonical = new Identifier(type.name(), type.namespace());
        if (isModuleType(module, type)) {
            return new ImportedType(new Identifier(type.name(), referencedAs), canonical);
        }
        final Identifier referenced = register.getReferencedTypeFromCanonical(canonical)
            .orElseThrow(); // @TODO better error handling
        return new ImportedType(referenced, canonical);
    }

}
