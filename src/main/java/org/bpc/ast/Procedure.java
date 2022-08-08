package org.bpc.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

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
}
