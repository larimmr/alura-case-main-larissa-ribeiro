package br.com.alura.projeto.registration;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import br.com.alura.projeto.course.CourseRepository;
import br.com.alura.projeto.user.UserRepository;

class RegistrationControllerReportTest {

    @Test
    void report__should_return_list_of_registration_report_items() {

        RegistrationRepository registrationRepository = mock(RegistrationRepository.class);
        CourseRepository courseRepository = mock(CourseRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

  
        RegistrationController controller = new RegistrationController(
                registrationRepository,
                courseRepository,
                userRepository
        );

        List<Object[]> fakeSqlResult = List.of(
                new Object[]{"Java para Iniciantes", "java", "Charles", "charles@alura.com.br", 10L},
                new Object[]{"Spring Boot", "spring", "Charles", "charles@alura.com.br", 9L}
        );

        when(registrationRepository.findTopCoursesRaw()).thenReturn(fakeSqlResult);

    
        ResponseEntity<List<RegistrationReportItem>> response = controller.report();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);

        RegistrationReportItem item1 = response.getBody().get(0);
        assertThat(item1.getCourseName()).isEqualTo("Java para Iniciantes");
        assertThat(item1.getCourseCode()).isEqualTo("java");
        assertThat(item1.getInstructorName()).isEqualTo("Charles");
        assertThat(item1.getTotalRegistrations()).isEqualTo(10L);

        RegistrationReportItem item2 = response.getBody().get(1);
        assertThat(item2.getCourseName()).isEqualTo("Spring Boot");
        assertThat(item2.getCourseCode()).isEqualTo("spring");
        assertThat(item2.getInstructorEmail()).isEqualTo("charles@alura.com.br");
        assertThat(item2.getTotalRegistrations()).isEqualTo(9L);
    }
}
