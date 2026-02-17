package com.ser.reqcheck;

import java.util.List;

public record AmbiguityRow(String rid, String text, double score, List<String> reasons) {}
