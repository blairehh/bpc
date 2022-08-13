package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.ast.Namespace;
import org.bpc.ast.Type;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ConflictingDeclaration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Registry {
    public record TypeRegistree(Identifier canonical, Identifier referenced) { }
    public record ProcedureRegistree(Identifier canonical, Identifier referenced) {}

    public static TypeRegistree type(Type type) {
        return new TypeRegistree(
            new Identifier(type.name(), type.namespace()),
            new Identifier(type.name(), type.namespace())
        );
    }

    public static TypeRegistree type(Identifier canonical, Identifier referenced) {
        return new TypeRegistree(canonical, referenced);
    }

    public static ProcedureRegistree procedure(Identifier canonical, Identifier referenced) {
        return new ProcedureRegistree(canonical, referenced);
    }

    private final Map<Namespace, Module> modules;
    private final Set<TypeRegistree> types;
    private final Set<ProcedureRegistree> procedures;

    public Registry(Map<Namespace, Module> modules, Set<TypeRegistree> types, Set<ProcedureRegistree> procedures) {
        this.modules = modules;
        this.types = types;
        this.procedures = procedures;
    }

    public Registry(SDK sdk, Set<TypeRegistree> types, Set<ProcedureRegistree> procedures) {
        this.modules = sdk.modules()
            .stream()
            .collect(Collectors.toMap(Module::namespace, (module) -> module));
        this.types = Stream.concat(sdk.baseRegistry().types.stream(), types.stream())
            .collect(Collectors.toSet());
        this.procedures = Stream.concat(sdk.baseRegistry().procedures.stream(), procedures.stream())
            .collect(Collectors.toSet());
    }

    public Optional<Module> getModule(Namespace namespace) {
        return Optional.ofNullable(this.modules.get(namespace));
    }

    public Optional<Identifier> getReferencedTypeFromCanonical(Identifier canonical) {
        return this.types
            .stream()
            .filter((type) -> type.canonical().equals(canonical))
            .map(TypeRegistree::referenced)
            .findFirst();
    }

    public boolean hasReferencedType(Identifier identifier) {
        return this.types
            .stream()
            .anyMatch((type) -> type.referenced().equals(identifier));
    }

    public Optional<CompilationError> referenceCanonicalTypeAs(Identifier canonical, Identifier referenced) {
        final boolean exists = this.hasReferencedType(referenced);
        if (exists) {
            return Optional.of(new ConflictingDeclaration(referenced));
        }
        this.types.add(new TypeRegistree(canonical, referenced));
        return Optional.empty();
    }

    public boolean hasReferencedProcedure(Identifier identifier) {
        return this.procedures
            .stream()
            .anyMatch((type) -> type.referenced().equals(identifier));
    }

    public Optional<CompilationError> referenceCanonicalProcedureAs(Identifier canonical, Identifier referenced) {
        final boolean exists = this.hasReferencedProcedure(referenced);
        if (exists) {
            return Optional.of(new ConflictingDeclaration(referenced));
        }
        this.procedures.add(new ProcedureRegistree(canonical, referenced));
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

        Registry register = (Registry) o;

        return Objects.equals(this.types, register.types)
            && Objects.equals(this.procedures, register.procedures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types, procedures);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Registry.class.getSimpleName() + "[", "]")
            .add("types=" + types)
            .add("procedures=" + procedures)
            .toString();
    }
}
