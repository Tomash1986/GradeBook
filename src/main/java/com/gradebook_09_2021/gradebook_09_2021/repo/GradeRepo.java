package com.gradebook_09_2021.gradebook_09_2021.repo;


import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepo extends JpaRepository<Grade, Long> {
}
