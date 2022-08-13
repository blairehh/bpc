package org.bpc.compile;

import org.bpc.ast.Namespace;
import org.bpc.ast.Use;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ModuleNotFound;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CodeFileLoaderTest {

    SDK sdk = new SDKv1();
    CodeFileLoader loader = new CodeFileLoader();

    @Test
    void testImportSdkModule() {

        CodeFile file = new CodeFile(
            List.of(new Use(new Namespace("console"))),
            List.of()
        );

        Set<CompilationError> errors = loader.load(file, sdk.baseModuleRegistry(), sdk.baseIdentityRegistry());

        assertThat(errors).isEmpty();
    }


    @Test
    void testUnknownImport() {
        CodeFile file = new CodeFile(
            List.of(new Use(new Namespace("apache"))),
            List.of()
        );

        Set<CompilationError> errors = loader.load(file, sdk.baseModuleRegistry(), sdk.baseIdentityRegistry());

        assertThat(errors).containsExactly(
            new ModuleNotFound(new Namespace("apache"))
        );
    }
}