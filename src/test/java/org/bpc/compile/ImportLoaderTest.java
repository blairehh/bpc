package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.Type;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bpc.compile.IdentityRegister.procedure;
import static org.bpc.compile.IdentityRegister.type;

class ImportLoaderTest {

    @Test
    void testImportModule() {
        SDK sdk = new SDKv1();
        IdentityRegister register = sdk.baseIdentityRegistry();
        Module module = new Module(
            new Namespace("disk"),
            List.of(
                new ExportedProcedure(
                    "write",
                    List.of(
                        new Parameter("fd", new Type("file-descriptor", new Namespace("disk"))),
                        new Parameter("value", new Type("string"))
                    ),
                    new Type("int")
                )
            ),
            List.of(
                new Type("file-descriptor", new Namespace("disk"))
            )
        );

        ReferencedModule actual = new ImportLoader()
            .reference(module, new Namespace("io", "filesystem"), register);

        ReferencedModule expected = new ReferencedModule(
            new Namespace("io", "filesystem"),
            new Namespace("disk"),
            List.of(
                new ReferencedProcedure(
                    new Identifier("write", new Namespace("io", "filesystem")),
                    new Identifier("write", new Namespace("disk")),
                    List.of(
                        new ReferencedParameter(
                            "fd",
                            new ReferencedType(
                                new Identifier("file-descriptor", new Namespace("io", "filesystem")),
                                new Identifier("file-descriptor", new Namespace("disk"))
                            )
                        ),
                        new ReferencedParameter(
                            "value",
                            new ReferencedType(new Identifier("string"), new Identifier("string"))
                        )
                    ),
                    Optional.of(new ReferencedType(new Identifier("int"), new Identifier("int")))
                )
            ),
            List.of(
                new ReferencedType(
                    new Identifier("file-descriptor", new Namespace("io", "filesystem")),
                    new Identifier("file-descriptor", new Namespace("disk"))
                )
            )
        );

        assertThat(actual).isEqualTo(expected);
        assertThat(register).isEqualTo(new IdentityRegister(
            sdk,
            Set.of(
                type(
                    new Identifier("file-descriptor", new Namespace("disk")),
                    new Identifier("file-descriptor", new Namespace("io", "filesystem"))
                )
            ),
            Set.of(
                procedure(
                    new Identifier("write", new Namespace("disk")),
                    new Identifier("write", new Namespace("io", "filesystem"))
                )
            )
        ));
    }
}