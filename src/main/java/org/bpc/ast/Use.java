package org.bpc.ast;

import java.util.List;

public record Use(List<String> namespace) {
    public Use(String value) {
        this(List.of(value));
    }
}
