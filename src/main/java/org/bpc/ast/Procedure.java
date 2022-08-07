package org.bpc.ast;

import java.util.ArrayList;
import java.util.List;

public record Procedure(String name, Type returnType, List<Parameter> parameters, Block block) {
    public Procedure(String name, Type returnType) {
        this(name, returnType, new ArrayList<>(), new Block());
    }
}
