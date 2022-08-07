package org.bpc.ast;

import org.bpc.syntax.SyntaxCompilation;
import org.bpc.syntax.SyntaxCompiler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bpc.Util.read;

class SourceFileTest {

    @Test
    void testEmptyProcWithNoParameters() {
        String code = read("empty_proc_with_no_parameters.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "bar",
                    null,
                    List.of(),
                    new Block()
                )
            )
        );
    }


    @Test
    void testEmptyProcedureWithReturnType() {
        String code = read("empty_proc_with_return.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "main",
                    "int",
                    List.of(new Parameter("foo", "int")),
                    new Block()
                )
            )
        );
    }

    @Test
    void testEmptyProcedureWithoutReturnType() {
        String code = read("empty_proc_without_return.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "main",
                    null,
                    List.of(new Parameter("foo", "int")),
                    new Block()
                )
            )
        );
    }

    @Test
    void testProcedureWithVariablesAndCalls() {
        String code = read("proc_with_variables_and_calls.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "foo",
                    null,
                    List.of(new Parameter("bar", "int")),
                    new Block(
                        new VariableDeclaration(
                            "baz",
                            "dec"
                        ),
                        new ProcedureCall("doh")
                    )
                )
            )
        );
    }

    @Test
    void testVariableWithNumberAssignment() {
        String code = read("variable_with_number_assignment.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "foo-bar",
                    "int",
                    List.of(new Parameter("fo", "fo")),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            "int",
                            new NumberExpr("9")
                        )
                    )
                )
            )
        );
    }

    @Test
    void testVariableWithLargeNumberAssignment() {
        String code = read("variable_with_large_number_assignment.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "foo-bar",
                    "int",
                    List.of(new Parameter("fo", "fo")),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            "int",
                            new NumberExpr("1234567890")
                        )
                    )
                )
            )
        );
    }


    @Test
    void testVariableWithPrimitiveTypeAssignments() {
        String code = read("variable_primitive_type_assignments.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "foo-bar",
                    "int",
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            "int",
                            new NumberExpr("9")
                        ),
                        new VariableDeclaration(
                            "active",
                            "binary",
                            new BoolExpr("true")
                        ),
                        new VariableDeclaration(
                            "letter",
                            "char",
                            new CharExpr("C")
                        ),
                        new VariableDeclaration(
                            "text",
                            "char",
                            new StringExpr("hello")
                        )
                    )
                )
            )
        );
    }

    @Test
    void testProcedureCallWithArguments() {
        String code = read("procedure_call_with_arguments.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "foo",
                    null,
                    List.of(),
                    new Block(
                        new ProcedureCall(
                            "bar",
                            List.of(
                                new NumberExpr("1"),
                                new BoolExpr("true"),
                                new StringExpr("oi")
                            )
                        )
                    )
                )
            )
        );
    }

    @Test
    void testProcedureCallAsExpr() {
        String code = read("procedure_call_as_expr.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);

        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "foo",
                    null,
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "bar",
                            "int",
                            new ProcedureCall(
                                "baz",
                                List.of(
                                    new ProcedureCall("doh", List.of())
                                )
                            )
                        )
                    )
                )
            )
        );
    }

    @Test
    void testProcedureCallAsExprWithLiteralArguments() {
        String code = read("procedure_call_as_expr_with_literal_arguments.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);

        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "foo",
                    null,
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "bar",
                            "int",
                            new ProcedureCall(
                                "baz",
                                List.of(
                                    new ProcedureCall(
                                        "doh",
                                        List.of(
                                            new NumberExpr("987"),
                                            new ProcedureCall("something", List.of(new StringExpr("here"))),
                                            new BoolExpr("true")
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );
    }


    @Test
    void testVariableWithNegativeNumberAssignment() {
        String code = read("variable_with_negative_number_assignment.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "func",
                    "int",
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            "int",
                            new NumberExpr("-97")
                        )
                    )
                )
            )
        );
    }

    @Test
    void testVariableWithDecimalNumberAssignment() {
        String code = read("variable_with_decimal_number_assignment.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "func",
                    "int",
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            "int",
                            new NumberExpr("97.6866")
                        )
                    )
                )
            )
        );
    }

    @Test
    void testVariableWithNegativeDecimalNumberAssignment() {
        String code = read("variable_with_negative_decimal_number_assignment.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                new Procedure(
                    "func",
                    "int",
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            "int",
                            new NumberExpr("-97.97")
                        )
                    )
                )
            )
        );
    }


}