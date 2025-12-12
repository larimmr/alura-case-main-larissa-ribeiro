package br.com.alura.projeto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditCategoryForm {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotBlank
    private String color;

    @NotNull
    private Integer order;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getOrder() { return order; }
    public void setOrder(Integer order) { this.order = order; }
}
