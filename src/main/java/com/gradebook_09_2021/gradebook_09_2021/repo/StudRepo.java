package com.gradebook_09_2021.gradebook_09_2021.repo;



import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudRepo extends JpaRepository<Student,Long> {

  List<Student> findByYearBookAndClassSign(YearBook yearBook, ClassSign classSign);

  Optional<Student> findByNameAndSurName(String name, String surName);

}
