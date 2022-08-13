package org.bpc.compile;

import org.bpc.ast.Namespace;

import java.util.Map;
import java.util.Optional;

public class ModuleRegistry {
    private final Map<Namespace, Module> modules;

    public ModuleRegistry(Map<Namespace, Module> modules) {
        this.modules = modules;
    }

    public Optional<Module> getModule(Namespace namespace) {
        return Optional.ofNullable(this.modules.get(namespace));
    }
}
