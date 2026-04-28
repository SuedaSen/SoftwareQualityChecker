package com.ser.reqcheck;

public class ViewConflict {
    private final String leftId;
    private final String rightId;
    private final String leftText;
    private final String rightText;
    private final double similarity;
    private final String kind;
    private final String evidence;

    public ViewConflict(ConflictCandidate c, String leftText, String rightText) {
        this.leftId = c.leftId();
        this.rightId = c.rightId();
        this.leftText = leftText;
        this.rightText = rightText;
        this.similarity = c.similarity();
        this.kind = c.kind();
        this.evidence = c.evidence();
    }

    public String getLeftId() { return leftId; }
    public String getRightId() { return rightId; }
    public String getLeftText() { return leftText; }
    public String getRightText() { return rightText; }
    public double getSimilarity() { return similarity; }
    public String getKind() { return kind; }
    public String getEvidence() { return evidence; }
}
