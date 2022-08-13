package org.bpc.compile;

import org.bpc.ast.Identifier;
import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.Type;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ModuleReferencerTest {

    @Test
    void testReferenceModule() {
        Module module = new Module(
            new Namespace("disk"),
            List.of(
                new ExportedProcedure(
                    "write",
                    List.of(
                        new Parameter("fd", new Type("file-descriptor", new Namespace("disk"))),
                        new Parameter("value", new Type("string"))
                    )
                )
            ),
            List.of(
                new Type("file-descriptor", new Namespace("disk"))
            )
        );

        ReferencedModule actual = new ModuleReferencer()
            .reference(module, new Namespace("io", "filesystem"), new IdentityRegister());

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
                    Optional.empty()
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
    }
}