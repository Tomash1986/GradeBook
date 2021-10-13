package com.gradebook_09_2021.gradebook_09_2021.service;


import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
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

@Service
public class AdminTeacherService {


    @Autowired
    private StudRepo studRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private ClassRepo classRepo;

//      Scenariusze
//      1-Nie ma nauczyciela jest klasa
//          -tworze nauczyciela i podpinam pod klase
//              -warunek dana klasa nie ma przypisanego nauczyciela z danego przedmiotu
//      2-Jest nauczyciel nie ma klasy(dodaje nastepna klase do nauczyciela)
//              -tworze nowa klase i dodaje do nauczyciela
//      3-Jest nayczyciel i jest klasa(dodaje nastepna klase do nauczyciela)
//              -warunek istniejaca klasa nie ma przypisanego nauczyciela z danego przedmiotu

    public void saveNew(DtoTeacher dtoTeacher) {
        boolean teacherExists = checkIfTeacherExists(dtoTeacher.getName(), dtoTeacher.getSurName());
        boolean classExists = checkIfClassExists(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign());
        boolean teacherExistsForSpecificSubjectInClass = checkIfTeacherAddedForSpecificSubjectToClass(
                dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign(), dtoTeacher.getTeachedSubject());
//        if (classExists) {
//            ClassDetails classDetails = classRepo.findByYearBookAndClassSign(dtoTeacher.getYearBook(), dtoTeacher.getClassSign()).get();
//            teacherExistsForSpecificSubjectInClass = checkIfTeacherAddedForSpecificSubjectToClass(classDetails, dtoTeacher.getTeachedSubject());
//        }
        if (!teacherExists & !classExists) {
//            Przypadek 1:Jesli nie istnieje Teacher i Klasa to Tworzę Nauczyciela i klase
            Teacher teacherToSave = Teacher.builder().build();
            teacherToSave.setName(dtoTeacher.getName());
            teacherToSave.setSurName(dtoTeacher.getSurName());
            teacherToSave.setTeachedSubject(dtoTeacher.getTeachedSubject());
            teacherToSave.setAssignedClasses(new ArrayList<>());

            ClassDetail classDetail = ClassDetail.builder().build();
            classDetail.setYearBook(dtoTeacher.getNewYearBook());
            classDetail.setClassSign(dtoTeacher.getNewClassSign());
            classDetail.setAssignTeachers(new ArrayList<>());
            teacherToSave.addClass(classDetail);
            teacherRepo.save(teacherToSave);

        } else if (!teacherExists & classExists & !teacherExistsForSpecificSubjectInClass) {
//            Przypadek 2:Nie istniej Nauczyciel ale istnieje klasa i nie ma przypisanego do tego przedmiotu nauczyciela
//            Tworze nowego nauczyceila na podstawie DToTeacher pobieram klase oraz wiaze na konie i zapisuje nuczyciela
            Teacher teacherToSave = Teacher.builder().build();
            teacherToSave.setName(dtoTeacher.getName());
            teacherToSave.setSurName(dtoTeacher.getSurName());
            teacherToSave.setTeachedSubject(dtoTeacher.getTeachedSubject());
            teacherToSave.setAssignedClasses(new ArrayList<>());

            ClassDetail classDetail = classRepo.findByYearBookAndClassSign(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign()).get();
            teacherToSave.addClass(classDetail);
            teacherRepo.save(teacherToSave);

        } else if (!teacherExists & classExists & teacherExistsForSpecificSubjectInClass) {
//            Przypadek 3:Nie istniej Nauczyciel ale istniej klasa i ma juz przydzielonego nauczyciela
//            Tworze nowego nauczyciela ale nie przypisuje mu zadnej classy
            Teacher teacherToSave = Teacher.builder().build();
            teacherToSave.setName(dtoTeacher.getName());
            teacherToSave.setSurName(dtoTeacher.getSurName());
            teacherToSave.setTeachedSubject(dtoTeacher.getTeachedSubject());
            teacherToSave.setAssignedClasses(new ArrayList<>());
            teacherRepo.save(teacherToSave);
        }

    }



    //    Metoda sprawdzajaca mozliwosc dodania do klasy wiecej niz jednego nauczyciela dla danego przedmiotu
    public void addClassToTeacher(DtoTeacher dtoTeacher) {
//        Przypadek 1. Nie ma isieje klasa -tworze nowa klase i dodaje ja do istnejacego nauczycieal
//        Przypadke 2. Istnieje klsa ale nie ma przypisanego nauczyciela z danego Przedmiotu
//        Przypadek 3. Istnieje klasa i ma przypisanego nauczyciela z Danego Przedmiotu Nie robie nic
        boolean classExists = checkIfClassExists(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign());
        boolean teacherExistsForSpecificSubjectInClass = checkIfTeacherAddedForSpecificSubjectToClass(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign(), dtoTeacher.getTeachedSubject());
//        Przypadek 1
        if (!classExists) {
            Teacher teacher = teacherRepo.findById(dtoTeacher.getId()).get();

            ClassDetail classDetail = ClassDetail.builder().build();
            classDetail.setYearBook(dtoTeacher.getNewYearBook());
            classDetail.setClassSign(dtoTeacher.getNewClassSign());
            classDetail.setAssignTeachers(new ArrayList<>());
            teacher.addClass(classDetail);
            teacherRepo.save(teacher);
        }
//        Przypadek 2
        else if (classExists & !teacherExistsForSpecificSubjectInClass) {
            Teacher teacher = teacherRepo.findById(dtoTeacher.getId()).get();
            ClassDetail classDetail = classRepo.findByYearBookAndClassSign(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign()).get();
            teacher.addClass(classDetail);
            teacherRepo.save(teacher);
        }

    }

    private boolean checkIfTeacherAddedForSpecificSubjectToClass(YearBook yearBook, ClassSign classSign, Subject subject) {
        //        Trzeba sprawdzic czy po pierwsze klasa istnieje po drugie czy ma juz przypisanego nauczyciela dla danego przedmiotu
        boolean ifClassExists = checkIfClassExists(yearBook, classSign);
        boolean ifTeacherExistsForSubject;
        if (ifClassExists) {
            ClassDetail classDetail1 = classRepo.findByYearBookAndClassSign(yearBook, classSign).get();
            ifTeacherExistsForSubject = classDetail1.getAssignTeachers().stream().filter(e -> e.getTeachedSubject().equals(subject)).findFirst().isPresent();
            if (ifTeacherExistsForSubject) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean checkIfClassExists(YearBook yearBook, ClassSign classSign) {
        Optional<ClassDetail> classDetails = classRepo.findByYearBookAndClassSign(yearBook, classSign);
        return classDetails.isPresent();
    }

    private boolean checkIfTeacherExists(String name, String surName) {
        Optional<Teacher> byNameAndSurName = teacherRepo.findByNameAndSurName(name, surName);
        return byNameAndSurName.isPresent();
    }

    public Teacher findByNameAndSurName(String name, String surName) {
        return teacherRepo.findByNameAndSurName(name, surName).get();
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepo.delete(teacher);
    }

    public Teacher findById(Long id) {
        return teacherRepo.findById(id).get();
    }

    public DtoTeacher mapTeacherToDtoTeacher(Teacher teacher) {
        DtoTeacher dtoTeacher = DtoTeacher.builder().build();
        dtoTeacher.setId(teacher.getId());
        dtoTeacher.setName(teacher.getName());
        dtoTeacher.setSurName(teacher.getSurName());
        dtoTeacher.setTeachedSubject(teacher.getTeachedSubject());
        dtoTeacher.setClassDetailList(teacher.getAssignedClasses());

        return dtoTeacher;
    }

    private boolean checkIfTeacherAddedForSpecificSubjectToClass(ClassDetail classDetail, Subject subject) {
        //        Trzeba sprawdzic czy po pierwsze klasa istnieje po drugie czy ma juz przypisanego nauczyciela dla danego przedmiotu
        boolean ifClassExists = checkIfClassExists(classDetail.getYearBook(), classDetail.getClassSign());
        boolean ifTeacherExistsForSubject;
        if (ifClassExists) {
            ClassDetail classDetail1 = classRepo.findByYearBookAndClassSign(classDetail.getYearBook(), classDetail.getClassSign()).get();
            ifTeacherExistsForSubject = classDetail1.getAssignTeachers().stream().filter(e -> e.getTeachedSubject().equals(subject)).findFirst().isPresent();
            if (ifTeacherExistsForSubject) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void saveNew(Teacher teacher) {
        teacherRepo.save(teacher);
    }

    public List<Teacher> getAll() {
        return teacherRepo.findAll();
    }

    public void updateTeacherClass(DtoTeacher dtoTeacher) {
        boolean teacherExistsById = teacherRepo.existsById(dtoTeacher.getId());
        boolean teacherExists = checkIfTeacherExists(dtoTeacher.getName(), dtoTeacher.getSurName());
        boolean classExists = checkIfClassExists(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign());
        boolean teacherExistsForSpecificSubjectInClass = checkIfTeacherAddedForSpecificSubjectToClass(
                dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign(), dtoTeacher.getTeachedSubject());

        if (teacherExists || teacherExistsById) {
//      Taki warunek moze byc albo moze go nie byc bo wiadomo że istniej taki teacher
            Teacher teacher = teacherRepo.getOne(dtoTeacher.getId());
            if (!classExists) {
//              To trzeba jakos przemyslec
                ClassDetail classToAssign = ClassDetail.builder().
                        yearBook(dtoTeacher.getNewYearBook()).classSign(dtoTeacher.getNewClassSign()).assignTeachers(new ArrayList<>()).build();
                teacher.addClass(classToAssign);
                teacherRepo.save(teacher);

            } else if (classExists & !teacherExistsForSpecificSubjectInClass) {
                ClassDetail classDetail = classRepo.findByYearBookAndClassSign(dtoTeacher.getNewYearBook(), dtoTeacher.getNewClassSign()).get();
                teacher.addClass(classDetail);
                teacherRepo.save(teacher);
            }
        }
//      3-Jest nayczyciel i jest klasa(dodaje nastepna klase do nauczyciela)
//              -warunek istniejaca klasa nie ma przypisanego nauczyciela z danego przedmiotu


    }

    public void updateTeacherNameAndSurName(DtoTeacher dtoTeacher) {
        boolean teacherExistsById = teacherRepo.existsById(dtoTeacher.getId());
        if ( teacherExistsById){
            Teacher teacher = teacherRepo.getOne(dtoTeacher.getId());
            
            teacher.setName(dtoTeacher.getName());
            teacher.setSurName(dtoTeacher.getSurName());
            teacherRepo.save(teacher);
        }
    }




    public void removeClassFromTeacher(Long teacherId, Long classId) {
        Teacher teacher = teacherRepo.getOne(teacherId);
        teacher.removeClass(classRepo.getOne(classId));
        teacherRepo.save(teacher);
    }

    public void deleteTeacher(Long teacherToDelId) {
        Teacher teacherToDel = teacherRepo.getOne(teacherToDelId);
        teacherToDel.removeAllClasses();
        teacherRepo.delete(teacherToDel);
    }


    public void addClassToTeacherFromMenuExtra(Long teacherId, Long classId){
        Teacher teacher = teacherRepo.getOne(teacherId);
        ClassDetail classDetail = classRepo.getOne(classId);

        teacher.addClass(classDetail);
        teacherRepo.save(teacher);
    }
}
