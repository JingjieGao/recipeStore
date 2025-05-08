package com.jingjiegao.rs.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * The type Favorite.
 */
@Entity
@Table(name ="favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "user_id")
    )
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id",
            foreignKey = @ForeignKey(name = "recipe_id")
    )
    private Recipe recipe;

    /**
     * Instantiates a new Favorite.
     */
    public Favorite() {
    }

    /**
     * Instantiates a new Favorite.
     *
     * @param user   the user
     * @param recipe the recipe
     */
    public Favorite(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
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
     * Gets recipe.
     *
     * @return the recipe
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Sets recipe.
     *
     * @param recipe the recipe
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", user=" + user +
                ", recipe=" + recipe +
                '}';
    }
}
