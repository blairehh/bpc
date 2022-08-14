package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;

import static org.bpc.Misc.number;

public record NumberExpr(Number value) implements LiteralExpr {
    public NumberExpr(String value) {
        this(number(value));
    }

    public NumberExpr(Number value) {
        this.value = number(value.toString());
    }

    @Override
    public List<Type> getTypesUsed() {
        return List.of(); // this can be many types but are all global types so no need to return anyway
    }

    @Override
    public Expr canonicalize(Registry registry) {
        return this;
    }
}
