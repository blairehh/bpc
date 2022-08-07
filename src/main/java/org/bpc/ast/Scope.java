package org.bpc.ast;

import java.util.ArrayList;
import java.util.List;

// TODO rename to block
public record Scope(List<Statement> statements) {

    public Scope(Statement... statements) {
        this(List.of(statements));
    }

    public Scope() {
        this(new ArrayList<>());
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

}
