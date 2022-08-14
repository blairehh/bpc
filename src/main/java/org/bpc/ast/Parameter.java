package org.bpc.ast;

import org.bpc.compile.Identifier;
import org.bpc.compile.Registry;

import java.util.List;

public record Parameter(String name, Type type) implements Identifier {
    public List<Type> getTypesUsed() {
        return List.of(type);
    }

    @Override
    public Namespace namespace() {
        return new Namespace();
    }

    public Parameter canonicalize(Registry registry) {
        return new Parameter(name, registry.getCanonicalTypeFromReference(type).orElseThrow());
    }
}
