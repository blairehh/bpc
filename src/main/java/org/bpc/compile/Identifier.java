package org.bpc.compile;

import org.bpc.ast.Namespace;

public interface Identifier {
    String name();
    Namespace namespace();
}