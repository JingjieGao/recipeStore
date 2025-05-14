package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Favorite;
import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Favorite dao test.
 */
class FavoriteDaoTest {
    private GenericDao<User> userDao;
    private GenericDao<Recipe> recipeDao;
    private FavoriteDao favoriteDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");

        favoriteDao = new FavoriteDao();
        userDao = new GenericDao<>(User.class);
        recipeDao = new GenericDao<>(Recipe.class);
    }

    /**
     * Gets by id.
     */
    @Test
    void getById() {
        Favorite retrievedFavorite = favoriteDao.getById(1);
        assertNotNull(retrievedFavorite);
        assertEquals(1, retrievedFavorite.getUser().getId());
        assertEquals(1, retrievedFavorite.getRecipe().getId());
    }

    /**
     * Update.
     */
    @Test
    void update() {
        // An existing favorite with ID 1
        Favorite favoriteToUpdate = favoriteDao.getById(1);
        assertNotNull(favoriteToUpdate);

        // Modify some properties, such as change the recipe
        Recipe newRecipe = recipeDao.getById(3); // id:3 name:Chocolate Cake
        favoriteToUpdate.setRecipe(newRecipe);
        favoriteDao.Update(favoriteToUpdate);

        // Verify the update
        Favorite updatedFavorite = favoriteDao.getById(1);
        assertNotNull(updatedFavorite);
        assertEquals("Chocolate Cake", updatedFavorite.getRecipe().getName());
    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        // Insert a new Favorite
        User existingUser = userDao.getById(1);
        Recipe existingRecipe = recipeDao.getById(4);
        Favorite newFavorite = new Favorite(existingUser, existingRecipe);

        // Create a new Favorite with existing User and Recipe
        int id = favoriteDao.insert(newFavorite);
        assertNotEquals(0, id);

        // Verify that the new Favorite was inserted
        Favorite insertedFavorite = favoriteDao.getById(id);
        assertNotNull(insertedFavorite);
        assertEquals(existingUser.getId(), insertedFavorite.getUser().getId());
        assertEquals(existingRecipe.getId(), insertedFavorite.getRecipe().getId());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        Favorite favoriteToDelete = favoriteDao.getById(1);
        assertNotNull(favoriteToDelete);
        favoriteDao.delete(favoriteToDelete);
        assertNull(favoriteDao.getById(1));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Favorite> favorites = favoriteDao.getAll();
        assertNotNull(favorites);
        assertEquals(2, favorites.size());
    }

    /**
     * Gets favorites by user id.
     */
    @Test
    void getFavoritesByUserId() {
        List<Favorite> favorites = favoriteDao.getFavoritesByUserId(1); // User id: 1
        assertNotNull(favorites, "Favorites should not be null.");
        assertTrue(favorites.size() > 0, "User should have at least one favorite.");
        assertEquals(1, favorites.get(0).getUser().getId(), "All favorites should belong to the specified user.");
    }

    /**
     * Is recipe favorited by user.
     */
    @Test
    void isRecipeFavoritedByUser() {
        // Use existing data to fetch the user and recipe from the database
        User existingUser1 = userDao.getById(1);
        Recipe existingRecipe1 = recipeDao.getById(1);
        User existingUser2 = userDao.getById(2);
        Recipe existingRecipe2 = recipeDao.getById(2);

        // User 1 has already favorited recipe 1
        assertTrue(favoriteDao.isRecipeFavoritedByUser(existingRecipe1.getId(), existingUser1.getId()),
                "The recipe should be favorited by the user.");

        // User 2 has not favorited recipe 1
        assertFalse(favoriteDao.isRecipeFavoritedByUser(existingRecipe1.getId(), existingUser2.getId()),
                "The recipe should not be favorited by the user.");

        // User 1 has not favorited recipe 2
        assertFalse(favoriteDao.isRecipeFavoritedByUser(existingRecipe2.getId(), existingUser1.getId()),
                "The recipe should not be favorited by the user.");
    }
}