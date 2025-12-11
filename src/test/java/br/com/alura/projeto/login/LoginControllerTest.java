package br.com.alura.projeto.login;

import br.com.alura.projeto.category.Category;
import br.com.alura.projeto.course.Course;
import br.com.alura.projeto.course.CourseRepository;
import br.com.alura.projeto.course.CourseStatus;
import br.com.alura.projeto.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void homeLoadsLoginViewAndQueriesOnlyActiveCourses() throws Exception {
        var category = new Category("Test Category", "cat-test", "#fff", 1);
        category.setId(1L); 
        var course = new Course("Test Course", "course-test", "Teacher", category, "description");

        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(courseRepository.findByCategoryIdAndStatus(any(Long.class), eq(CourseStatus.ACTIVE)))
                .thenReturn(List.of(course));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("categories"));

        verify(courseRepository, times(1)).findByCategoryIdAndStatus(any(Long.class), eq(CourseStatus.ACTIVE));
    }
}