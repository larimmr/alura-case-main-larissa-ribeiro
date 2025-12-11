package br.com.alura.projeto.course;

import org.junit.jupiter.api.Test;
import br.com.alura.projeto.category.Category;
import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {

    @Test
    void inactivate__should_set_status_to_inactive_and_set_inactivatedAt() {

        Category category = new Category("Backend", "backend", "#000000", 1);
        Course course = new Course("Spring Boot", "spring-boot", "dev@alura.com", category, "Spring Boot Course");

        course.inactivate();

        assertThat(course.getStatus()).isEqualTo(CourseStatus.INACTIVE);
        assertThat(course.getInactivatedAt()).isNotNull();
    }

    @Test
    void activate__should_set_status_to_active_and_clear_inactivatedAt() {
      
        Category category = new Category("Backend", "backend", "#000000", 1);
        Course course = new Course("Spring Boot", "spring-boot", "dev@alura.com", category, "Spring Boot Course");

        course.inactivate();
        course.activate();

        assertThat(course.getStatus()).isEqualTo(CourseStatus.ACTIVE);
        assertThat(course.getInactivatedAt()).isNull();
    }
}
