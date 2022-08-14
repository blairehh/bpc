package org.bpc.transpile.java;

import org.bpc.ast.*;
import org.bpc.transpile.TranspileFile;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FileTranspilerTest {

    FileTranspiler transpile = new FileTranspiler();

    @Test
    void testEmptyTranspileFile() {
        TranspileFile file = new TranspileFile(
            "./main.bp",
            List.of()
        );

        String javaCode = transpile.transpile(file);

        assertThat(javaCode).isEqualTo("""
            public static class __f__Li9tYWluLmJw {
            }
            """.trim());
    }

    @Test
    void testTranspileEmptyProcedure() {
        TranspileFile file = new TranspileFile(
            "./procs.bp",
            List.of(
                new Procedure(
                    "main",
                    Optional.empty(),
                    List.of(),
                    new Block()
                )
            )
        );

        String javaCode = transpile.transpile(file);

        assertThat(javaCode).isEqualTo("""
            public static class __f__Li9wcm9jcy5icA__ {
            public static void __p__main() {
            }
            }
            """.trim());
    }

    @Test
    void testTranspileProcedureWithReturnTypeAndParameters() {
        TranspileFile file = new TranspileFile(
            "./procs.bp",
            List.of(
                new Procedure(
                    "main",
                    Optional.of(new Type("int")),
                    List.of(
                        new Parameter(
                            "args",
                            new Type("list", new Namespace("collections"))
                        )
                    ),
                    new Block()
                )
            )
        );

        String javaCode = transpile.transpile(file);

        assertThat(javaCode).isEqualTo("""
            public static class __f__Li9wcm9jcy5icA__ {
            public static __t__int __p__main(__t__collections__list __v__args) {
            }
            }
            """.trim());
    }

    @Test
    void testProcedureWithMultipleParameters() {
        TranspileFile file = new TranspileFile(
            "./procs.bp",
            List.of(
                new Procedure(
                    "main",
                    Optional.empty(),
                    List.of(
                        new Parameter(
                            "args",
                            new Type("list", new Namespace("collections"))
                        ),
                        new Parameter("size", new Type("int")),
                        new Parameter(
                            "writer",
                            new Type("buffer", new Namespace("out", "bin"))
                        )
                    ),
                    new Block()
                )
            )
        );

        String javaCode = transpile.transpile(file);

        assertThat(javaCode).isEqualTo("""
            public static class __f__Li9wcm9jcy5icA__ {
            public static void __p__main(__t__collections__list __v__args,__t__int __v__size,__t__out__bin__buffer __v__writer) {
            }
            }
            """.trim());
    }

    @Test
    void testVariableDeclaration() {
        TranspileFile file = new TranspileFile(
            "./procs.bp",
            List.of(
                new Procedure(
                    "main",
                    Optional.empty(),
                    List.of(),
                    new Block(
                        new VariableDeclaration(
                            "name",
                            new Type("string"),
                            new StringExpr("Bob")
                        )
                    )
                )
            )
        );

        String javaCode = transpile.transpile(file);

        assertThat(javaCode).isEqualTo("""
            public static class __f__Li9wcm9jcy5icA__ {
            public static void __p__main() {
            __t__string __v__name = "Bob";
            }
            }
            """.trim());
    }

    @Test
    void testReturnStatement() {
        TranspileFile file = new TranspileFile(
            "./procs.bp",
            List.of(
                new Procedure(
                    "main",
                    Optional.empty(),
                    List.of(),
                    new Block(
                        new ReturnStatement(new Identifier("value"))
                    )
                )
            )
        );

        String javaCode = transpile.transpile(file);

        assertThat(javaCode).isEqualTo("""
            public static class __f__Li9wcm9jcy5icA__ {
            public static void __p__main() {
            return __v__value;
            }
            }
            """.trim());
    }

    @Test
    void testProcedureCall() {
        TranspileFile file = new TranspileFile(
            "./procs.bp",
            List.of(
                new Procedure(
                    "main",
                    Optional.empty(),
                    List.of(),
                    new Block(
                        new ProcedureCall(
                            new Identifier("println"),
                            List.of(
                                new StringExpr("oi"),
                                new BoolExpr(false)
                            )
                        )
                    )
                )
            )
        );

        String javaCode = transpile.transpile(file);

        assertThat(javaCode).isEqualTo("""
            public static class __f__Li9wcm9jcy5icA__ {
            public static void __p__main() {
            __p__println("oi",false);
            }
            }
            """.trim());
    }
}