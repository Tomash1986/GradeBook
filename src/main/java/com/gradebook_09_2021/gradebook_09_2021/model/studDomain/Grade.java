package com.gradebook_09_2021.gradebook_09_2021.model.studDomain;

import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_ocena_v2")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ocena")
    private Integer grade;
    @Column(name = "przedmiot")
    @Enumerated(EnumType.STRING)
    private Subject subject;
    @Column(name="komentarz")
    private String gradeComment="domyslny comment";
    @Column(name="data_wstawienia")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateStart;
    @Column(name="imie_naczyciela")
    private String teacherName="domyslne imie";
    @Column(name="nazwisko_nauczyciela")
    private String teacherSurname="domyslne nazwisko";


    public Grade(Integer grade, Subject subject, LocalDate dateStart) {
        this.grade = grade;
        this.subject = subject;
        this.dateStart = dateStart;
    }



}
