package com.gradebook_09_2021.gradebook_09_2021.service;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Grade;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.ClassDetail;
import com.gradebook_09_2021.gradebook_09_2021.repo.GradeRepo;
import com.gradebook_09_2021.gradebook_09_2021.repo.StudRepo;
import com.gradebook_09_2021.gradebook_09_2021.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class AdminStudentService {

    @Autowired
    private StudRepo studRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private GradeRepo gradeRepo;

    public List<ClassDetail> findAllClassInSchool() {
        List<Student> allStudnets = studRepo.findAll();
        List<ClassDetail> classList = allStudnets
                .stream()
                .map(e -> {
                    ClassDetail classDetail = new ClassDetail();
                    classDetail.setYearBook(e.getYearBook());
                    classDetail.setClassSign(e.getClassSign());
                    return classDetail;
                    })
                .distinct()
                .sorted((o1,o2)-> {
                    if(o1.getYearBook().compareTo(o2.getYearBook())==0){
                        return o1.getClassSign().compareTo(o2.getClassSign());
                    }else
                        return o1.getYearBook().compareTo(o2.getYearBook());
                })
                .collect(Collectors.toList());

        return classList;
    }

    public List<Student> findStudsForSpecClass(YearBook yearBook, ClassSign classSign) {
        List<Student> studentList = studRepo
                .findByYearBookAndClassSign(yearBook, classSign).stream()
                .sorted((o1, o2) -> o1.getSurName().compareTo(o2.getSurName()))
                .collect(Collectors.toList());

        return studentList;
    }

    public Student saveNewStudent(Student student) {
        if (!checkIfStudentExitsInSpecClass(student)) {
            return studRepo.save(student);
        }
        return student;
    }

    public Student updateStudentData(Student student) {
        Student studFromDB = studRepo.findById(student.getId()).get();
        if (studFromDB.getYearBook().equals(student.getYearBook())) {
            if (student.getName() != null) {
                studFromDB.setName(student.getName());
            }
            if (student.getSurName() != null) {
                studFromDB.setSurName(student.getSurName());
            }
            studFromDB.setClassSign(student.getClassSign());

            return studRepo.save(studFromDB);
        } else
//            Return student without moficiation
            return studFromDB;

    }

    private boolean checkIfStudentExitsInSpecClass(Student student) {
        return studRepo.findByNameAndSurName(student.getName(), student.getSurName()).isPresent();
    }

    public Student findById(Long studentToEditId) {
        Student student = studRepo.findById(studentToEditId).get();
        Map<Subject, List<Grade>> subjectListMap = student.getStudGradeBook().stream().
                collect(Collectors.groupingBy(Grade::getSubject));
        return student;
    }

    public Map<Subject, List<Grade>> getGradeBook(Student student) {

        Map<Subject, List<Grade>> subjectListMap =  student.getStudGradeBook().stream().
                collect(Collectors.groupingBy(Grade::getSubject));
        boolean subjectMATExists = subjectListMap.containsKey(Subject.MAT);
        boolean subjectPOLExists = subjectListMap.containsKey(Subject.POL);
        boolean subjectENGExists = subjectListMap.containsKey(Subject.ANG);
        if(!subjectMATExists & !subjectPOLExists & !subjectENGExists){
            subjectListMap.put(Subject.POL, new ArrayList<>());
            subjectListMap.put(Subject.ANG, new ArrayList<>());
            subjectListMap.put(Subject.MAT, new ArrayList<>());
        }
        else if (subjectMATExists & !subjectPOLExists & !subjectENGExists) {
            subjectListMap.put(Subject.POL, new ArrayList<>());
            subjectListMap.put(Subject.ANG, new ArrayList<>());
        } else if(subjectPOLExists & !subjectMATExists & !subjectENGExists){
            subjectListMap.put(Subject.MAT, new ArrayList<>());
            subjectListMap.put(Subject.ANG, new ArrayList<>());
        } else if(subjectENGExists & !subjectPOLExists & !subjectMATExists){
            subjectListMap.put(Subject.MAT, new ArrayList<>());
            subjectListMap.put(Subject.POL, new ArrayList<>());
        } else if(subjectENGExists & subjectPOLExists & !subjectMATExists){
            subjectListMap.put(Subject.MAT, new ArrayList<>());
        } else if(subjectENGExists & !subjectPOLExists & subjectMATExists){
            subjectListMap.put(Subject.POL, new ArrayList<>());
        } else if(!subjectENGExists & subjectPOLExists & subjectMATExists){
            subjectListMap.put(Subject.ANG, new ArrayList<>());
        }

        Map<Subject, List<Grade>> treeMap = new TreeMap<>(subjectListMap);
        subjectListMap.putAll(treeMap);

        return treeMap;
    }

    public Grade findGradeById(Long id) {
        Grade grade = gradeRepo.findById(id).get();
        return grade;
    }

    public Grade saveGrade(Grade grade) {
        return gradeRepo.save(grade);
    }

    public Student addGradeToStudent(Long studentId, Integer grade, Subject subject) {
        Student studentDB = studRepo.findById(studentId).get();
        List<Grade> studGradeBook = studentDB.getStudGradeBook();
        if (grade != null) {
            if (grade > 0 & grade < 7) {
                Grade newGrade = Grade.builder().grade(grade).subject(subject).build();
                studGradeBook.add(newGrade);
                return studRepo.save(studentDB);
            }
        }
        return studentDB;
    }

    public void updateStudentGrade(Long gradeId, Integer grade) {

        if (grade != null) {
            if ((grade > 0 & grade < 7)) {
                Grade editedGrade = gradeRepo.findById(gradeId).get();
                editedGrade.setGrade(grade);
                gradeRepo.save(editedGrade);
            }
        }
    }

    public void deleteStudentGrade(Long gradeId) {
        gradeRepo.deleteById(gradeId);
    }

    public void deleteStudent(Long studentToDelId) {
        studRepo.deleteById(studentToDelId);
    }
}
