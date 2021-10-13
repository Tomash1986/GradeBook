package com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain;


import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "t_klasy")
public class ClassDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rocznik")
    @Enumerated(EnumType.STRING)
    private YearBook yearBook= YearBook.I;
    @Column(name = "klasa")
    @Enumerated(EnumType.STRING)
    private ClassSign classSign=ClassSign.a;

    @ManyToMany(mappedBy = "assignedClasses", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Teacher> assignTeachers = new ArrayList<>();


    //    Błedne rowiązanie ponieważ nie następuję wpis do tablicy mapującej !!!!
    //    Nie stosować !!!
//    public ClassDetails(YearBook yearBook, ClassSign classSign, List<Teacher> teacherList) {
//        this.yearBook = yearBook;
//        this.classSign = classSign;
//        this.assignTeachers = teacherList;
//    }

//    To roziwąznie stosować poniewąż pozniej można zastosować metody ADD  i remove któe tworzą wiąznie !!!!!
//    public ClassDetails(YearBook yearBook, ClassSign classSign) {
//        this.yearBook = yearBook;
//        this.classSign = classSign;
//    }

    public void addTeacher(Teacher teacher) {
        //To jest sklejenie w tablicy mapujacej
//        1) Dodaje do techera obiekt klasy
//        2) Dodaje do klasy obiekt teachera

        teacher.getAssignedClasses().add(this);
        getAssignTeachers().add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        //To jest sklejenie w tablicy mapujacej
//        1 ) Usuwam z techera obiekt klasy
//        2) Usuwam dz klasyklasy obiekt teachera
        teacher.getAssignedClasses().remove(this);
        getAssignTeachers().remove(teacher);
    }


}
