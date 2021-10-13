package com.gradebook_09_2021.gradebook_09_2021.controler;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.DTOGrade;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.DtoStudent;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Grade;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;
import com.gradebook_09_2021.gradebook_09_2021.service.GradeService;
import com.gradebook_09_2021.gradebook_09_2021.service.StudentService;
import com.gradebook_09_2021.gradebook_09_2021.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private GradeService gradeService;

    //http://localhost:8080/teacher/gradeForSpecClass?yearBook=I&classSign=a&subject=POL.
    @RequestMapping(value = "/teacher/gradeForSpecClass", method = RequestMethod.GET)
    String getGradesForSpecificClass(@RequestParam(value="yearBook") YearBook yearBook,
                                     @RequestParam(value="classSign") ClassSign classSign,
                                     @RequestParam(value="subject") Subject subject,
                                     Model model){
        List<DtoStudent> studentAndTheirGradsForSpecifcSubject = teacherService.getStudentAndTheirGradsForSpecifcSubject(yearBook, classSign, subject);
        model.addAttribute("studentsGrades",studentAndTheirGradsForSpecifcSubject);
        model.addAttribute("yearBook",yearBook);
        model.addAttribute("classSign",classSign);
        model.addAttribute("subject",subject);
        return "/teacher/gradeForSpecClass";
    }

    @GetMapping(value = "/teacher/add")
    String addGrade(@RequestParam(value="yearBook") YearBook yearBook,
                    @RequestParam(value="classSign") ClassSign classSign,
                    @RequestParam(value="subject") Subject subject,
                    @RequestParam(value = "id") Long id,
                    Model model){
        Grade newGrade = Grade.builder().build();
        newGrade.setSubject(subject);
        Student student = studentService.getStudentById(id);
        student.getStudGradeBook().add(newGrade);
        studentService.save(student);
        Student studentAftAdd = studentService.getStudentById(id);
        Grade editedGrade = studentAftAdd.getStudGradeBook().get(studentAftAdd.getStudGradeBook().size() - 1);


        DTOGrade editedDtoGrade = gradeService.mapGradeToDtoGrade(editedGrade, yearBook, classSign);
        model.addAttribute("editedDtoGrade",editedDtoGrade);
        model.addAttribute("yearBook",editedDtoGrade.getYearBook());
        model.addAttribute("classSign",editedDtoGrade.getClassSign());
        model.addAttribute("subject",editedDtoGrade.getSubject());

        return "/teacher/add-grades";
    }


    @RequestMapping(value = "/teacher/usun", method = RequestMethod.GET)
    String deleteGrade(@RequestParam(value="yearBook") YearBook yearBook,
                       @RequestParam(value="classSign") ClassSign classSign,
                       @RequestParam(value="subject") Subject subject,
                       @RequestParam(value = "id") Long id,
                       Model model){
        gradeService.delete(id);
        List<DtoStudent> studentAndTheirGradsForSpecifcSubject = teacherService.getStudentAndTheirGradsForSpecifcSubject(yearBook, classSign, subject);
        model.addAttribute("studentsGrades",studentAndTheirGradsForSpecifcSubject);
        model.addAttribute("yearBook",yearBook);
        model.addAttribute("classSign",classSign);
        model.addAttribute("subject",subject);
        return "/teacher/gradeForSpecClass";
    }

    @RequestMapping(value = "/teacher/edit", method = RequestMethod.POST)
    String editGrade(DTOGrade dtoGrade, Model model){
        Grade editedGrade = gradeService.mapDtoGradeToGrade2(dtoGrade);
        gradeService.save(editedGrade);
        model.addAttribute("yearBook",dtoGrade.getYearBook());
        model.addAttribute("classSign",dtoGrade.getClassSign());
        model.addAttribute("subject",dtoGrade.getSubject());
        return "/teacher/grade-changed-conf";
    }



    @GetMapping ("/teacher/edit")
    public String addTrainer(@RequestParam(value="yearBook") YearBook yearBook,
                             @RequestParam(value="classSign") ClassSign classSign,
                             @RequestParam(value="subject") Subject subject,
                             @RequestParam(value = "id") Long id,
                             Model model){
        Grade editedGrade = gradeService.edit(id);
        DTOGrade editedDtoGrade = gradeService.mapGradeToDtoGrade(editedGrade, yearBook, classSign);
        model.addAttribute("editedDtoGrade",editedDtoGrade);
        model.addAttribute("yearBook",editedDtoGrade.getYearBook());
        model.addAttribute("classSign",editedDtoGrade.getClassSign());
        model.addAttribute("subject",editedDtoGrade.getSubject());
        return "/teacher/edit-grades";
    }









}
