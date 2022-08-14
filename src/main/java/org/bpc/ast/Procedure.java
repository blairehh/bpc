package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public record Procedure(ProcedureSignature signature, Block block) {
    public Procedure(ProcedureSignature signature) {
        this(signature, new Block());
    }

    public List<Type> getTypesUsed() {
        final Stream<Stream<Type>> types =  Stream.of(
            signature.getTypesUsed().stream(),
            block.getTypesUsed().stream()
        );

        return types.flatMap(Function.identity()).toList();
    }

    public Procedure canonicalize(Registry registry) {
        return new Procedure(
            this.signature.canonicalize(registry),
            block.canonicalize(registry)
        );
    }
}
