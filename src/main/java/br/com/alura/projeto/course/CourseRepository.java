package br.com.alura.projeto.course;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCode(String code);

    Optional<Course> findByCode(String code);

    List<Course> findByCategoryId(Long categoryId);

    List<Course> findByCategoryIdAndStatus(Long categoryId, CourseStatus status);

}
