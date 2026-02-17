package com.ser.reqcheck;

import java.util.List;
import java.util.stream.Collectors;

/** View DTO with getters for Thymeleaf. */
public class ViewResult {
    private final List<ViewAmbiguityRow> ambiguity;
    private final List<ViewConflict> conflicts;

    public ViewResult(List<AmbiguityRow> ambiguity, List<ConflictCandidate> conflicts) {
        this.ambiguity = ambiguity != null
                ? ambiguity.stream().map(ViewAmbiguityRow::new).collect(Collectors.toList())
                : List.of();
        this.conflicts = conflicts != null
                ? conflicts.stream().map(ViewConflict::new).collect(Collectors.toList())
                : List.of();
    }

    public List<ViewAmbiguityRow> getAmbiguity() { return ambiguity; }
    public List<ViewConflict> getConflicts() { return conflicts; }
}
