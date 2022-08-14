package org.bpc.ast;

import org.bpc.compile.Registry;

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

    public List<Type> getTypesUsed() {
        return this.statements.stream()
            .flatMap((statement) -> statement.getTypesUsed().stream())
            .toList();
    }

    public Block canonicalize(Registry registry) {
        return new Block(this.statements.stream().map((statement) -> statement.canonicalize(registry)).toList());
    }
}
