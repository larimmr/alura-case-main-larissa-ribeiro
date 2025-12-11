package br.com.alura.projeto.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.alura.projeto.category.CategoryRepository;
import br.com.alura.projeto.course.CourseRepository;

@Controller
public class LoginController {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public LoginController(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
      
        var categories = categoryRepository.findAll();
        categories.forEach(cat -> {
            cat.setCourses(courseRepository.findByCategoryId(cat.getId()));
        });
        model.addAttribute("categories", categories);

        return "login";
    }
}
