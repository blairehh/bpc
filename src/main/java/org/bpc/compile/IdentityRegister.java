package org.bpc.compile;

import org.bpc.ast.Identifier;

import java.util.Map;
import java.util.Optional;

public class IdentityRegister {
    private final Map<Identifier, Identifier> map;

    public IdentityRegister() {
        this.map = Map.of();
    }

    public Optional<Identifier> getReferencedFromCanonical(Identifier canonical) {
        return Optional.ofNullable(this.map.get(canonical));
    }
}
