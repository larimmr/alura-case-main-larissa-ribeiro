package br.com.alura.projeto.course;

public record CourseDTO(
        Long id,
        String name,
        String code,
        String instructor,
        String description,
        String status,
        Long categoryId,
        String categoryName) {
    public CourseDTO(Course course) {
        this(
                course.getId(),
                course.getName(),
                course.getCode(),
                course.getInstructor(),
                course.getDescription(),
                course.getStatus().name(),
                course.getCategory() != null ? course.getCategory().getId() : null,
                course.getCategory() != null ? course.getCategory().getName() : null);
    }
}
