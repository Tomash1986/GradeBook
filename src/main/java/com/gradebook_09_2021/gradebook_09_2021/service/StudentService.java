package com.gradebook_09_2021.gradebook_09_2021.service;

import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;

import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Grade;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;
import com.gradebook_09_2021.gradebook_09_2021.repo.StudRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudRepo studRepo;

    public List<Student> getAllSchoolStudents(){
        return studRepo.findAll();
    }

    public Student getStudentById(Long id){
        return studRepo.getOne(id);
    }

    public List<Grade> getStudGradesforSpecificSubject(Long id, Subject subject){
        Student student = studRepo.getOne(id);
        List<Grade> studGradeBook = student.getStudGradeBook();
        List<Grade> studentGradesForSpecificSubject = studGradeBook.stream().filter(e -> e.getSubject().equals(subject)).collect(Collectors.toList());
        return studentGradesForSpecificSubject;
    }

    public void save(Student student) {
        studRepo.save(student);
    }

}
