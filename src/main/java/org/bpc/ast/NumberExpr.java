package org.bpc.ast;

import static org.bpc.Misc.number;

public record NumberExpr(Number value) implements Expr {
    public NumberExpr(String value) {
        this(number(value));
    }

    public NumberExpr(Number value) {
        this.value = number(value.toString());
    }
}
