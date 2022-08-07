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
                    new Scope()
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
                    new Scope()
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
                    new Scope()
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
                    new Scope(
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
                    new Scope(
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
                    new Scope(
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
                    new Scope(
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
                    new Scope(
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

}