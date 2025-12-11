package br.com.alura.projeto;

import br.com.alura.projeto.login.LoginController;
import br.com.alura.projeto.course.CourseRepository;
import br.com.alura.projeto.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
class ProjetoAluraApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseRepository courseRepository;

	@MockBean
	private CategoryRepository categoryRepository;

	@Test
	void loginControllerLoadsAndReturnsLoginView() throws Exception {
		when(categoryRepository.findAll()).thenReturn(java.util.List.of());

		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

}
