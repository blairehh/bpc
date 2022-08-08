package org.bpc.compile.checks;

import org.bpc.ast.*;
import org.bpc.compile.CompileJob;
import org.bpc.compile.Module;
import org.bpc.compile.SDK;
import org.bpc.compile.errors.CompilationError;
import org.bpc.compile.errors.TypeUnknown;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnknownTypeCheck {

    public Set<CompilationError> check(CompileJob job) {
        return job.files().stream()
            .flatMap((file) -> file.getTypesUsed().stream())
            .flatMap((type) -> check(job.sdk(), type))
            .collect(Collectors.toSet());
    }

    private Stream<CompilationError> check(SDK sdk, Type type) {
        if (sdk.types().contains(type)) {
            return Stream.empty();
        }

        for (Module module : sdk.modules()) {
            if (module.types().contains(type)) {
                return Stream.empty();
            }
        }

        return Stream.of(new TypeUnknown(type));
    }
}
