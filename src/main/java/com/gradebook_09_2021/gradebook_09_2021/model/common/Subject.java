package com.gradebook_09_2021.gradebook_09_2021.model.common;


public enum Subject  {
     ANG("angielski"), MAT("matematyka"),POL("polski"),;

    private final String description;

    private Subject(String description) {
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

}
