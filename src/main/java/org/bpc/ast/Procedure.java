package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public record Procedure(String name, Optional<Type> returnType, List<Parameter> parameters, Block block) {
    public Procedure(String name, Type returnType) {
        this(name, Optional.ofNullable(returnType), new ArrayList<>(), new Block());
    }

    public List<Type> getTypesUsed() {
        final Stream<Stream<Type>> types =  Stream.of(
            returnType.stream(),
            parameters.stream().flatMap((parameter) -> parameter.getTypesUsed().stream()),
            block.getTypesUsed().stream()
        );

        return types.flatMap(Function.identity()).toList();
    }

    public Procedure canonicalize(Registry registry) {
        return new Procedure(
            this.name,
            this.returnType.map((type) -> registry.getCanonicalTypeFromReference(type).orElseThrow()),
            parameters.stream()
                .map((parameter) -> parameter.canonicalize(registry))
                .toList(),
            block.canonicalize(registry)
        );
    }
}
