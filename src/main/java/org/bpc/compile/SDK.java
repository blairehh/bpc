package org.bpc.compile;

import org.bpc.ast.Type;

import java.util.List;

public interface SDK {
    List<Module> modules();
    List<Type> types();
    IdentityRegister baseRegistry();
}
