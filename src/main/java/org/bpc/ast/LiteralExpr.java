package org.bpc.ast;

public sealed interface LiteralExpr extends Expr permits BoolExpr, StringExpr, NumberExpr {
}
