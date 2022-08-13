package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.Optional;

public class ImportLoader {
    public ReferencedModule reference(Module module, Namespace referencedAs, IdentityRegister register) {
        return new ReferencedModule(
            referencedAs,
            module.namespace(),
            module.procedures().stream().map((proc) -> this.procedure(module, referencedAs, proc, register)).toList(),
            module.types().stream().map((type) -> this.type(referencedAs, type, register)).toList()
        );
    }

    private boolean isModuleType(Module module, Type type) {
        return module.types().stream()
            .anyMatch((moduleType) -> moduleType.equals(type));
    }


    private ReferencedType type(Namespace referencedAs, Type type, IdentityRegister register) {
        final Identifier referenced = new Identifier(type.name(), referencedAs);
        final Identifier canonical = new Identifier(type.name(), type.namespace());
        register.referenceCanonicalTypeAs(canonical, referenced);
        return new ReferencedType(referenced, canonical);
    }

    private ReferencedParameter parameter(Module module, Namespace referencedAs, Parameter parameter, IdentityRegister register) {
        final Identifier canonical = new Identifier(parameter.type().name(), parameter.type().namespace());
        if (isModuleType(module, parameter.type())) {
            final Identifier referenced = new Identifier(parameter.type().name(), referencedAs);
            return new ReferencedParameter(parameter.name(), new ReferencedType(referenced, canonical));
        }
        final Identifier referenced = register.getReferencedTypeFromCanonical(canonical)
            .orElseThrow(); // @TODO better error handling
        return new ReferencedParameter(parameter.name(), new ReferencedType(referenced, canonical));
    }

    private ReferencedProcedure procedure(Module module, Namespace referencedAs, ExportedProcedure proc, IdentityRegister register) {
        final Identifier referenced = new Identifier(proc.name(), referencedAs);
        final Identifier canonical = new Identifier(proc.name(), module.namespace());
        register.referenceCanonicalProcedureAs(canonical, referenced);
        return new ReferencedProcedure(
            referenced,
            canonical,
            proc.parameters().stream().map((param) -> parameter(module, referencedAs, param, register)).toList(),
            Optional.ofNullable(proc.returnType())
                .map((type) -> procedureReturn(module, referencedAs, type, register))
        );
    }

    private ReferencedType procedureReturn(Module module, Namespace referencedAs, Type type, IdentityRegister register) {
        final Identifier canonical = new Identifier(type.name(), type.namespace());
        if (isModuleType(module, type)) {
            return new ReferencedType(new Identifier(type.name(), referencedAs), canonical);
        }
        final Identifier referenced = register.getReferencedTypeFromCanonical(canonical)
            .orElseThrow(); // @TODO better error handling
        return new ReferencedType(referenced, canonical);
    }

}
