package br.com.alura.projeto.course;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import br.com.alura.projeto.category.CategoryRepository;
import java.util.List;

import br.com.alura.projeto.category.Category;
import br.com.alura.projeto.category.CategoryRepository;

@Controller
public class CourseController {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public CourseController(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin/courses")
    public String list(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);

        return "/admin/course/list";
    }

    @GetMapping("/admin/course/new")
    public String create(NewCourseForm form, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "/admin/course/newForm";
    }

    @PostMapping("/admin/course/new")
    public String save(@Valid NewCourseForm form, BindingResult bindingResult, Model model) {

        if (courseRepository.existsByCode(form.getCode())) {
            bindingResult.rejectValue("code", "error.course", "Code already exists");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "course/new";
        }

        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category"));
        Course course = new Course(
                form.getName(),
                form.getCode(),
                form.getInstructorEmail(),
                category,
                form.getDescription());

        courseRepository.save(course);
        return "redirect:/admin/courses";
    }

    @PostMapping("/course/{code}/inactive")
    public ResponseEntity<?> updateStatus(@PathVariable("code") String courseCode) {

        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getStatus() == CourseStatus.ACTIVE) {
            course.inactivate();
        } else {
            course.activate(); 
        }
        courseRepository.save(course);

        return ResponseEntity.ok().build();
    }

}
