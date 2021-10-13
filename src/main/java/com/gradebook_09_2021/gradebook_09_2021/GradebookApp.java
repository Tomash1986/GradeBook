package com.gradebook_09_2021.gradebook_09_2021;

import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.ClassDetail;
import com.gradebook_09_2021.gradebook_09_2021.repo.ClassRepo;
import com.gradebook_09_2021.gradebook_09_2021.repo.StudRepo;
import com.gradebook_09_2021.gradebook_09_2021.utils.studentFactory.SchoolFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@SpringBootApplication
public class GradebookApp implements CommandLineRunner {

    @Autowired
    private StudRepo studRepo;

    @Autowired
    private ClassRepo classRepo;



    public static void main(String[] args) {
        SpringApplication.run(GradebookApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        SchoolFactory schoolFactory = new SchoolFactory();

        List<Student> schoolStudentList = schoolFactory.getSchoolStudentList();



        for (Student student : schoolStudentList) {
            studRepo.save(student);
        }

        List<Student> all = studRepo.findAll();
        for (Student student : all) {
            System.out.println(student);
        }

        List<ClassDetail> allClassInSchool = findAllClassInSchool();
        for (ClassDetail classDetail : allClassInSchool) {
            classRepo.save(classDetail);
        }
        List<ClassDetail> classRepoAll = classRepo.findAll();

        for (ClassDetail classDetail : classRepoAll) {
            System.out.println(classDetail);
        }


    }

    private List<ClassDetail> findAllClassInSchool() {
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
}
