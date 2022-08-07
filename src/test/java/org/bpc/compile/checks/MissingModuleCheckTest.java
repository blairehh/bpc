package org.bpc.compile.checks;

import org.bpc.ast.Namespace;
import org.bpc.ast.Use;
import org.bpc.compile.CodeFile;
import org.bpc.compile.CompileJob;
import org.bpc.compile.SDKv1;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ModuleNotFound;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MissingModuleCheckTest {

    MissingModuleCheck check = new MissingModuleCheck();

    @Test
    void testSdkModuleIsNotBadUse() {
        CompileJob job = new CompileJob(
            new SDKv1(),
            List.of(
                new CodeFile(
                    List.of(new Use(new Namespace("console"))),
                    List.of()
                )
            )
        );

        List<CompilationError> errors = check.check(job);

        assertThat(errors).isEmpty();
    }

    @Test
    void testBadUse() {
        CompileJob job = new CompileJob(
            new SDKv1(),
            List.of(
                new CodeFile(
                    List.of(new Use(new Namespace("apache"))),
                    List.of()
                )
            )
        );

        List<CompilationError> errors = check.check(job);

        assertThat(errors).containsExactly(
            new ModuleNotFound(new Namespace("apache"))
        );
    }
}