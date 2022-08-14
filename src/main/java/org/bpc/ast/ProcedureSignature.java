package org.bpc.ast;

import java.util.List;
import java.util.Optional;

public record ProcedureSignature(String name, List<Parameter> parameters, Optional<Type> returnType) {
    public ProcedureSignature(String name, List<Parameter> parameters) {
        this(name, parameters, Optional.empty());
    }
}
