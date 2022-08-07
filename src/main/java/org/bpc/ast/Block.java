package org.bpc.ast;

import java.util.ArrayList;
import java.util.List;


public record Block(List<Statement> statements) {

    public Block(Statement... statements) {
        this(List.of(statements));
    }

    public Block() {
        this(new ArrayList<>());
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

}
