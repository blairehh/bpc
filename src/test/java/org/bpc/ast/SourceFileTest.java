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
                    new Type("int"),
                    List.of(new Parameter("foo", new Type("int"))),
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
                    List.of(new Parameter("foo", new Type("int"))),
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
                    List.of(new Parameter("bar", new Type("int"))),
                    new Block(
                        new VariableDeclaration(
                            "baz",
                            new Type("dec"),
                            new NumberExpr(0)
                        ),
                        new ProcedureCall(new Identifier("doh"))
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
                    new Type("int"),
                    List.of(new Parameter("fo", new Type("fo"))),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            new Type("int"),
                            new NumberExpr(9)
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
                    new Type("int"),
                    List.of(new Parameter("fo", new Type("fo"))),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            new Type("int"),
                            new NumberExpr(1234567890)
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
                    new Type("int"),
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            new Type("int"),
                            new NumberExpr(9)
                        ),
                        new VariableDeclaration(
                            "active",
                            new Type("binary"),
                            new BoolExpr(true)
                        ),
                        new VariableDeclaration(
                            "letter",
                            new Type("char"),
                            new CharExpr("C")
                        ),
                        new VariableDeclaration(
                            "text",
                            new Type("char"),
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
                            new Identifier("bar"),
                            List.of(
                                new NumberExpr(1),
                                new BoolExpr(false),
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
                            new Type("int"),
                            new ProcedureCall(
                                new Identifier("baz"),
                                List.of(
                                    new ProcedureCall(new Identifier("doh"), List.of())
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
                            new Type("int"),
                            new ProcedureCall(
                                new Identifier("baz"),
                                List.of(
                                    new ProcedureCall(
                                        new Identifier("doh"),
                                        List.of(
                                            new NumberExpr(987),
                                            new ProcedureCall(new Identifier("something"), List.of(new StringExpr("here"))),
                                            new BoolExpr(true)
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
                    new Type("int"),
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            new Type("int"),
                            new NumberExpr(-97)
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
                    new Type("int"),
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            new Type("int"),
                            new NumberExpr(97.6866)
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
                    new Type("int"),
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            new Type("int"),
                            new NumberExpr(-97.97)
                        )
                    )
                )
            )
        );
    }

    @Test
    void testProcWithReturnStatement() {
        String code = read("proc_with_return_statement.bp");
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
                        new ReturnStatement(
                            new NumberExpr(5)
                        )
                    )
                )
            )
        );
    }

    @Test
    void testProcThatJustCallsAnotherProc() {
        String code = read("proc_that_just_calls_proc.bp");
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
                    List.of(),
                    new Block(
                        new ProcedureCall(new Identifier("foo"))
                    )
                )
            )
        );
    }


    @Test
    void testMultipleProcs() {
        String code = read("multiple_procs.bp");
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
                    List.of(),
                    new Block(
                        new ProcedureCall(new Identifier("foo"))
                    )
                ),
                new Procedure(
                    "foo",
                    null,
                    List.of(),
                    new Block(
                        new ProcedureCall(new Identifier("bar"))
                    )
                ),
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
    void testPassDeclaredVariableToProc() {
        String code = read("pass_declared_variable_to_proc.bp");
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
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "number",
                            new Type("int"),
                            new NumberExpr(8)
                        ),
                        new ProcedureCall(new Identifier("print"), List.of(new Identifier("number")))
                    )
                )
            )
        );
    }

    @Test
    void testSimpleProcWithNamespacedReturn() {
        String code = read("simple_proc_with_namespaced_return.bp");
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
                    new Type("tcp-server", new Namespace("sock")),
                    List.of(),
                    new Block(
                        new ProcedureCall(new Identifier("run"))
                    )
                )
            )
        );
    }

    @Test
    void testProcCallAndVariableNamespaced() {
        String code = read("proc_call_and_variable_namespaced.bp");
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
                    List.of(),
                    new Block(
                        new ProcedureCall(
                            new Identifier("println", new Namespace("console")),
                            List.of(
                                new Identifier("name", new Namespace("os")),
                                new ProcedureCall(new Identifier("bar", new Namespace("foo")))
                            )
                        )
                    )
                )
            )
        );
    }

    @Test
    void testVariableDeclarationWithNamespacedType() {
        String code = read("variable_declaration_with_namespaced_type.bp");
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
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "fd",
                            new Type("file-descriptor", new Namespace("io")),
                            new NumberExpr(0)
                        )
                    )
                )
            )
        );
    }

    @Test
    void testProcWithNamespacedParameters() {
        String code = read("proc_with_namespaced_parameters.bp");
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
                    List.of(
                        new Parameter("fd", new Type("file-descriptor", new Namespace("io"))),
                        new Parameter("mode", new Type("int"))
                    ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testSingleUse() {
        String code = read("single_use.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                List.of(new Use("console")),
                new Procedure(
                    "main",
                    null,
                    List.of( ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testUseWithDash() {
        String code = read("use_with_dash.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                List.of(new Use(new Namespace("spring-boot"))),
                new Procedure(
                    "main",
                    null,
                    List.of( ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testUseWithDot() {
        String code = read("use_with_dot.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                List.of(new Use(new Namespace("aws", "sqs"))),
                new Procedure(
                    "main",
                    null,
                    List.of( ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testUseWithManyDots() {
        String code = read("use_with_many_dots.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                List.of(new Use(new Namespace("foo", "bar", "baz", "doh"))),
                new Procedure(
                    "main",
                    null,
                    List.of( ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testMultipleUse() {
        String code = read("multiple_use.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile();
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                List.of(new Use("console"), new Use("disk"), new Use("http")),
                new Procedure(
                    "main",
                    null,
                    List.of( ),
                    new Block()
                )
            )
        );
    }
}