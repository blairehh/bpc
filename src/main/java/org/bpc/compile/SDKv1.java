package org.bpc.compile;

import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.*;
import java.util.stream.Collectors;

public class SDKv1 implements SDK {
    @Override
    public List<Module> modules() {
        return List.of(
            new Module(
                new Namespace("console"),
                List.of(
                    new ExportedProcedure("println", List.of(new Parameter("value", new Type("string"))))
                ),
                List.of()
            )
        );
    }

    @Override
    public List<Type> types() {
        return List.of(
            new Type("int"),
            new Type("string"),
            new Type("dec"),
            new Type("binary"),
            new Type("char"),
            new Type("num")
        );
    }

    @Override
    public IdentityRegister baseIdentityRegistry() {
        final Set<IdentityRegister.TypeRegistree> types = this.types()
            .stream()
            .map(IdentityRegister::type)
            .collect(Collectors.toSet());
        return new IdentityRegister(new HashSet<>(types), new HashSet<>());
    }

    @Override
    public ModuleRegistry baseModuleRegistry() {
        final Map<Namespace, Module> mods = this.modules()
            .stream()
            .collect(Collectors.toMap(Module::namespace, (module) -> module));
        return new ModuleRegistry(new HashMap<>(mods));
    }
}
