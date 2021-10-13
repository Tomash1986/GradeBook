package com.gradebook_09_2021.gradebook_09_2021.model.common;

public enum YearBook {
    I("I"), II("II"), III("III");
    private final String description;

    private YearBook(String description) {
        this.description=description;
    }

    public String getDescription(){
        return description;
    }
}
