package org.bpc.compile;

import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.List;
import java.util.Optional;

public record ExportedProcedure(String name, List<Parameter> parameters, Optional<Type> returnType) {
    public ExportedProcedure(String name, List<Parameter> parameters) {
        this(name, parameters, Optional.empty());
    }
}
