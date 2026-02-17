package com.ser.reqcheck;

public record ConflictCandidate(String leftId, String rightId, double similarity, String kind, String evidence) {}
