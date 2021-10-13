package com.gradebook_09_2021.gradebook_09_2021.service;


import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.DtoStudent;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Grade;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.ClassDetail;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.DtoTeacher;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.Teacher;
import com.gradebook_09_2021.gradebook_09_2021.repo.ClassRepo;
import com.gradebook_09_2021.gradebook_09_2021.repo.StudRepo;

import com.gradebook_09_2021.gradebook_09_2021.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private StudRepo studRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private ClassRepo classRepo;


    List<Student> getStudentsAndTheirGrades(YearBook yearBook, ClassSign classSign) {
        return studRepo.findByYearBookAndClassSign(yearBook, classSign);
    }

    //    Tego użyjemy w controlerze teachera
    public List<DtoStudent> getStudentAndTheirGradsForSpecifcSubject(YearBook yearBook, ClassSign classSign, Subject subject) {
//       Ta czesc zwróci liste uczniwo z danej klasy ale z ocenami ze wszystkich przedmitow
        List<Student> students = getStudentsAndTheirGrades(yearBook, classSign);
        List<DtoStudent> listofGradesOfSubject = new ArrayList<>();
//        Ta czesc zwrocic oceny uczniow danej klasy dla danego przedmitou
        for (Student studnet : students) {
            DtoStudent dtoStudent = new DtoStudent();
            dtoStudent.setId(studnet.getId());
            dtoStudent.setName(studnet.getName());
            dtoStudent.setSurName(studnet.getSurName());
            dtoStudent.setYearBook(studnet.getYearBook());
            dtoStudent.setClassSign(studnet.getClassSign());
            List<Grade> studGradesForSpecificSubject = studnet.getStudGradeBook().stream().filter(e -> e.getSubject().equals(subject)).collect(Collectors.toList());
            dtoStudent.setStudGradeBook(studGradesForSpecificSubject);
            listofGradesOfSubject.add(dtoStudent);
        }

        return listofGradesOfSubject;
    }

    private boolean checkIfClassExists(YearBook yearBook, ClassSign classSign) {
        Optional<ClassDetail> classDetails = classRepo.findByYearBookAndClassSign(yearBook, classSign);
        return classDetails.isPresent();
    }

    public boolean checkIfTeacherExists(String name, String surName) {
        Optional<Teacher> byNameAndSurName = teacherRepo.findByNameAndSurName(name, surName);
        return byNameAndSurName.isPresent();
    }

    public void save(Teacher teacher) {
        teacherRepo.save(teacher);
    }

    public List<Teacher> getAll() {
        return teacherRepo.findAll();
    }

    public Teacher findLastAdded() {
        List<Teacher> teacherList = teacherRepo.findAll();
        Teacher lastAddedTeacher = teacherList.get(teacherList.size() - 1);
        return lastAddedTeacher;
    }

    public Teacher findById(Long Id) {
        return teacherRepo.findById(Id).get();

    }


    public DtoTeacher mapTeacherToDtoTeacher(Teacher teacher) {
        DtoTeacher dtoTeacher = DtoTeacher.builder().build();
        dtoTeacher.setId(teacher.getId());
        dtoTeacher.setName(teacher.getName());
        dtoTeacher.setSurName(teacher.getSurName());
        dtoTeacher.setTeachedSubject(teacher.getTeachedSubject());


        return dtoTeacher;
    }

    public Teacher mapDTOTeacherToTeacher(DtoTeacher dtoTeacher) {
        Teacher teacherToSave = Teacher.builder().build();
        teacherToSave.setName(dtoTeacher.getName());
        teacherToSave.setSurName(dtoTeacher.getSurName());
        teacherToSave.setTeachedSubject(dtoTeacher.getTeachedSubject());
        teacherToSave.setAssignedClasses(new ArrayList<>());
        ClassDetail classDetail;
        if (checkIfClassExists(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign())) {

            ClassDetail classDetail1 = classRepo.findByYearBookAndClassSign(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign()).get();
            boolean ifTeacherExistsForSubject = classDetail1.getAssignTeachers().stream().filter(e -> e.getTeachedSubject().equals(dtoTeacher.getTeachedSubject())).findFirst().isPresent();
            if (ifTeacherExistsForSubject) {
                return teacherToSave;
            } else
                classDetail = classDetail1;
        } else {
            classDetail = ClassDetail.builder().build();
            classDetail.setClassSign(dtoTeacher.getNewClassSign());
            classDetail.setYearBook(dtoTeacher.getNewYearBook());
            classDetail.setAssignTeachers(new ArrayList<>());
        }

        teacherToSave.addClass(classDetail);
        return teacherToSave;
    }

    public void delete(Long id) {
        teacherRepo.deleteById(id);
    }
    public void delete(Teacher teacher) {
        teacherRepo.delete(teacher);
    }
}