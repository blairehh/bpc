package org.bpc.ast;

import org.bpc.compile.Registry;

import java.util.List;

// TODO merge this with Expr?
public interface Statement {
    List<Type> getTypesUsed();
    Statement canonicalizeStatement(Registry registry);
}
