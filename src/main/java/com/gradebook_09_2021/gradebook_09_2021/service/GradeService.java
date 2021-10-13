package com.gradebook_09_2021.gradebook_09_2021.service;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.DTOGrade;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Grade;
import com.gradebook_09_2021.gradebook_09_2021.repo.GradeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    @Autowired
    private GradeRepo gradeRepo;



    public void delete(Long id){
        gradeRepo.deleteById(id);
    }

    public Grade edit(Long id){
        return gradeRepo.getOne(id);
    }

    public DTOGrade mapGradeToDtoGrade(Grade grade, YearBook yearBook, ClassSign classSign){
        DTOGrade dtoGrade = DTOGrade.builder().build();

        dtoGrade.setId(grade.getId());
        dtoGrade.setGrade(grade.getGrade());
        dtoGrade.setDateStart(grade.getDateStart());
        dtoGrade.setGradeComment(grade.getGradeComment());
        dtoGrade.setTeacherName(grade.getTeacherName());
        dtoGrade.setTeacherSurname(grade.getTeacherSurname());

        dtoGrade.setSubject(grade.getSubject());
        dtoGrade.setClassSign(classSign);
        dtoGrade.setYearBook(yearBook);
        return dtoGrade;
    }

    public Grade mapDtoGradeToGrade2(DTOGrade dtoGrade){
        Grade grade = Grade.builder().build();
        grade.setId(dtoGrade.getId());
        grade.setGrade(dtoGrade.getGrade());
        grade.setDateStart(dtoGrade.getDateStart());
        grade.setGradeComment(dtoGrade.getGradeComment());
        grade.setSubject(dtoGrade.getSubject());
        grade.setTeacherName(dtoGrade.getTeacherName());
        grade.setTeacherSurname(dtoGrade.getTeacherSurname());
        return grade;
    }

    public void save(Grade grade){
        gradeRepo.save(grade);
    }
}
