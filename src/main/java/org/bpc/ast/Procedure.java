package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

// @TODO make returnType Optional
public record Procedure(String name, Type returnType, List<Parameter> parameters, Block block) {
    public Procedure(String name, Type returnType) {
        this(name, returnType, new ArrayList<>(), new Block());
    }

    public List<Type> getTypesUsed() {
        final Stream<Stream<Type>> types =  Stream.of(
            Stream.of(returnType),
            parameters.stream().flatMap((parameter) -> parameter.getTypesUsed().stream()),
            block.getTypesUsed().stream()
        );

        return types.flatMap(Function.identity()).toList();
    }

    public Procedure canonicalize(Registry registry) {
        return new Procedure(
            this.name,
            registry.getCanonicalTypeFromReference(this.returnType)
                .orElseThrow(),
            parameters.stream()
                .map((parameter) -> parameter.canonicalize(registry))
                .toList(),
            block.canonicalize(registry)
        );
    }
}
