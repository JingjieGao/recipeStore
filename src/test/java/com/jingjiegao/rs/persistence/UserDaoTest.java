package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Category;
import com.jingjiegao.rs.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RecipeDaoTest {

    private RecipeDao recipeDao;

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
        Category retrievedCategory = categoryDao.getById(1);
        assertNotNull(retrievedCategory);
        assertEquals("Appetizer", retrievedCategory.getName());
    }

    /**
     * Update.
     */
    @Test
    void update() {
        Category category = categoryDao.getById(1);
        String originalName = category.getName();
        category.setName("UpdatedName");
        categoryDao.Update(category);
        Category updated = categoryDao.getById(1);
        assertEquals("UpdatedName", updated.getName());
    }


    /**
     * Insert.
     */
    @Test
    void insert() {
        Category newCategory = new Category("TestCategory");
        int id = categoryDao.insert(newCategory);
        assertNotEquals(0, id);
        Category inserted = categoryDao.getById(id);
        assertEquals("TestCategory", inserted.getName());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        Category newCategory = new Category("ToBeDeleted");
        int id = categoryDao.insert(newCategory);
        Category toDelete = categoryDao.getById(id);
        assertNotNull(toDelete);
        categoryDao.delete(toDelete);
        assertNull(categoryDao.getById(id));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Category> categories = categoryDao.getAll();
        assertNotNull(categories);
        assertEquals(5, categories.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        List<Category> categories = categoryDao.getByPropertyEqual("name", "Appetizer");
        assertEquals(1, categories.size());
        assertEquals(1, categories.get(0).getId());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        List<Category> categories = categoryDao.getByPropertyLike("name", "B");
        assertEquals(1, categories.size());
    }


    @Test
    void getByCategoryId() {
    }
}