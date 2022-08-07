package org.bpc.compile;

import org.bpc.ast.SourceFile;

import java.util.List;

public record Compiler(SDK sdk, List<SourceFile> sourceFiles) {

}
