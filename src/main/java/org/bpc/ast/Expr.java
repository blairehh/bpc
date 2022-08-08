package org.bpc.ast;

import java.util.List;

public interface Expr {
    List<Type> getTypesUsed();
}
