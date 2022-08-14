package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public record ProcedureSignature(String name, List<Parameter> parameters, Optional<Type> returnType) {
    public ProcedureSignature(String name, List<Parameter> parameters) {
        this(name, parameters, Optional.empty());
    }

    public ProcedureSignature(String name, Type type) {
        this(name, new ArrayList<>(), Optional.ofNullable(type));
    }

    public List<Type> getTypesUsed() {
        final Stream<Stream<Type>> types =  Stream.of(
            returnType.stream(),
            parameters.stream().flatMap((parameter) -> parameter.getTypesUsed().stream())
        );

        return types.flatMap(Function.identity()).toList();
    }

    public ProcedureSignature canonicalize(Registry registry) {
        return new ProcedureSignature(
            this.name,
            parameters.stream()
                .map((parameter) -> parameter.canonicalize(registry))
                .toList(),
            this.returnType.map((type) -> registry.getCanonicalTypeFromReference(type).orElseThrow())
        );
    }
}
