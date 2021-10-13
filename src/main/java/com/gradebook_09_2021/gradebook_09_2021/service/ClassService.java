package com.gradebook_09_2021.gradebook_09_2021.service;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.ClassDetail;
import com.gradebook_09_2021.gradebook_09_2021.repo.ClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepo classRepo;


    public boolean checkIfExist(YearBook yearBook, ClassSign classSign) {
        Optional<ClassDetail> classDetails = classRepo.findByYearBookAndClassSign(yearBook, classSign);
        return classDetails.isPresent();
    }

    public ClassDetail getClassDetails(YearBook yearBook, ClassSign classSign){
        return classRepo.findByYearBookAndClassSign(yearBook, classSign).get();

    }
    public ClassDetail getClassById(Long id){
        return classRepo.findById(id).get();
    }
}
