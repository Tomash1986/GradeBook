package com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class DtoTeacher {

    Long id;
    private String name;
    private String surName;
    private Subject teachedSubject;
    private List<ClassDetail> classDetailList;

//    To jest potrzebne aby w menu mozna bylo dodac liste rozwijalna z nieobsadzonymi klasami
    private List<ClassDetail> classWithoutAsignedTeacherToSubject;

// To musze wrzucic bo nie bede mogl wypelnic formularza !!!
    private YearBook newYearBook;
    private ClassSign newClassSign;
//

}
