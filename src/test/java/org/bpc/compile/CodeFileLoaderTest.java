package org.bpc.compile;

import org.bpc.ast.*;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ModuleNotFound;
import org.bpc.compile.errors.TypeUnknown;
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
            List.of(new Import(new Namespace("console"))),
            List.of()
        );

        Set<CompilationError> errors = loader.load(file, sdk.baseRegistry());

        assertThat(errors).isEmpty();
    }

    @Test
    void testUnknownImport() {
        CodeFile file = new CodeFile(
            List.of(new Import(new Namespace("apache"))),
            List.of()
        );

        Set<CompilationError> errors = loader.load(file, sdk.baseRegistry());

        assertThat(errors).containsExactly(
            new ModuleNotFound(new Namespace("apache"))
        );
    }

    @Test
    void testProcedureReturnTypeIsUnknown() {
        CodeFile file = new CodeFile(
            List.of(),
            List.of(
                new Procedure(
                    "foo",
                    new Type("bar")
                )
            )
        );

        Set<CompilationError> errors = loader.load(file, sdk.baseRegistry());

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("bar"))
        );
    }

    @Test
    void testProcedureParameterTypeIsUnknown() {
        CodeFile file = new CodeFile(
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
        );

        Set<CompilationError> errors = loader.load(file, sdk.baseRegistry());

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("bar"))
        );
    }

    @Test
    void testVariableDeclarationInProcedureContainsUnknownType() {
        CodeFile file = new CodeFile(
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
        );

        Set<CompilationError> errors = loader.load(file,  sdk.baseRegistry());

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("what"))
        );
    }

    @Test
    void testNoUnknownTypes() {
        CodeFile file = new CodeFile(
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
        );

        Set<CompilationError> errors = loader.load(file, sdk.baseRegistry());

        assertThat(errors).isEmpty();
    }
}