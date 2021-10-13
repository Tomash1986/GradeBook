
package com.gradebook_09_2021.gradebook_09_2021.model.studDomain;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DTOGrade {


    private Long id;
    private Integer grade;
    private Subject subject;
    private YearBook yearBook;
    private ClassSign classSign;
//
    private String gradeComment="domyslny comment";
    private LocalDate dateStart;
    private String teacherName="domyslne imie";
    private String teacherSurname="domyslne nazwisko";


    public DTOGrade(Integer grade, Subject subject, LocalDate dateStart) {
        this.grade = grade;
        this.subject = subject;
        this.dateStart = dateStart;
    }



}