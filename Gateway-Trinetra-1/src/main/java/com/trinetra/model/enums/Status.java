package com.trinetra.model.enums;

public enum Status {
    SUCCESS("success"),
    FAILED("failed");

    public final String name;

    private Status(String name) {
        this.name = name;
    }
}
