package br.com.alura.projeto.course;

import br.com.alura.projeto.category.Category;
import br.com.alura.projeto.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void listAllCourses__should_return_ok() throws Exception {
        Category category = new Category("Backend", "backend", "#000000", 1);

        Course course1 = new Course("Spring Boot", "springboot", "dev@alura.com", category, "Spring Boot Course");
        Course course2 = new Course("Advanced Java", "java101", "dev@alura.com", category, "Advanced Java Course");

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        mockMvc.perform(get("/admin/courses"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    void saveCourse__should_return_redirect_when_form_is_valid() throws Exception {

        Category category = new Category("Backend", "backend", "#000000", 1);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        NewCourseForm form = new NewCourseForm();
        form.setName("Spring Boot");
        form.setCode("springboot");
        form.setDescription("Spring Boot Course");
        form.setInstructorEmail("dev@alura.com");
        form.setCategoryId(1L);

        mockMvc.perform(post("/admin/course/new")
                .flashAttr("newCourseForm", form))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/courses"));
    }

    @Test
    void editCourseForm__should_load_course_and_return_ok() throws Exception {
        Category category = new Category("Backend", "backend", "#000000", 1);
        category.setId(1L);

        Course course = new Course("Spring Boot", "springboot", "dev@alura.com", category, "Spring Boot Course");
        course.activate();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        mockMvc.perform(get("/admin/course/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("editCourseForm"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    void updateCourse__should_redirect_when_form_is_valid() throws Exception {
        Category category = new Category("Backend", "backend", "#000000", 1);
        category.setId(1L);

        Course course = new Course("Spring Boot", "springboot", "dev@alura.com", category, "Spring Boot Course");
        course.activate();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(courseRepository.existsByCode("springboot")).thenReturn(false);

        EditCourseForm form = new EditCourseForm();
        form.setId(1L);
        form.setName("Spring Boot Updated");
        form.setCode("springboot");
        form.setDescription("Updated description");
        form.setInstructorEmail("dev@alura.com");
        form.setCategoryId(1L);
        form.setStatus(CourseStatus.ACTIVE);

        mockMvc.perform(post("/admin/course/1/edit")
                .flashAttr("editCourseForm", form))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/courses"));
    }

    @Test
    void toggleCourseStatus__should_toggle_course_status() throws Exception {
        Category category = new Category("Backend", "backend", "#000000", 1);
        Course course = new Course("Spring Boot", "springboot", "dev@alura.com", category, "Spring Boot Course");

        when(courseRepository.findByCode("springboot")).thenReturn(Optional.of(course));

        mockMvc.perform(post("/course/springboot/inactive"))
                .andExpect(status().isOk());
        assert(course.getStatus() == CourseStatus.INACTIVE);
        mockMvc.perform(post("/course/springboot/inactive"))
                .andExpect(status().isOk());
        assert(course.getStatus() == CourseStatus.ACTIVE);
    }
}
