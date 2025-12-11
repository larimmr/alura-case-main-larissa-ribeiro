package br.com.alura.projeto.course;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class EditCourseForm {

    @NotNull
    private Long id; // id do curso a ser editado

    @NotBlank
    private String name;

    @NotBlank
    @Length(min = 4, max = 10)
    private String code;

    private String description;

    @NotBlank
    @Email
    private String instructorEmail;

    @NotNull
    private Long categoryId;

    @NotNull
    private CourseStatus status; // ACTIVE ou INACTIVE

    public EditCourseForm() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructorEmail() { return instructorEmail; }
    public void setInstructorEmail(String instructorEmail) { this.instructorEmail = instructorEmail; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }
}
