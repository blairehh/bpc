package org.bpc.compile;

import org.bpc.ast.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bpc.compile.Registry.procedure;
import static org.bpc.compile.Registry.type;

class ImportLoaderTest {

    @Test
    void testImportModule() {
        SDK sdk = new SDKv1();
        Registry register = sdk.baseRegistry();
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

        new ImportLoader()
            .load(module, new Namespace("io", "filesystem"), register);

        assertThat(register).isEqualTo(new Registry(
            sdk,
            Set.of(
                type(
                    new Type("file-descriptor", new Namespace("disk")),
                    new Type("file-descriptor", new Namespace("io", "filesystem"))
                )
            ),
            Set.of(
                procedure(
                    new ImportedProcedureIdentifier("write", new Namespace("disk")),
                    new ImportedProcedureIdentifier("write", new Namespace("io", "filesystem")),
                    new ImportedProcedure(
                        new ImportedProcedureIdentifier("write", new Namespace("io", "filesystem")),
                        new ImportedProcedureIdentifier("write", new Namespace("disk")),
                        List.of(
                            new ImportedParameter(
                                "fd",
                                new ImportedType(
                                    new Type("file-descriptor", new Namespace("io", "filesystem")),
                                    new Type("file-descriptor", new Namespace("disk"))
                                )
                            ),
                            new ImportedParameter(
                                "value",
                                new ImportedType(new Type("string"), new Type("string"))
                            )
                        ),
                        Optional.of(new ImportedType(new Type("int"), new Type("int")))
                    )
                )
            )
        ));
    }
}