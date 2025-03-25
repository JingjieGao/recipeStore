package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Category;
import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void getById() {
        Recipe retrievedRecipe = recipeDao.getById(1);
        assertNotNull(retrievedRecipe);
        assertEquals("Caesar Salad", retrievedRecipe.getName());
        assertEquals(1, retrievedRecipe.getId());
    }

    @Test
    void update() {
        Recipe recipe = recipeDao.getById(2);
        recipe.setName("For test");
        recipeDao.update(recipe);
        Recipe retrievedRecipe = recipeDao.getById(2);
        assertEquals("For test", retrievedRecipe.getName());
    }

    @Test
    void insert() {
        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.getById(1);
        assertNotNull(category, "category is null");
        Recipe recipe = new Recipe();
        recipe.setName("For test");
        recipe.setCategory(category);
        recipe.setIngredients("For test");
        recipe.setInstructions("For test");

        int insertedRecipeId = recipeDao.insert(recipe);

        Recipe retrievedRecipe = recipeDao.getById(insertedRecipeId);

        assertNotNull(retrievedRecipe);
        assertEquals(recipe.getIngredients(), retrievedRecipe.getIngredients());
        assertEquals("For test", retrievedRecipe.getName());
    }

    @Test
    void delete() {
        recipeDao.delete(recipeDao.getById(3));
        assertNull(recipeDao.getById(3));
    }

    @Test
    void getAll() {
        List<Recipe> recipes = recipeDao.getAll();
        assertEquals(4, recipes.size());
    }

}