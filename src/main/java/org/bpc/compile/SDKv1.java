package org.bpc.compile;

import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.ProcedureSignature;
import org.bpc.ast.Type;

import java.util.*;
import java.util.stream.Collectors;

import static org.bpc.Primitives.*;

public class SDKv1 implements SDK {
    @Override
    public List<Module> modules() {
        return List.of(
            new Module(
                new Namespace("console"),
                List.of(
                    new ProcedureSignature("println", List.of(new Parameter("value", new Type("string"))))
                ),
                List.of()
            )
        );
    }

    @Override
    public List<Type> types() {
        return List.of(
            INT,
            STRING,
            DEC,
            BOOL,
            NUM
        );
    }

    @Override
    public Registry baseRegistry() {
        final Set<Registry.TypeRegistree> types = this.types()
            .stream()
            .map(Registry::type)
            .collect(Collectors.toSet());
        final Map<Namespace, Module> mods = this.modules()
            .stream()
            .collect(Collectors.toMap(Module::namespace, (module) -> module));
        return new Registry(mods, new HashSet<>(types), new HashSet<>());
    }
}
