package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Category;
import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Recipe dao test.
 */
class RecipeDaoTest {
    private GenericDao<Category> categoryDao;
    private GenericDao<User> userDao;
    private RecipeDao recipeDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");

        recipeDao = new RecipeDao();
        categoryDao = new GenericDao<>(Category.class);
        userDao = new GenericDao<>(User.class);
    }

    /**
     * Gets by id.
     */
    @Test
    void getById() {
        // id:1 user_id:1 category_id:1 name:Caesar Salad
        Recipe retrievedRecipe = recipeDao.getById(1);
        assertNotNull(retrievedRecipe);
        assertEquals(1, retrievedRecipe.getUser().getId());
        assertEquals(1, retrievedRecipe.getCategory().getId());
        assertEquals("Caesar Salad", retrievedRecipe.getName());
    }

    /**
     * Update.
     */
    @Test
    void update() {
        // id:2 name:Spaghetti Bolognese
        Recipe recipe = recipeDao.getById(2);
        String originalName = recipe.getName();
        recipe.setName("UpdatedName");
        recipeDao.Update(recipe);
        Recipe updated = recipeDao.getById(2);
        assertEquals("UpdatedName", updated.getName());
    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        // Use an existing User and Category
        User existingUser = userDao.getById(1); // Alice
        Category existingCategory = categoryDao.getById(1);  // Appetizer

        // Create a new Recipe with existing User and Category
        Recipe newRecipe = new Recipe(existingUser, existingCategory, "TestRecipe", "Ingredients", "Instructions");
        int id = recipeDao.insert(newRecipe);

        // Verify that the new Recipe was inserted
        assertNotEquals(0, id);
        Recipe inserted = recipeDao.getById(id);
        assertEquals("TestRecipe", inserted.getName());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        // Use an existing User and Category
        User existingUser = userDao.getById(1); // Alice
        Category existingCategory = categoryDao.getById(1);  // Appetizer

        // Create a new Recipe with existing User and Category
        Recipe newRecipe = new Recipe(existingUser, existingCategory, "ToBeDeletedRecipe", "Ingredients", "Instructions");
        int id = recipeDao.insert(newRecipe);

        // Verify that the Recipe was inserted
        Recipe toDelete = recipeDao.getById(id);
        assertNotNull(toDelete);

        // Delete the Recipe
        recipeDao.delete(toDelete);

        // Verify that the Recipe was deleted
        assertNull(recipeDao.getById(id));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Recipe> recipes = recipeDao.getAll();
        assertNotNull(recipes);
        // Caesar Salad, Spaghetti Bolognese, Chocolate Cake, Mojito, and Mystery Dish
        assertEquals(5, recipes.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        List<Recipe> recipes = recipeDao.getByPropertyEqual("name", "Caesar Salad");
        assertEquals(1, recipes.size());
        assertEquals(1, recipes.get(0).getId());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        List<Recipe> recipes = recipeDao.getByPropertyLike("name", "C");
        assertEquals(2, recipes.size());
    }

    /**
     * Gets by category id.
     */
    @Test
    void getByCategoryId() {
        // Category id: 1, name: Appetizer
        List<Recipe> recipes = recipeDao.getByCategoryId(1);
        assertNotNull(recipes, "The recipe list should not be null");
        // Ensure at least one recipe is returned
        assertTrue(recipes.size() > 0, "At least one recipe should be returned for category ID 1");
        // Verify that the category is correct
        assertEquals(1, recipes.get(0).getCategory().getId(), "The category ID of the recipe should be 1");

    }

    /**
     * Delete recipe does not delete user or category.
     */
    @Test
    void deleteRecipeDoesNotDeleteUserOrCategory() {
        // Access an existing Recipe by its name
        Recipe existingRecipe = recipeDao.getByPropertyEqual("name", "Caesar Salad").get(0);
        assertNotNull(existingRecipe, "The recipe should exist before deletion");

        // Ensure that the associated User and Category still exist
        User userBeforeDelete = userDao.getById(existingRecipe.getUser().getId());
        Category categoryBeforeDelete = categoryDao.getById(existingRecipe.getCategory().getId());
        assertNotNull(userBeforeDelete, "User should still exist before deletion");
        assertNotNull(categoryBeforeDelete, "Category should still exist before deletion");

        // Delete the Recipe
        recipeDao.delete(existingRecipe);

        // Verify that the User and Category are still in the database after deleting the Recipe
        User userAfterDelete = userDao.getById(existingRecipe.getUser().getId());
        Category categoryAfterDelete = categoryDao.getById(existingRecipe.getCategory().getId());
        assertNotNull(userAfterDelete, "User should still exist after deleting the recipe");
        assertNotNull(categoryAfterDelete, "Category should still exist after deleting the recipe");
    }

}