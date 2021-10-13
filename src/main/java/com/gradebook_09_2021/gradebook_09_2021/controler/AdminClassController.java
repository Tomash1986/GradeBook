package com.gradebook_09_2021.gradebook_09_2021.controler;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.DtoStudent;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.ClassDetail;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.DtoTeacher;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.Teacher;
import com.gradebook_09_2021.gradebook_09_2021.service.AdminClassService;
import com.gradebook_09_2021.gradebook_09_2021.service.AdminStudentService;
import com.gradebook_09_2021.gradebook_09_2021.service.AdminTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminClassController {

    @Autowired
    private AdminClassService adminClassService;
    @Autowired
    private AdminTeacherService adminTeacherService;
    @Autowired
    private AdminStudentService adminStudentService;


//    http://localhost:8080/admin-class/main-classes-list

    @RequestMapping(value = "/admin-class/main-classes-list", method = RequestMethod.GET)
    String showAvailableClassInSchool(Model model) {
        List<ClassDetail> allClassInSchool = adminClassService.findAlLClasses();
        model.addAttribute("classesInSchool", allClassInSchool);
        List<DtoTeacher> availableTeachers = adminClassService.findAvailableTeachers();
        model.addAttribute("teacherList", availableTeachers);
        model.addAttribute("classesWithoutPOL",adminClassService.classWithoutAssignedTeacherToSpecSubject(Subject.POL));
        model.addAttribute("classesWithoutMAT",adminClassService.classWithoutAssignedTeacherToSpecSubject(Subject.MAT));
        model.addAttribute("classesWithoutANG",adminClassService.classWithoutAssignedTeacherToSpecSubject(Subject.ANG));
        return "/admin-class/main-classes-list";
    }

    @RequestMapping(value = "/admin-class/create-teacher", method = RequestMethod.GET)
    String createNewTeacherInMainAdminPanel(Model model) {
        DtoTeacher dtoTeacher = DtoTeacher.builder().build();
        model.addAttribute("dtoTeacher", dtoTeacher);
        List<ClassDetail> allClassInSchool = adminClassService.findAlLClasses();
        model.addAttribute("classesInSchool", allClassInSchool);
        List<DtoTeacher> availableTeachers = adminClassService.findAvailableTeachers();
        model.addAttribute("teacherList", availableTeachers);
        return "/admin-class/main-classes-list-add-teacher";
    }

    @RequestMapping(value = "/admin-class/list-students", method = RequestMethod.POST)
    String showStudentsOfSpecficClass(@RequestParam(value = "yearBook") YearBook yearBook,
                                    @RequestParam(value = "classSign") ClassSign classSign,
                                    Model model) {
        List<Student> studentList = adminStudentService.findStudsForSpecClass(yearBook, classSign);
        List<ClassDetail> allClassInSchool = adminClassService.findAlLClasses();
        model.addAttribute("classesInSchool", allClassInSchool);
        List<DtoTeacher> availableTeachers = adminClassService.findAvailableTeachers();
        model.addAttribute("teacherList", availableTeachers);

        model.addAttribute("studentList", studentList);
        model.addAttribute("yearBook", yearBook);
        model.addAttribute("classSign", classSign);
        return "/admin-class/main-classes-list-spec-class-details";
    }

    @RequestMapping(value = "/admin-class/class-grades-for-subject", method = RequestMethod.POST)
    String showGradesOfClassForRequestedSubject(@RequestParam(value = "yearBook") YearBook yearBook,
                                                @RequestParam(value = "classSign") ClassSign classSign,
                                                @RequestParam(value="subject") Subject subject,
                                                Model model){
        List<ClassDetail> allClassInSchool = adminClassService.findAlLClasses();
        model.addAttribute("classesInSchool", allClassInSchool);
        List<DtoStudent> studentAndTheirGradsForSpecifcSubject = adminClassService.getStudentsAndTheirGradsForSpecificSubject(yearBook, classSign, subject);
        model.addAttribute("studentsGrades",studentAndTheirGradsForSpecifcSubject);
        model.addAttribute("yearBook",yearBook);
        model.addAttribute("classSign",classSign);
        model.addAttribute("subject",subject);
        model.addAttribute("availableGrades",List.of(1,2,3,4,5,6) );
        return "/admin-class/main-classes-grades-list-for-spec-subject";
    }



    @RequestMapping(value = "/admin-class/add-teacher", method = RequestMethod.POST)
    String addTeacher(DtoTeacher dtoTeacher, Model model) {
//      Z ksiazki horsmana wynia ze enumy porownuje sie inczej niz oniekty !!! Przez ==
        if(!dtoTeacher.getName().isEmpty() && !dtoTeacher.getSurName().isEmpty() && (dtoTeacher.getTeachedSubject()==Subject.POL|| dtoTeacher.getTeachedSubject()==Subject.ANG || dtoTeacher.getTeachedSubject()==Subject.MAT))
            adminTeacherService.saveNew(dtoTeacher);

        return "redirect:/admin-class/main-classes-list";
    }

    @RequestMapping(value="/admin-class/edit-teacher", method=RequestMethod.POST)
    String editTeacherFromClassMenu(@RequestParam(value = "teacherToEditId") Long teacherId, Model model) {
        Teacher teacher = adminTeacherService.findById(teacherId);
        DtoTeacher dtoTeacher = adminTeacherService.mapTeacherToDtoTeacher(teacher);
        model.addAttribute("teacherToEdit",dtoTeacher);
        List<ClassDetail> allClassInSchool = adminClassService.findAlLClasses();
        model.addAttribute("classesInSchool", allClassInSchool);
        List<DtoTeacher> availableTeachers = adminClassService.findAvailableTeachers();
        model.addAttribute("teacherList", availableTeachers);

        return "/admin-class/main-classes-list-edit-teacher";
    }

    @RequestMapping(value = "/admin-class/edited-teacher-update",method=RequestMethod.POST)
    String updateEditedTeacherFromClassMenu(DtoTeacher dtoTeacher){
        adminClassService.saveTeacher(dtoTeacher);
        return "redirect:/admin-class/main-classes-list";
    }

    @RequestMapping(value = "/admin-class/add-class-menu", method = RequestMethod.POST)
    String addClassToTeacherFromMenu(@RequestParam(value = "classId") Long classId, Model model) {
        List<Teacher> assignTeachers = adminClassService.getAssignTeachers(classId);
        model.addAttribute("assignTeachers", assignTeachers);
        return "/admin-class/display-classDetails";
    }

    @RequestMapping(value = "/admin-class/delete-class-menu", method = RequestMethod.POST)
    String removeClassOFTeacherFromMenu(@RequestParam(value = "classId") Long classId,
                                        @RequestParam(value = "teacherId") Long teacherId) {
        adminTeacherService.removeClassFromTeacher(teacherId, classId);
        return "redirect:/admin-class/main-classes-list";
    }

    @RequestMapping(value = "/admin-class/delete-teacher", method = RequestMethod.POST)
    String deleteTeacherFromMenu(@RequestParam(value = "teacherToDelId") Long teacherToDelId, Model model) {
        adminTeacherService.deleteTeacher(teacherToDelId);
        return "redirect:/admin-class/main-classes-list";

    }


}
