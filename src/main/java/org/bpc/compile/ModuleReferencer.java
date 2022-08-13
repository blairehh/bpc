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
            module.procedures().stream().map((proc) -> this.referenced(module, referencedAs, proc, register)).toList(),
            module.types().stream().map((type) -> this.referenced(referencedAs, type)).toList()
        );
    }

    private boolean isModuleType(Module module, Type type) {
        return module.types().stream()
            .anyMatch((moduleType) -> moduleType.equals(type));
    }


    private ReferencedType referenced(Namespace referencedAs, Type type) {
        return new ReferencedType(
            new Identifier(type.name(), referencedAs),
            new Identifier(type.name(), type.namespace())
        );
    }

    private ReferencedParameter referenced(Module module, Namespace referencedAs, Parameter parameter, IdentityRegister register) {
        final Identifier canonical = new Identifier(parameter.type().name(), parameter.type().namespace());
        final Identifier referenced = isModuleType(module, parameter.type())
            ? new Identifier(parameter.type().name(), referencedAs)
            : new Identifier(parameter.type().name(), parameter.type().namespace()); // @TODO check identity register
        return new ReferencedParameter(parameter.name(), new ReferencedType(referenced, canonical));
    }

    private ReferencedProcedure referenced(Module module, Namespace referencedAs, ExportedProcedure proc, IdentityRegister register) {
        return new ReferencedProcedure(
            new Identifier(proc.name(), referencedAs),
            new Identifier(proc.name(), module.namespace()),
            proc.parameters().stream().map((param) -> referenced(module, referencedAs, param, register)).toList(),
            Optional.empty()
        );
    }

}
