package com.ser.reqcheck;

import java.util.List;

public class ViewAmbiguityRow {
    private final String rid;
    private final String text;
    private final double score;
    private final List<String> reasons;

    public ViewAmbiguityRow(AmbiguityRow r) {
        this.rid = r.rid();
        this.text = r.text();
        this.score = r.score();
        this.reasons = r.reasons();
    }

    public String getRid() { return rid; }
    public String getText() { return text; }
    public double getScore() { return score; }
    public List<String> getReasons() { return reasons; }
}
