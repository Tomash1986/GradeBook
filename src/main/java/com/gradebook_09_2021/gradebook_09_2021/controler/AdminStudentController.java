package com.gradebook_09_2021.gradebook_09_2021.controler;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Grade;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.ClassDetail;
import com.gradebook_09_2021.gradebook_09_2021.service.AdminStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class AdminStudentController {

    @Autowired
    private AdminStudentService adminStudentService;


    @RequestMapping(value = "/admin-student/all-classes", method = RequestMethod.GET)
    String showAvailableClassInSchool(Model model) {
        List<ClassDetail> allClassInSchool = adminStudentService.findAllClassInSchool();
        model.addAttribute("classesInSchool", allClassInSchool);
        return "/admin-student/all-classes";
    }

    @RequestMapping(value = "/admin-student/list-students", method = RequestMethod.POST)
    String showStudentOfSpecifClass(@RequestParam(value = "yearBook") YearBook yearBook,
                                    @RequestParam(value = "classSign") ClassSign classSign,
                                    Model model) {
        List<Student> studentList = adminStudentService.findStudsForSpecClass(yearBook, classSign);

        model.addAttribute("studentList", studentList);
        model.addAttribute("yearBook", yearBook);
        model.addAttribute("classSign", classSign);
        return "/admin-student/students-list";
    }

    @RequestMapping(value = "/admin-student/list-students", method = RequestMethod.GET)
    String showStudentOfSpecifClassGET(@RequestParam(value = "yearBook") YearBook yearBook,
                                    @RequestParam(value = "classSign") ClassSign classSign,
                                    Model model) {
        List<Student> studentList = adminStudentService.findStudsForSpecClass(yearBook, classSign);

        model.addAttribute("studentList", studentList);
        model.addAttribute("yearBook", yearBook);
        model.addAttribute("classSign", classSign);
        return "/admin-student/students-list";
    }

    @RequestMapping(value = "/admin-student/create-student", method = RequestMethod.POST)
    String createStudent(@RequestParam(value = "yearBook") YearBook yearBook,
                         @RequestParam(value = "classSign") ClassSign classSign,
                         Model model) {
        Student student = Student.builder().build();
        student.setYearBook(yearBook);
        student.setClassSign(classSign);
        student.setStudGradeBook(new ArrayList<>());
        student.getStudGradeBook().add(Grade.builder().subject(Subject.POL).build());
        student.getStudGradeBook().add(Grade.builder().subject(Subject.ANG).build());
        student.getStudGradeBook().add(Grade.builder().subject(Subject.MAT).build());
        System.out.println(student.getStudGradeBook());
        model.addAttribute("student", student);
        return "/admin-student/create-student";
    }

    @RequestMapping(value = "/admin-student/add-student", method = RequestMethod.POST)
    String addStudent(Student student, Model model) {
        Student saveStudent = adminStudentService.saveNewStudent(student);
        model.addAttribute("student", saveStudent);
        return "/admin-student/create-student";
    }

    @RequestMapping(value = "/admin-student/edit-student-menu", method = RequestMethod.POST)
    String editStudent(@RequestParam(value = "studentToEditId") Long studentToEditId, Model model) {
        Student editedStudent = adminStudentService.findById(studentToEditId);
        Map<Subject, List<Grade>> gradeBook = adminStudentService.getGradeBook(editedStudent);
        model.addAttribute("editedstudent", editedStudent);
        model.addAttribute("gradeBook", gradeBook);
        return "/admin-student/edit-student";
    }

    @RequestMapping(value = "/admin-student/update-student", method = RequestMethod.POST)
    String updateStudent(Student student, Model model) {
        Student studentById = adminStudentService.findById(student.getId());
        if (student.equals(studentById))
            System.out.println("tak");
        Student updatedStud = adminStudentService.updateStudentData(student);
        Map<Subject, List<Grade>> gradeBook = adminStudentService.getGradeBook(updatedStud);
        model.addAttribute("editedstudent", updatedStud);
        model.addAttribute("gradeBook", gradeBook);
        return "/admin-student/edit-student";
    }


    @RequestMapping(value = "/admin-student/delete-student", method = RequestMethod.POST)
    String deleteStudent(@RequestParam(value = "studentToDelId") Long studentToDelId,
                         @RequestParam(value = "yearBook") YearBook yearBook,
                         @RequestParam(value = "classSign") ClassSign classSign,
                         Model model ){
        adminStudentService.deleteStudent(studentToDelId);
        List<Student> studentList = adminStudentService.findStudsForSpecClass(yearBook, classSign);
        model.addAttribute("studentList", studentList);
        model.addAttribute("yearBook", yearBook);
        model.addAttribute("classSign", classSign);
        return "/admin-student/students-list";
    }

    @RequestMapping(value="/admin-student/add-student-grade",method= RequestMethod.POST)
    String addGradeToStudent(@RequestParam(value = "editedStudentId") Long editedStudentId,
                             @RequestParam(value = "newGrade") Integer newAddGrade,
                             @RequestParam(value = "subject" ) Subject subject,
                             Model model){
        Student editedStudent = adminStudentService.addGradeToStudent(editedStudentId, newAddGrade, subject);
        Map<Subject, List<Grade>> gradeBook = adminStudentService.getGradeBook(editedStudent);
        model.addAttribute("editedstudent", editedStudent);
        model.addAttribute("gradeBook", gradeBook);
        return "/admin-student/edit-student";
    }



    @RequestMapping(value = "/admin-student/update-student-grade", method = RequestMethod.POST)
    String upadateStudGrade(@RequestParam(value = "gradeId") Long gradeId,
                            @RequestParam(value = "newGrade") Integer newGrade,
                            @RequestParam(value = "editedStudentId") Long editedStudentId,
                            Model model) {
        adminStudentService.updateStudentGrade(gradeId, newGrade);
        Student editedStudent = adminStudentService.findById(editedStudentId);
        Map<Subject, List<Grade>> gradeBook = adminStudentService.getGradeBook(editedStudent);
        model.addAttribute("editedstudent", editedStudent);
        model.addAttribute("gradeBook", gradeBook);
        return "/admin-student/edit-student";
    }

    @RequestMapping(value = "/admin-student/delete-student-grade", method = RequestMethod.POST)
    String deleteStudGrade(@RequestParam(value = "gradeId") Long gradeId,
                           @RequestParam(value = "editedStudentId") Long editedStudentId,
                           Model model) {
        adminStudentService.deleteStudentGrade(gradeId);
        Student editedStudent = adminStudentService.findById(editedStudentId);
        Map<Subject, List<Grade>> gradeBook = adminStudentService.getGradeBook(editedStudent);
        model.addAttribute("editedstudent", editedStudent);
        model.addAttribute("gradeBook", gradeBook);
        return "/admin-student/edit-student";
    }


}
