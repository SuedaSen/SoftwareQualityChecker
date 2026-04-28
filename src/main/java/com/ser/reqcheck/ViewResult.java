package com.ser.reqcheck;

import java.util.List;
import java.util.stream.Collectors;

/** View DTO with getters for Thymeleaf. */
public class ViewResult {
    private final List<ViewAmbiguityRow> ambiguity;
    private final List<ViewConflict> conflicts;

    public ViewResult(List<AmbiguityRow> ambiguity, List<ConflictCandidate> conflicts, List<Requirement> requirements) {
        this.ambiguity = ambiguity != null
                ? ambiguity.stream().map(ViewAmbiguityRow::new).collect(Collectors.toList())
                : List.of();
        var textById = requirements == null ? java.util.Map.<String, String>of()
                : requirements.stream().collect(Collectors.toMap(Requirement::rid, Requirement::text, (a, b) -> a));
        this.conflicts = conflicts != null
                ? conflicts.stream()
                .map(c -> new ViewConflict(c, textById.getOrDefault(c.leftId(), ""), textById.getOrDefault(c.rightId(), "")))
                .collect(Collectors.toList())
                : List.of();
    }

    public List<ViewAmbiguityRow> getAmbiguity() { return ambiguity; }
    public List<ViewConflict> getConflicts() { return conflicts; }
}
