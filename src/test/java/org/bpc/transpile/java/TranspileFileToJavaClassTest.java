package org.bpc.transpile.java;

import org.bpc.ast.*;
import org.bpc.transpile.TranspileFile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TranspileFileToJavaClassTest {

    TranspileFileToJavaClass transpile = new TranspileFileToJavaClass();

    @Test
    void testEmptyTranspileFile() {
        TranspileFile file = new TranspileFile(
            "./main.bp",
            List.of()
        );

        String javaCode = transpile.toJavaClass(file);

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
                    null,
                    List.of(),
                    new Block()
                )
            )
        );

        String javaCode = transpile.toJavaClass(file);

        assertThat(javaCode).isEqualTo("""
            public static class __f__Li9wcm9jcy5icA__ {
            public static void __p__main() {
            }
            }
            """.trim());
    }
}