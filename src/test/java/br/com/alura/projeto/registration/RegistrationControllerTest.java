package br.com.alura.projeto.registration;

import br.com.alura.projeto.category.Category;
import br.com.alura.projeto.course.Course;
import br.com.alura.projeto.course.CourseRepository;
import br.com.alura.projeto.user.Role;
import br.com.alura.projeto.user.User;
import br.com.alura.projeto.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegistrationRepository registrationRepository;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private UserRepository userRepository;

    private Course activeCourse;
    private User existingUser;

    @BeforeEach
    void setup() {
        Category category = new Category("Backend", "backend", "#000000", 1);
        category.setId(1L);

        activeCourse = new Course("Advanced Java", "java-adv", "dev@alura.com", category, "Advanced Java course");
        activeCourse.activate();
        activeCourse.setId(1L);

        existingUser = new User("Larissa Ribeiro", "larissa@teste.com", Role.STUDENT, "123456");
    }

    @Test
    void createRegistration_should_return_created_when_valid() throws Exception {
        NewRegistrationDTO dto = new NewRegistrationDTO();
        dto.setCourseCode("java-adv");
        dto.setStudentEmail("larissa@teste.com");

        when(courseRepository.findByCode("java-adv")).thenReturn(Optional.of(activeCourse));
        when(userRepository.findByEmail("larissa@teste.com")).thenReturn(Optional.of(existingUser));
        when(registrationRepository.existsByUserIdAndCourseId(existingUser.getId(), activeCourse.getId()))
                .thenReturn(false);

        mockMvc.perform(post("/registration/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Registration successful"));

        verify(registrationRepository, times(1)).save(any());
    }

    @Test
    void createRegistration_should_return_badRequest_when_course_inactive_or_not_found() throws Exception {
        NewRegistrationDTO dto = new NewRegistrationDTO();
        dto.setCourseCode("java-adv");
        dto.setStudentEmail("larissa@teste.com");

        activeCourse.inactivate(); 
        when(courseRepository.findByCode("java-adv")).thenReturn(Optional.of(activeCourse));

        mockMvc.perform(post("/registration/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Course not found or inactive"));
    }

    @Test
    void createRegistration_should_return_badRequest_when_user_not_found() throws Exception {
        NewRegistrationDTO dto = new NewRegistrationDTO();
        dto.setCourseCode("java-adv");
        dto.setStudentEmail("larissa@teste.com");

        when(courseRepository.findByCode("java-adv")).thenReturn(Optional.of(activeCourse));
        when(userRepository.findByEmail("larissa@teste.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/registration/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }

    @Test
    void createRegistration_should_return_badRequest_when_user_already_registered() throws Exception {
        NewRegistrationDTO dto = new NewRegistrationDTO();
        dto.setCourseCode("java-adv");
        dto.setStudentEmail("larissa@teste.com");

        when(courseRepository.findByCode("java-adv")).thenReturn(Optional.of(activeCourse));
        when(userRepository.findByEmail("larissa@teste.com")).thenReturn(Optional.of(existingUser));
        when(registrationRepository.existsByUserIdAndCourseId(existingUser.getId(), activeCourse.getId()))
                .thenReturn(true);

        mockMvc.perform(post("/registration/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already registered for this course"));
    }

    @Test
    void createRegistration_should_return_badRequest_when_course_not_found() throws Exception {
        NewRegistrationDTO dto = new NewRegistrationDTO();
        dto.setCourseCode("non-existent-course");
        dto.setStudentEmail("larissa@teste.com");

        when(courseRepository.findByCode("non-existent-course")).thenReturn(Optional.empty());

        mockMvc.perform(post("/registration/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Course not found or inactive"));
    }
}
