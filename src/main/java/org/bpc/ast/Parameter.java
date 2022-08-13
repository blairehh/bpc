package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;

public record Parameter(String name, Type type) {
    public List<Type> getTypesUsed() {
        return List.of(type);
    }

    public Parameter canonicalize(Registry registry) {
        return new Parameter(name, registry.getCanonicalTypeFromReference(type).orElseThrow());
    }
}
