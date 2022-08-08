package org.bpc.compile.checks;


import org.bpc.ast.*;
import org.bpc.compile.CodeFile;
import org.bpc.compile.CompileJob;
import org.bpc.compile.SDKv1;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.TypeUnknown;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UnknownTypeCheckTest {

    private UnknownTypeCheck check = new UnknownTypeCheck();

    @Test
    void testProcedureReturnTypeIsUnknown() {
        CompileJob job = new CompileJob(
            new SDKv1(),
            List.of(
                new CodeFile(
                    List.of(),
                    List.of(
                        new Procedure(
                            "foo",
                            new Type("bar")
                        )
                    )
                )
            )
        );

        Set<CompilationError> errors = check.check(job);

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("bar"))
        );
    }

    @Test
    void testProcedureParameterTypeIsUnknown() {
        CompileJob job = new CompileJob(
            new SDKv1(),
            List.of(
                new CodeFile(
                    List.of(),
                    List.of(
                        new Procedure(
                            "foo",
                            new Type("binary"),
                            List.of(
                                new Parameter("bar", new Type("bar"))
                            ),
                            new Block()
                        )
                    )
                )
            )
        );

        Set<CompilationError> errors = check.check(job);

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("bar"))
        );
    }

    @Test
    void testVariableDeclarationInProcedureContainsUnknownType() {
        CompileJob job = new CompileJob(
            new SDKv1(),
            List.of(
                new CodeFile(
                    List.of(),
                    List.of(
                        new Procedure(
                            "foo",
                            new Type("binary"),
                            List.of(
                                new Parameter("bar", new Type("int"))
                            ),
                            new Block(
                                List.of(
                                    new VariableDeclaration(
                                        "baz",
                                        new Type("what"),
                                        new NumberExpr(3)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );

        Set<CompilationError> errors = check.check(job);

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("what"))
        );
    }

    @Test
    void testNoUnknownTypes() {
        CompileJob job = new CompileJob(
            new SDKv1(),
            List.of(
                new CodeFile(
                    List.of(),
                    List.of(
                        new Procedure(
                            "foo",
                            new Type("binary"),
                            List.of(
                                new Parameter("bar", new Type("int"))
                            ),
                            new Block(
                                List.of(
                                    new VariableDeclaration(
                                        "baz",
                                        new Type("num"),
                                        new NumberExpr(3)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );

        Set<CompilationError> errors = check.check(job);

        assertThat(errors).isEmpty();
    }
}