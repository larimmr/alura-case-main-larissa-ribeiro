package br.com.alura.projeto.registration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    @Query(value = """
                    SELECT
                c.name AS courseName,
                c.code AS courseCode,
                u.name AS instructorName,
                c.instructor AS instructorEmail,
                COUNT(r.id) AS totalRegistrations
            FROM registration r
            JOIN course c ON r.course_id = c.id
            LEFT JOIN user u ON c.instructor = u.email
            GROUP BY c.id, c.name, c.code, u.name, u.email
            ORDER BY totalRegistrations DESC;
                    """, nativeQuery = true)
    List<Object[]> findTopCoursesRaw();
}
