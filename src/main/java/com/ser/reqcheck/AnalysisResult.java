package com.ser.reqcheck;

import java.util.List;

public record AnalysisResult(List<AmbiguityRow> ambiguity, List<ConflictCandidate> conflicts) {}
