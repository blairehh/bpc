package org.bpc.registry;

import java.util.List;

public record Module(List<String> namespace, List<ExportedProcedure> procedures) {
}
