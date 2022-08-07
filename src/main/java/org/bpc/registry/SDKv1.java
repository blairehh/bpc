package org.bpc.registry;

import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.List;

public class SDKv1 implements SDK {
    @Override
    public List<Module> modules() {
        return List.of(
            new Module(
                List.of("console"),
                List.of(
                    new ExportedProcedure("println", List.of(new Parameter("value", new Type("string"))))
                )
            )
        );
    }
}
