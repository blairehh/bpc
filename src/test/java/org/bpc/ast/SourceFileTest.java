package org.bpc.ast;

import org.bpc.syntax.SyntaxCompilation;
import org.bpc.syntax.SyntaxCompiler;
import org.junit.jupiter.api.Test;

import java.sql.Ref;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bpc.Util.read;

class SourceFileTest {

    @Test
    void testEmptyProcWithNoParameters() {
        String code = read("empty_proc_with_no_parameters.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "bar",
                        List.of(),
                        Optional.empty()
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(new Parameter("foo", new Type("int"))),
                        Optional.of(new Type("int"))
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(new Parameter("foo", new Type("int"))),
                        Optional.empty()
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(new Parameter("bar", new Type("int"))),
                        Optional.empty()
                    ),
                    new Block(
                        new VariableDeclaration(
                            "baz",
                            new Type("float"),
                            new NumberExpr(0)
                        ),
                        new ProcedureCall(new ProcedureExpr(new Reference("doh")))
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "foo-bar",
                        List.of(new Parameter("fo", new Type("fo"))),
                        Optional.of(new Type("int"))
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "foo-bar",
                        List.of(new Parameter("fo", new Type("fo"))),
                        Optional.of(new Type("int"))
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "foo-bar",
                        List.of(),
                        Optional.of(new Type("int"))
                    ),
                    new Block(
                        new VariableDeclaration(
                            "num",
                            new Type("int"),
                            new NumberExpr(9)
                        ),
                        new VariableDeclaration(
                            "active",
                            new Type("bool"),
                            new BoolExpr(true)
                        ),
                        new VariableDeclaration(
                            "text",
                            new Type("string"),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block(
                        new ProcedureCall(
                            new ProcedureExpr(
                                new Reference("bar"),
                                List.of(
                                    new NumberExpr(1),
                                    new BoolExpr(false),
                                    new StringExpr("oi")
                                )
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);

        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block(
                        new VariableDeclaration(
                            "bar",
                            new Type("int"),
                            new ProcedureExpr(
                                new Reference("baz"),
                                List.of(
                                    new ProcedureExpr(new Reference("doh"), List.of())
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);

        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block(
                        new VariableDeclaration(
                            "bar",
                            new Type("int"),
                            new ProcedureExpr(
                                new Reference("baz"),
                                List.of(
                                    new ProcedureExpr(
                                        new Reference("doh"),
                                        List.of(
                                            new NumberExpr(987),
                                            new ProcedureExpr(new Reference("something"), List.of(new StringExpr("here"))),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "func",
                        List.of(),
                        Optional.of(new Type("int"))
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "func",
                        List.of(),
                        Optional.of(new Type("int"))
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "func",
                        List.of(),
                        Optional.of(new Type("int"))
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(),
                        Optional.empty()
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block(
                        new ProcedureCall(new ProcedureExpr(new Reference("foo")))
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block(
                        new ProcedureCall(new ProcedureExpr(new Reference("foo")))
                    )
                ),
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block(
                        new ProcedureCall(new ProcedureExpr(new Reference("bar")))
                    )
                ),
                new Procedure(
                    new ProcedureSignature(
                        "bar",
                        List.of(),
                        Optional.empty()
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block(
                        new VariableDeclaration(
                            "number",
                            new Type("int"),
                            new NumberExpr(8)
                        ),
                        new ProcedureCall(new ProcedureExpr(new Reference("print"), List.of(new Reference("number"))))
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.of(new Type("tcp-server", new Namespace("sock")))
                    ),
                    new Block(
                        new ProcedureCall(new ProcedureExpr(new Reference("run")))
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block(
                        new ProcedureCall(
                            new ProcedureExpr(
                                new Reference("println", new Namespace("console")),
                                List.of(
                                    new Reference("name", new Namespace("os")),
                                    new ProcedureExpr(new Reference("bar", new Namespace("foo")))
                                )
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
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

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(
                            new Parameter("fd", new Type("file-descriptor", new Namespace("io"))),
                            new Parameter("mode", new Type("int"))
                        ),
                        Optional.empty()
                    ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testSingleImport() {
        String code = read("single_import.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                List.of(new Import("console")),
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testImportWithDash() {
        String code = read("import_with_dash.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                List.of(new Import(new Namespace("spring-boot"))),
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testImportWithDot() {
        String code = read("import_with_dot.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                List.of(new Import(new Namespace("aws", "sqs"))),
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testImportWithManyDots() {
        String code = read("import_with_many_dots.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                List.of(new Import(new Namespace("foo", "bar", "baz", "doh"))),
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block()
                )
            )
        );
    }

    @Test
    void testMultipleImports() {
        String code = read("multiple_imports.bp");
        SyntaxCompilation compilation = new SyntaxCompiler()
            .compile(code);

        SourceFile sourceFile = new SourceFile("./code.bp");
        compilation.walk(sourceFile);

        System.out.println(sourceFile);
        assertThat(compilation.hasErrors()).isFalse();
        assertThat(sourceFile).isEqualTo(
            new SourceFile(
                "./code.bp",
                List.of(new Import("console"), new Import("disk"), new Import("http")),
                new Procedure(
                    new ProcedureSignature(
                        "main",
                        List.of(),
                        Optional.empty()
                    ),
                    new Block()
                )
            )
        );
    }
}