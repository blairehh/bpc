package org.bpc.compile;

import org.bpc.ast.Namespace;
import org.bpc.ast.Parameter;
import org.bpc.ast.Type;

import java.util.List;

public class SDKv1 implements SDK {
    @Override
    public List<Module> modules() {
        return List.of(
            new Module(
                new Namespace("console"),
                List.of(
                    new ExportedProcedure("println", List.of(new Parameter("value", new Type("string"))))
                ),
                List.of()
            )
        );
    }

    @Override
    public List<Type> types() {
        return List.of(
            new Type("int"),
            new Type("string"),
            new Type("dec"),
            new Type("int"),
            new Type("binary"),
            new Type("char"),
            new Type("num")
        );
    }
}
