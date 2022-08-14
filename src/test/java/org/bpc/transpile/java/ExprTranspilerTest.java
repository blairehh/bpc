package org.bpc.transpile.java;

import org.bpc.ast.BoolExpr;
import org.bpc.ast.Identifier;
import org.bpc.ast.NumberExpr;
import org.bpc.ast.StringExpr;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExprTranspilerTest {

    ExprTranspiler transpiler = new ExprTranspiler();

    @Test
    void testTranspileString() {
        StringBuilder builder = new StringBuilder();
        transpiler.transpile(builder, new StringExpr("Hello World"));
        assertThat(builder.toString()).isEqualTo("\"Hello World\"");
    }

    @Test
    void testTranspileNumber() {
        StringBuilder builder = new StringBuilder();
        transpiler.transpile(builder, new NumberExpr(556.4));
        assertThat(builder.toString()).isEqualTo("556.4");
    }

    @Test
    void testTranspileBoolean() {
        StringBuilder builder = new StringBuilder();
        transpiler.transpile(builder, new BoolExpr(true));
        assertThat(builder.toString()).isEqualTo("true");
    }

    @Test
    void testTranspileIdentifier() {
        StringBuilder builder = new StringBuilder();
        transpiler.transpile(builder, new Identifier("max-value"));
        assertThat(builder.toString()).isEqualTo("__v__max_value");
    }
}