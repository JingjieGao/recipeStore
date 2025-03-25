package com.jingjiegao.rs.entity;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;

/**
 * The type Recipe.
 */
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "recipes_category_id_fk")
    )
    private Category category;

    private String ingredients;

    private String instructions;

    /**
     * Instantiates a new Recipe.
     */
    public Recipe() {
    }

    /**
     * Instantiates a new Recipe.
     *
     * @param name         the name
     * @param category     the category
     * @param ingredients  the ingredients
     * @param instructions the instructions
     */
    public Recipe(String name, Category category, String ingredients, String instructions) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets ingredients.
     *
     * @return the ingredients
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Sets ingredients.
     *
     * @param ingredients the ingredients
     */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Gets instructions.
     *
     * @return the instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Sets instructions.
     *
     * @param instructions the instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
