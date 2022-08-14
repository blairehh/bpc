package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;

public interface Statement {
    List<Type> getTypesUsed();
    Statement canonicalize(Registry registry);
}
