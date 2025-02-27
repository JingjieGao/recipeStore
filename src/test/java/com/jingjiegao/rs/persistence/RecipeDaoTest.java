package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Author dao test.
 */
class RecipeDaoTest {
    /**
     * The Author dao.
     */
    RecipeDao recipeDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        recipeDao = new RecipeDao();
    }

    /**
     * Gets by id.
     */
    @Test
    void getById() {
        Recipe retrievedRecipe = recipeDao.getById(1);
        assertNotNull(retrievedRecipe);
        assertEquals("Chicken Curry", retrievedRecipe.getName());
        assertEquals(1, retrievedRecipe.getId());
    }

    /**
     * Update.
     */
    @Test
    void update() {
        Recipe recipe = recipeDao.getById(2);
        recipe.setName("For test");
        recipeDao.update(recipe);
        Recipe retrievedRecipe = recipeDao.getById(2);
        assertEquals("For test", retrievedRecipe.getName());
    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        UserDao userDao = new UserDao();
        User user = userDao.getById(1);
        assertNotNull(user, "user is null");
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        recipe.setName("For test");
        recipe.setCategory("For test");
        recipe.setIngredients("For test");
        recipe.setInstructions("For test");

        int insertedRecipeId = recipeDao.insert(recipe);

        Recipe retrievedRecipe = recipeDao.getById(insertedRecipeId);

        assertNotNull(retrievedRecipe);
        assertEquals(recipe.getCategory(), retrievedRecipe.getCategory());
        assertEquals("For test", retrievedRecipe.getName());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        recipeDao.delete(recipeDao.getById(3));
        assertNull(recipeDao.getById(3));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Recipe> recipes = recipeDao.getAll();
        assertEquals(3, recipes.size());
    }
}