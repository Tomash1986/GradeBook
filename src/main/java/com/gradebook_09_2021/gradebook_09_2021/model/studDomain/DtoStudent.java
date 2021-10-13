package com.gradebook_09_2021.gradebook_09_2021.model.studDomain;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;

import java.util.ArrayList;
import java.util.List;

public class DtoStudent {

        private Long id;
        private String name;
        private String surName;
        private YearBook yearBook;
        private ClassSign classSign;
        private List<Grade> studGradeBook =new ArrayList<>();

        public DtoStudent(String name, String surName, YearBook yearBook, ClassSign classSign, List<Grade> studGradeBook) {
            this.name = name;
            this.surName = surName;
            this.yearBook = yearBook;
            this.classSign = classSign;
            this.studGradeBook = studGradeBook;
        }

        public DtoStudent() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurName() {
            return surName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }

        public YearBook getYearBook() {
            return yearBook;
        }

        public void setYearBook(YearBook yearBook) {
            this.yearBook = yearBook;
        }

        public ClassSign getClassSign() {
            return classSign;
        }

        public void setClassSign(ClassSign classSign) {
            this.classSign = classSign;
        }

        public List<Grade> getStudGradeBook() {
            return studGradeBook;
        }

        public void setStudGradeBook(List<Grade> studGradeBook) {
            this.studGradeBook = studGradeBook;
        }
}
