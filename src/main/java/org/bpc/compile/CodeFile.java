package org.bpc.compile;

import org.bpc.ast.Procedure;
import org.bpc.ast.Type;
import org.bpc.ast.Use;

import java.util.List;

public record CodeFile(List<Use> uses, List<Procedure> procedures) {
    public List<Type> getTypesUsed() {
        return this.procedures.stream()
            .flatMap((procedure) -> procedure.getTypesUsed().stream())
            .toList();
    }
}
