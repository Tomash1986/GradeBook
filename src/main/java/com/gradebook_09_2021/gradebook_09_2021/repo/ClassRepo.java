package com.gradebook_09_2021.gradebook_09_2021.repo;

import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.teacherDomain.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepo extends JpaRepository<ClassDetail,Long> {

    Optional<ClassDetail> findByYearBookAndClassSign(YearBook yearBook, ClassSign classSign);

}
