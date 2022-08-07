package org.bpc.ast;

import java.util.ArrayList;
import java.util.List;

public record Procedure(String name, String returnType, List<Parameter> parameters, Scope scope) {
    public Procedure(String name, String returnType) {
        this(name, returnType, new ArrayList<>(), new Scope());
    }
}