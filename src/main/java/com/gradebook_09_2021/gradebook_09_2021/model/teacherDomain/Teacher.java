package com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain;

import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
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
@Table(name = "t_nauczyciele")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imie")
    private String name="domyslne";
    @Column(name = "nazwisko")
    private String surName;
    @Column(name = "uczony_przedmiot")
    @Enumerated(EnumType.STRING)
    private Subject teachedSubject;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_class_mapping",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns ={@JoinColumn(name="class_id")})
    private List<ClassDetail> assignedClasses= new ArrayList<>();



    //    tutaj w przeciwienstwie do classdetails nie trzeba robic powiazan(bo jest to właściciel relacji)
//    porownac jak to wyglada tutaj a jak w classdetials
    public void addClass(ClassDetail classdetail){
        assignedClasses.add(classdetail);
    }

    public void removeClass(ClassDetail classDetail){
        assignedClasses.remove(classDetail);
    }
    public void removeAllClasses(){
//        for (int i = 0; i < assignedClasses.size(); i++) {
//             assignedClasses.get(i).removeTeacher(this);
//        }
        for(ClassDetail assignedClass :new ArrayList<>(this.getAssignedClasses()))
        {
            assignedClass.removeTeacher(this);
        }
    }


}
