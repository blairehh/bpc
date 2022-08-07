package org.bpc.compile;

import java.util.List;

public record CompileJob(SDK sdk, List<CodeFile> files) {

}
