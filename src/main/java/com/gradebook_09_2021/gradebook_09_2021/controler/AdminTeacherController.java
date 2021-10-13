package com.gradebook_09_2021.gradebook_09_2021.controler;


import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.DtoTeacher;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.Teacher;
import com.gradebook_09_2021.gradebook_09_2021.service.AdminTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminTeacherController {


    @Autowired
    private AdminTeacherService adminTeacherService;


    @RequestMapping(value = "/admin-teacher/all-teachers", method = RequestMethod.GET)
    String showTeachers(Model model) {
        List<Teacher> teacherList = adminTeacherService.getAll();
        model.addAttribute("teacherList", teacherList);
        return "/admin-teacher/all-teachers";
    }


    @RequestMapping(value = "/admin-teacher/create-teacher", method = RequestMethod.GET)
    String createNewTeacher(Model model) {
        DtoTeacher dtoTeacher = DtoTeacher.builder().build();
        model.addAttribute("dtoTeacher", dtoTeacher);
        List<String> arrayList = new ArrayList<> (List.of("Ia", "Ib"));
        model.addAttribute("arrList", arrayList);
        return "/admin-teacher/create-teacher";
    }


    @RequestMapping(value = "/admin-teacher/add-teacher", method = RequestMethod.POST)
    String addTeacher(DtoTeacher dtoTeacher, Model model) {
        adminTeacherService.saveNew(dtoTeacher);
        Teacher lastAdded = adminTeacherService.findByNameAndSurName(dtoTeacher.getName(),dtoTeacher.getSurName());
        DtoTeacher dtoTeacher2 = adminTeacherService.mapTeacherToDtoTeacher(lastAdded);
        model.addAttribute("dtoTeacher", dtoTeacher2);
        model.addAttribute("addedTeacher", lastAdded);
        return "/admin-teacher/display-teacher";
    }


    @RequestMapping(value = "/admin-teacher/add-class", method = RequestMethod.POST)
    String addClassToTeacher(DtoTeacher dtoTeacher, Model model) {

        adminTeacherService.addClassToTeacher(dtoTeacher);

        Teacher teacher = adminTeacherService.findById(dtoTeacher.getId());
        DtoTeacher dtoTeacher2 = adminTeacherService.mapTeacherToDtoTeacher(teacher);
        model.addAttribute("dtoTeacher", dtoTeacher2);
        model.addAttribute("addedTeacher", teacher);
        return "/admin-teacher/display-teacher";
    }

    @RequestMapping(value = "/admin-teacher/add-class-menu", method = RequestMethod.POST)
    String addClassToTeacherFromMenu(@RequestParam(value = "teacherId") Long teacherId, Model model) {
        Teacher teacher = adminTeacherService.findById(teacherId);
        DtoTeacher dtoTeacher2 = adminTeacherService.mapTeacherToDtoTeacher(teacher);
        model.addAttribute("dtoTeacher", dtoTeacher2);
        model.addAttribute("addedTeacher", teacher);
        return "/admin-teacher/display-teacher";
    }
    @RequestMapping(value = "/admin-teacher/add-class-menu-extra", method = RequestMethod.POST)
    String addClassToTeacherFromMenuExtra(@RequestParam(value = "teacherId" ) Long teacherId,
                                          @RequestParam(value = "classToAssignId", required = false) Long classToAssignId, Model model) {
        if(classToAssignId!=null)
            adminTeacherService.addClassToTeacherFromMenuExtra(teacherId,classToAssignId);
        return "redirect:/admin-class/main-classes-list";
    }

    @RequestMapping(value = "/admin-teacher/delete-class-menu", method = RequestMethod.POST)
    String removeClassOFTeacherFromMenu(@RequestParam(value = "classId") Long classId,
                                        @RequestParam(value = "teacherId") Long teacherId) {
        adminTeacherService.removeClassFromTeacher(teacherId, classId);
        return "redirect:/admin-teacher/all-teachers";
    }

    @RequestMapping(value = "/admin-teacher/delete-class", method = RequestMethod.POST)
    String removeClassOFTeacher(@RequestParam(value = "classId") Long classId,
                                @RequestParam(value = "teacherId") Long teacherId, Model model) {
        adminTeacherService.removeClassFromTeacher(teacherId, classId);
        Teacher editedTeacher = adminTeacherService.findById(teacherId);
        DtoTeacher dtoTeacher1 = adminTeacherService.mapTeacherToDtoTeacher(editedTeacher);
        model.addAttribute("teacherToEdit", dtoTeacher1);
        return "/admin-teacher/edit-teacher";
    }

    @RequestMapping(value = "/admin-teacher/delete-teacher", method = RequestMethod.POST)
    String deleteTeacherFromMenu(@RequestParam(value = "teacherToDelId") Long teacherToDelId, Model model) {
        System.out.println(teacherToDelId);
        adminTeacherService.deleteTeacher(teacherToDelId);
        return "redirect:/admin-teacher/all-teachers";
    }

    @RequestMapping(value="/admin-teacher/edit-teacher-menu",method = RequestMethod.POST)
    String editTeacher(@RequestParam(value="teacherToEditId") Long teacherToEditId, Model model)
    {
        Teacher teacherToEdit = adminTeacherService.findById(teacherToEditId);
        DtoTeacher dtoTeacher = adminTeacherService.mapTeacherToDtoTeacher(teacherToEdit);
        model.addAttribute("teacherToEdit",dtoTeacher);
        return "/admin-teacher/edit-teacher";
    }

    @RequestMapping(value="/admin-teacher/edit-teacher-update-class",method = RequestMethod.POST)
    String updateTeacherClass(DtoTeacher dtoTeacher, Model model)
    {
        adminTeacherService.updateTeacherClass(dtoTeacher);
        Teacher editedTeacher = adminTeacherService.findById(dtoTeacher.getId());
        DtoTeacher dtoTeacher2 = adminTeacherService.mapTeacherToDtoTeacher(editedTeacher);
        model.addAttribute("teacherToEdit", dtoTeacher2);
        return "/admin-teacher/edit-teacher";
    }

    @RequestMapping(value="/admin-teacher/edit-teacher-update-name-surname",method = RequestMethod.POST)
    String upadteTeacherNameAndSurName(DtoTeacher dtoTeacher, Model model)
    {
        adminTeacherService.updateTeacherNameAndSurName(dtoTeacher);
        Teacher editedTeacher = adminTeacherService.findById(dtoTeacher.getId());
        DtoTeacher dtoTeacher1 = adminTeacherService.mapTeacherToDtoTeacher(editedTeacher);
        model.addAttribute("teacherToEdit", dtoTeacher1);
        return "/admin-teacher/edit-teacher";
    }

}
