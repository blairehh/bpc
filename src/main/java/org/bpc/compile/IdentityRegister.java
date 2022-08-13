package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ConflictingDeclaration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public IdentityRegister(SDK sdk, Map<Identifier, Identifier> types, Map<Identifier, Identifier> procedures) {
        this.types = Stream.concat(sdk.baseRegistry().types.entrySet().stream(), types.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.procedures = Stream.concat(sdk.baseRegistry().procedures.entrySet().stream(), procedures.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Optional<Identifier> getReferencedTypeFromCanonical(Identifier canonical) {
        return Optional.ofNullable(this.types.get(canonical));
    }

    public Optional<CompilationError> referenceCanonicalTypeAs(Identifier canonical, Identifier referenced) {
        final Identifier existing = this.types.get(referenced);
        if (existing != null) {
            return Optional.of(new ConflictingDeclaration(referenced));
        }
        this.types.put(canonical, referenced);
        return Optional.empty();
    }

    public Optional<CompilationError> referenceCanonicalProcedureAs(Identifier canonical, Identifier referenced) {
        final Identifier existing = this.procedures.get(referenced);
        if (existing != null) {
            return Optional.of(new ConflictingDeclaration(referenced));
        }
        this.procedures.put(canonical, referenced);
        return Optional.empty();
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
