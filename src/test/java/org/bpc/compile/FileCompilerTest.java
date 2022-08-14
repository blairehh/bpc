package org.bpc.compile;

import org.bpc.ast.*;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.ModuleNotFound;
import org.bpc.compile.errors.TypeUnknown;
import org.bpc.transpile.TranspileFile;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FileCompilerTest {

    SDK sdk = new SDKv1();
    FileCompiler loader = new FileCompiler();

    @Test
    void testImportSdkModule() {

        CodeFile file = new CodeFile(
            "./code.bp",
            List.of(new Import(new Namespace("console"))),
            List.of()
        );

        FileCompilation result = loader.load(file, sdk.baseRegistry());

        assertThat(result).isEqualTo(new FileCompilation.Ok(new TranspileFile("./code.bp", List.of())));
    }

    @Test
    void testUnknownImport() {
        CodeFile file = new CodeFile(
            "./code.bp",
            List.of(new Import(new Namespace("apache"))),
            List.of()
        );

        Set<CompilationError> errors = ((FileCompilation.Errors)loader.load(file, sdk.baseRegistry())).errors();

        assertThat(errors).containsExactly(
            new ModuleNotFound(new Namespace("apache"))
        );
    }

    @Test
    void testProcedureReturnTypeIsUnknown() {
        CodeFile file = new CodeFile(
            "./code.bp",
            List.of(),
            List.of(
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        new Type("bar")
                    )
                )
            )
        );

        Set<CompilationError> errors = ((FileCompilation.Errors)loader.load(file, sdk.baseRegistry())).errors();

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("bar"))
        );
    }

    @Test
    void testProcedureParameterTypeIsUnknown() {
        CodeFile file = new CodeFile(
            "./code.bp",
            List.of(),
            List.of(
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(
                            new Parameter("bar", new Type("bar"))
                        ),
                        Optional.of(new Type("bool"))
                    ),
                    new Block()
                )
            )
        );

        Set<CompilationError> errors = ((FileCompilation.Errors)loader.load(file, sdk.baseRegistry())).errors();

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("bar"))
        );
    }

    @Test
    void testVariableDeclarationInProcedureContainsUnknownType() {
        CodeFile file = new CodeFile(
            "./code.bp",
            List.of(),
            List.of(
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(
                            new Parameter("bar", new Type("int"))
                        ),
                        Optional.of(new Type("bool"))
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

        Set<CompilationError> errors = ((FileCompilation.Errors)loader.load(file,  sdk.baseRegistry())).errors();

        assertThat(errors).containsExactly(
            new TypeUnknown(new Type("what"))
        );
    }

    @Test
    void testNoUnknownTypes() {
        CodeFile file = new CodeFile(
            "./code.bp",
            List.of(),
            List.of(
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(
                            new Parameter("bar", new Type("int"))
                        ),
                        Optional.of(new Type("bool"))
                    ),
                    new Block(
                        List.of(
                            new VariableDeclaration(
                                "baz",
                                new Type("float"),
                                new NumberExpr(3)
                            )
                        )
                    )
                )
            )
        );

        FileCompilation result = loader.load(file, sdk.baseRegistry());

        TranspileFile transpile = new TranspileFile(
            "./code.bp",
            List.of(
                new Procedure(
                    new ProcedureSignature(
                        "foo",
                        List.of(
                            new Parameter("bar", new Type("int"))
                        ),
                        Optional.of(new Type("bool"))
                    ),
                    new Block(
                        List.of(
                            new VariableDeclaration(
                                "baz",
                                new Type("float"),
                                new NumberExpr(3)
                            )
                        )
                    )
                )
            )
        );

        assertThat(result).isEqualTo(new FileCompilation.Ok(transpile));
    }
}