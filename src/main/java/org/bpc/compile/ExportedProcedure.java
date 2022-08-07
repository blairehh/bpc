package org.bpc.compile;

import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.List;

public record ExportedProcedure(String name, List<Parameter> parameters, Type returnType) {
    public ExportedProcedure(String name, List<Parameter> parameters) {
        this(name, parameters, null);
    }
}
