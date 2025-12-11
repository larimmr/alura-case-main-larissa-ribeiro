package br.com.alura.projeto.course;

import br.com.alura.projeto.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Length(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Código deve conter apenas letras minúsculas, números e hífen")
    @Column(unique = true)
    private String code;

    @NotBlank
    private String instructor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CourseStatus status = CourseStatus.ACTIVE;

    private LocalDateTime inactivatedAt;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public Course() {
    }

    public Course(String name, String code, String instructor, Category category, String description) {
        this.name = name;
        this.code = code;
        this.instructor = instructor;
        this.category = category;
        this.description = description;
        this.status = CourseStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getInstructor() {
        return instructor;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public LocalDateTime getInactivatedAt() {
        return inactivatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void inactivate() {
        this.status = CourseStatus.INACTIVE;
        this.inactivatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.status = CourseStatus.ACTIVE;
        this.inactivatedAt = null;
    }
}
