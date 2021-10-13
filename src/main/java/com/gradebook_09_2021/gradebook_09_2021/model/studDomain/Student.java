package com.gradebook_09_2021.gradebook_09_2021.model.studDomain;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Table(name = "t_uczniowie_v2")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Long id;

    @Column(name = "imie")
    private String name;
    @Column(name = "nazwisko")
    private String surName;
    @Column(name = "rocznik")
    @Enumerated(EnumType.STRING)
    private YearBook yearBook;
    @Column(name = "klasa")
    @Enumerated(EnumType.STRING)
    private ClassSign classSign;

    @OneToMany (cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="student_fk")
    private List<Grade> studGradeBook =new ArrayList<>();

    public Student(String name, String surName, YearBook yearBook, ClassSign classSign, List<Grade> studGradeBook) {
        this.name = name;
        this.surName = surName;
        this.yearBook = yearBook;
        this.classSign = classSign;
        this.studGradeBook = studGradeBook;

    }



}
