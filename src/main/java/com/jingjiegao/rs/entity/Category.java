package com.jingjiegao.rs.entity;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Category.
 */
@Entity
@Table(name ="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes = new ArrayList<>();

    /**
     * Instantiates a new Category.
     */
    public Category() {
    }

    /**
     * Add recipe.
     *
     * @param recipe the recipe
     */
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        recipe.setCategory(this);
    }

    /**
     * Remove recipe.
     *
     * @param recipe the recipe
     */
    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
        recipe.setCategory(null);
    }

    /**
     * Instantiates a new Category.
     *
     * @param name the name
     */
    public Category(String name) {
        this.name = name;
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
     * Gets recipes.
     *
     * @return the recipes
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Sets recipes.
     *
     * @param recipes the recipes
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
