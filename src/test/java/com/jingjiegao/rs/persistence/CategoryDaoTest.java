package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Category;
import com.jingjiegao.rs.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {

    CategoryDao categoryDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void getById() {
        categoryDao = new CategoryDao();
        Category retrievedCategory = categoryDao.getById(1);
        assertNotNull(retrievedCategory);
        assertEquals("Appetizer", retrievedCategory.getName());
    }

    @Test
    void update() {
        categoryDao = new CategoryDao();
        Category categoryToUpdate = categoryDao.getById(1);
        categoryToUpdate.setName("NameForTest");
        categoryDao.update(categoryToUpdate);

        Category actualCategory = categoryDao.getById(1);
        assertEquals("NameForTest", actualCategory.getName());
    }

    @Test
    void insert() {
        categoryDao = new CategoryDao();
        Category categoryToInsert = new Category("NameForTest");
        int insertedCategoryId = categoryDao.insert(categoryToInsert);
        assertNotEquals(0, insertedCategoryId);
        Category insertedCategory = categoryDao.getById(insertedCategoryId);
        assertEquals("NameForTest", insertedCategory.getName());
    }

    @Test
    void delete() {
        categoryDao = new CategoryDao();
        categoryDao.delete(categoryDao.getById(2));
        assertNull(categoryDao.getById(2));
    }

    @Test
    void getAll() {
        categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getAll();
        assertEquals(4, categories.size());
    }

    @Test
    void getByPropertyEqual() {
        categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getByPropertyLike("name", "Appetizer");
        assertEquals(1, categories.size());
        assertEquals(1, categories.get(0).getId());
    }

    @Test
    void getByPropertyLike() {
        categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getByPropertyLike("name", "B");
        assertEquals(1, categories.size());
    }

}