package com.jingjiegao.rs.entity;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Recipe.
 */
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "recipes_user_id_fk")
    )
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "recipes_category_id_fk")
    )
    private Category category;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "instructions")
    private String instructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    /**
     * Instantiates a new Recipe.
     */
    public Recipe() {
    }

    /**
     * Instantiates a new Recipe.
     *
     * @param user         the user
     * @param category     the category
     * @param name         the name
     * @param ingredients  the ingredients
     * @param instructions the instructions
     */
    public Recipe(User user, Category category, String name, String ingredients, String instructions) {
        this.user = user;
        this.category = category;
        this.name = name;
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
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
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

    /**
     * Gets favorites.
     *
     * @return the favorites
     */
    public List<Favorite> getFavorites() {
        return favorites;
    }

    /**
     * Sets favorites.
     *
     * @param favorites the favorites
     */
    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    /**
     * Add favorite.
     *
     * @param favorite the favorite
     */
    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
        favorite.setRecipe(this);
    }

    /**
     * Remove favorite.
     *
     * @param favorite the favorite
     */
    public void removeFavorite(Favorite favorite) {
        favorites.remove(favorite);
        favorite.setRecipe(null);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", user=" + user +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}