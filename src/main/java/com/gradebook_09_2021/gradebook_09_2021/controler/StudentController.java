package com.gradebook_09_2021.gradebook_09_2021.controler;

import com.gradebook_09_2021.gradebook_09_2021.service.GradeService;
import com.gradebook_09_2021.gradebook_09_2021.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeService gradeService;





    @GetMapping("/student/lista")
    public String studentList(Model model) {
        model.addAttribute("students", studentService.getAllSchoolStudents());

        return "student/student-list";
    }

    @GetMapping("/student/studentdetails")
    public String studentDetailedList(Model model) {
        model.addAttribute("students", studentService.getAllSchoolStudents());
        return "student/studentdetails-list";
    }


}
