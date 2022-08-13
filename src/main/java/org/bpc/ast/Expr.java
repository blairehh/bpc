package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;

public interface Expr {
    List<Type> getTypesUsed();
    Expr canonicalize(Registry registry);
}
