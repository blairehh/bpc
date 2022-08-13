package org.bpc.compile;

import org.bpc.ast.Identifier;

import java.util.*;

public class IdentityRegister {
    private final Map<Identifier, Identifier> types;
    private final Map<Identifier, Identifier> procedures;

    public IdentityRegister() {
        this.types = new HashMap<>();
        this.procedures = new HashMap<>();
    }

    public IdentityRegister(Map<Identifier, Identifier> types, Map<Identifier, Identifier> procedures) {
        this.types = types;
        this.procedures = procedures;
    }

    public Optional<Identifier> getReferencedTypeFromCanonical(Identifier canonical) {
        return Optional.ofNullable(this.types.get(canonical));
    }

    public void referenceCanonicalTypeAs(Identifier canonical, Identifier referenced) {
        this.types.put(canonical, referenced);
    }

    public void referenceCanonicalProcedureAs(Identifier canonical, Identifier referenced) {
        this.procedures.put(canonical, referenced);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        IdentityRegister register = (IdentityRegister) o;

        return Objects.equals(this.types, register.types)
            && Objects.equals(this.procedures, register.procedures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types, procedures);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", IdentityRegister.class.getSimpleName() + "[", "]")
            .add("types=" + types)
            .add("procedures=" + procedures)
            .toString();
    }
}
