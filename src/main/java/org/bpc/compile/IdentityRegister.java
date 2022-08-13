package org.bpc.compile;

import org.bpc.ast.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class IdentityRegister {
    private final Map<Identifier, Identifier> map;

    public IdentityRegister() {
        this.map = new HashMap<>();
    }

    public IdentityRegister(Map.Entry<Identifier, Identifier>... items) {
        this.map = Arrays.stream(items)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Optional<Identifier> getReferencedFromCanonical(Identifier canonical) {
        return Optional.ofNullable(this.map.get(canonical));
    }

    public void referenceCanonicalAs(Identifier canonical, Identifier referenced) {
        this.map.put(canonical, referenced);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        IdentityRegister that = (IdentityRegister) o;

        return Objects.equals(this.map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
