package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    public IdentityRegister baseRegistry() {
        final Function<Type, Identifier> toIdentifier = (type) -> new Identifier(type.name(), type.namespace());
        final Map<Identifier, Identifier> types = this.types()
            .stream()
            .collect(Collectors.toMap(toIdentifier, toIdentifier));
        return new IdentityRegister(new HashMap<>(types), new HashMap<>());
    }
}
