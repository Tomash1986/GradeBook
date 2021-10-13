package com.gradebook_09_2021.gradebook_09_2021.model.common;

public enum ClassSign {
    a("a"),b("b"),c("c");
    private String description;

    private ClassSign(String description) {
        this.description=description;
    }

    public String getDescription() {
        return description;
    }
}
