package br.com.alura.projeto.category;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    private final Validator validator;

    public CategoryTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void should_create_category_with_valid_data() {
        Category category = new Category(
                "Backend",
                "backend",
                "#000000",
                1
        );

        assertThat(category.getName()).isEqualTo("Backend");
        assertThat(category.getCode()).isEqualTo("backend");
        assertThat(category.getColor()).isEqualTo("#000000");
        assertThat(category.getOrder()).isEqualTo(1);
    }

    @Test
    void should_fail_when_code_is_shorter_than_4_characters() {
        Category category = new Category(
                "Backend",
                "abc",
                "#000000",
                1
        );

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("code"));
    }

    @Test
    void should_fail_when_code_is_longer_than_10_characters() {
        Category category = new Category(
                "Backend",
                "thiscodeistoolong",
                "#000000",
                1
        );

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("code"));
    }

    @Test
    void should_fail_when_name_is_blank() {
        Category category = new Category(
                " ",
                "valid",
                "#000000",
                1
        );

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void should_fail_when_color_is_blank() {
        Category category = new Category(
                "Backend",
                "valid",
                " ",
                1
        );

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("color"));
    }

    @Test
    void should_fail_when_order_is_less_than_one() {
        Category category = new Category(
                "Backend",
                "valid",
                "#000000",
                0
        );

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("order"));
    }

    @Test
    void should_update_category_fields() {
        Category category = new Category(
                "Backend",
                "backend",
                "#000000",
                1
        );

        category.update("Frontend", "front", "#FFFFFF", 2);

        assertThat(category.getName()).isEqualTo("Frontend");
        assertThat(category.getCode()).isEqualTo("front");
        assertThat(category.getColor()).isEqualTo("#FFFFFF");
        assertThat(category.getOrder()).isEqualTo(2);
    }
}
