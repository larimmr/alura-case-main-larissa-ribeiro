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

    @GetMapping("/admin/course/{id}/edit")
    public String edit(@PathVariable("id") Long id, EditCourseForm form, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        form.setId(course.getId());
        form.setName(course.getName());
        form.setCode(course.getCode());
        form.setDescription(course.getDescription());
        form.setInstructorEmail(course.getInstructor());
        form.setCategoryId(course.getCategory().getId());
        form.setStatus(course.getStatus());

        model.addAttribute("categories", categoryRepository.findAll());
        return "/admin/course/editForm";
    }
  
    @PostMapping("/admin/course/{id}/edit")
    public String update(@PathVariable("id") Long id, @Valid EditCourseForm form, BindingResult bindingResult,
            Model model) {
        if (courseRepository.existsByCode(form.getCode()) &&
                !courseRepository.findByCode(form.getCode()).get().getId().equals(id)) {
            bindingResult.rejectValue("code", "error.course", "Code already exists");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "/admin/course/editForm";
        }

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category"));

        course.update(
                form.getName(),
                form.getCode(),
                form.getInstructorEmail(),
                category,
                form.getDescription(),
                form.getStatus());

        courseRepository.save(course);

        return "redirect:/admin/courses";
    }
}
