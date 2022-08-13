package org.bpc.transpile.java;

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
            public static class _f_Li9tYWluLmJw {
            }
            """.trim());
    }
}