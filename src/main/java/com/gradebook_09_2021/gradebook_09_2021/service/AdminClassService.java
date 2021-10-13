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
import java.util.stream.Collectors;

@Service
public class AdminClassService {

    @Autowired
    private StudRepo studRepo;

    @Autowired
    private ClassRepo classRepo;

    @Autowired
    private TeacherRepo teacherRepo;
    

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

    public List <ClassDetail>findAlLClasses(){
        return classRepo.findAll();
    }
    public ClassDetail findById(Long classId) {
        return classRepo.getOne(classId);
    }

    public List<Teacher> getAssignTeachers(Long classId) {
        return classRepo.getOne(classId).getAssignTeachers();
    }

    public List<ClassDetail> classWithoutAssignedTeacherToSpecSubject(Subject subject){

        return classRepo.findAll()
                .stream()
                .filter(e->e.getAssignTeachers().stream().filter(o->o.getTeachedSubject().equals(subject)).findFirst().isEmpty())
                .collect(Collectors.toList());
    }

    public List<DtoTeacher> findAvailableTeachers(){
         return teacherRepo.findAll().stream().map(e-> {
            if(e.getTeachedSubject().equals(Subject.POL)){
                List<ClassDetail> classDetails = classWithoutAssignedTeacherToSpecSubject(Subject.POL);
                DtoTeacher dtoTeacher = mapTeacherToDtoTeacher(e);
                dtoTeacher.setClassWithoutAsignedTeacherToSubject(classDetails);
                return dtoTeacher;
            }
            else if(e.getTeachedSubject().equals(Subject.MAT)){
                List<ClassDetail> classDetails = classWithoutAssignedTeacherToSpecSubject(Subject.MAT);
                DtoTeacher dtoTeacher = mapTeacherToDtoTeacher(e);
                dtoTeacher.setClassWithoutAsignedTeacherToSubject(classDetails);
                return dtoTeacher;
            }else {
                List<ClassDetail> classDetails = classWithoutAssignedTeacherToSpecSubject(Subject.ANG);
                DtoTeacher dtoTeacher = mapTeacherToDtoTeacher(e);
                dtoTeacher.setClassWithoutAsignedTeacherToSubject(classDetails);
                return dtoTeacher;
            }
        }).collect(Collectors.toList());
    }


    private DtoTeacher mapTeacherToDtoTeacher(Teacher teacher) {
        DtoTeacher dtoTeacher = DtoTeacher.builder().build();
        dtoTeacher.setId(teacher.getId());
        dtoTeacher.setName(teacher.getName());
        dtoTeacher.setSurName(teacher.getSurName());
        dtoTeacher.setTeachedSubject(teacher.getTeachedSubject());
        dtoTeacher.setClassDetailList(teacher.getAssignedClasses());

        return dtoTeacher;
    }

    public void saveTeacher(Teacher teacher) {
        Teacher teacherFroDB = teacherRepo.getOne(teacher.getId());
        Teacher teacherInDB = findTeacherInDB(teacher.getId());
        if(teacherInDB.getTeachedSubject()!=teacher.getTeachedSubject()){
            teacher.removeAllClasses();
            teacherRepo.save(teacher);
        } else
            teacherRepo.save(teacher);
    }


    private Teacher findTeacherInDB(Long id){
        return teacherRepo.getOne(id);
    }

    public void saveTeacher(DtoTeacher dtoTeacher) {
        Teacher teacherFromDB = teacherRepo.getOne(dtoTeacher.getId());
        if(teacherFromDB.getTeachedSubject()!=dtoTeacher.getTeachedSubject()){
            teacherFromDB.removeAllClasses();
            teacherFromDB.setName(dtoTeacher.getName());
            teacherFromDB.setSurName(dtoTeacher.getSurName());
            teacherFromDB.setTeachedSubject(dtoTeacher.getTeachedSubject());
            teacherRepo.save(teacherFromDB);
        }else
        {
            teacherFromDB.setName(dtoTeacher.getName());
            teacherFromDB.setSurName(dtoTeacher.getSurName());
            teacherRepo.save(teacherFromDB);
        }
    }


    List<Student> getStudentsAndTheirGrades(YearBook yearBook, ClassSign classSign) {
        return studRepo.findByYearBookAndClassSign(yearBook, classSign);
    }


    public List<DtoStudent> getStudentsAndTheirGradsForSpecificSubject(YearBook yearBook, ClassSign classSign, Subject subject) {

        List<Student> students = getStudentsAndTheirGrades(yearBook, classSign);
        List<DtoStudent> listofGradesOfSubject = new ArrayList<>();
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
}
