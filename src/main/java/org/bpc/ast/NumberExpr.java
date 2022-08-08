package org.bpc.ast;

import java.util.List;

import static org.bpc.Misc.number;

public record NumberExpr(Number value) implements Expr {
    public NumberExpr(String value) {
        this(number(value));
    }

    public NumberExpr(Number value) {
        this.value = number(value.toString());
    }

    @Override
    public List<Type> getTypesUsed() {
        return List.of(new Type("num"));
    }
}
