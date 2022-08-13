package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.Optional;

public class ModuleReferencer {
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
        final Identifier referenced = isModuleType(module, parameter.type())
            ? new Identifier(parameter.type().name(), referencedAs)
            : new Identifier(parameter.type().name(), parameter.type().namespace()); // @TODO check identity register
        return new ReferencedParameter(parameter.name(), new ReferencedType(referenced, canonical));
    }

    private ReferencedProcedure procedure(Module module, Namespace referencedAs, ExportedProcedure proc, IdentityRegister register) {
        return new ReferencedProcedure(
            new Identifier(proc.name(), referencedAs),
            new Identifier(proc.name(), module.namespace()),
            proc.parameters().stream().map((param) -> parameter(module, referencedAs, param, register)).toList(),
            Optional.ofNullable(proc.returnType())
                .map((type) -> procedureReturn(module, referencedAs, type))
        );
    }

    private ReferencedType procedureReturn(Module module, Namespace referencedAs, Type type) {
        if (isModuleType(module, type)) {
            return new ReferencedType(
                new Identifier(type.name(), referencedAs),
                new Identifier(type.name(), type.namespace())
            );
        }
        // TODO check IdentityRegister
        final Identifier identifier = new Identifier(type.name(), type.namespace());
        return new ReferencedType(identifier, identifier);
    }

}
