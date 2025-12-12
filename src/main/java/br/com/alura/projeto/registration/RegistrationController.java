package br.com.alura.projeto.registration;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.projeto.course.Course;
import br.com.alura.projeto.course.CourseRepository;
import br.com.alura.projeto.course.CourseStatus;
import br.com.alura.projeto.user.User;
import br.com.alura.projeto.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public RegistrationController(RegistrationRepository registrationRepository,
            CourseRepository courseRepository,
            UserRepository userRepository) {
        this.registrationRepository = registrationRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/registration/new")
    public ResponseEntity<String> createRegistration(@Valid @RequestBody NewRegistrationDTO newRegistration) {

        Course course = courseRepository.findByCode(newRegistration.getCourseCode())
                .orElse(null);
        if (course == null || !course.getStatus().equals(CourseStatus.ACTIVE)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Course not found or inactive");
        }

        User user = userRepository.findByEmail(newRegistration.getStudentEmail())
                .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User not found");
        }

        boolean alreadyRegistered = registrationRepository
                .existsByUserIdAndCourseId(user.getId(), course.getId());
        if (alreadyRegistered) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already registered for this course");
        }

        Registration registration = new Registration();
        registration.setUserId(user.getId());
        registration.setCourseId(course.getId());

        registrationRepository.save(registration);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }

    @GetMapping("/registration/report")
    public ResponseEntity<List<RegistrationReportItem>> report() {
        List<RegistrationReportItem> items = new ArrayList<>();

        // TODO: Implementar a Questão 6 - Relatório de Cursos Mais Acessados aqui...

        // Dados fictícios abaixo que devem ser substituídos
        items.add(new RegistrationReportItem(
                "Java para Iniciantes",
                "java",
                "Charles",
                "charles@alura.com.br",
                10L));

        items.add(new RegistrationReportItem(
                "Spring para Iniciantes",
                "spring",
                "Charles",
                "charles@alura.com.br",
                9L));

        items.add(new RegistrationReportItem(
                "Maven para Avançados",
                "maven",
                "Charles",
                "charles@alura.com.br",
                9L));

        return ResponseEntity.ok(items);
    }

}
