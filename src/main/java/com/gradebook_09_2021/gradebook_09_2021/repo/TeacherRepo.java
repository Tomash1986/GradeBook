package com.gradebook_09_2021.gradebook_09_2021.repo;

import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepo extends JpaRepository<Teacher,Long> {

    Optional<Teacher> findAllByNameAndSurName(String name, String surName);

    Optional<Teacher> findByNameAndSurName(String name, String surName);
}
