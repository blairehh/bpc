package org.bpc.ast;

import java.util.List;

public record Parameter(String name, Type type) {
    public List<Type> getTypesUsed() {
        return List.of(type);
    }
}
