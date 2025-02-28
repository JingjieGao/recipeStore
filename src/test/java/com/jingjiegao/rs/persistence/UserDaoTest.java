package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Author dao test.
 */
class UserDaoTest {
    /**
     * The Author dao.
     */
    UserDao userDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    /**
     * Gets by id.
     */
    @Test
    void getById() {
        userDao = new UserDao();
        User retrievedUser = userDao.getById(1);
        assertNotNull(retrievedUser);
        assertEquals("Jane", retrievedUser.getFirstName());
    }

    /**
     * Update.
     */
    @Test
    void update() {
        userDao = new UserDao();
        User userToUpdate = userDao.getById(1);
        userToUpdate.setLastName("LastNameForTest");
        userDao.update(userToUpdate);

        // retrieve the user and check that the name change worked
        User actualUser = userDao.getById(1);
        assertEquals("LastNameForTest", actualUser.getLastName());
    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        userDao = new UserDao();
        User userToInsert = new User("Kia", "Yang","kia@example.com","password111");
        int insertedUserId = userDao.insert(userToInsert);
        assertNotEquals(0, insertedUserId);
        User insertedUser = userDao.getById(insertedUserId);
        assertEquals("Kia", insertedUser.getFirstName());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        userDao = new UserDao();
        userDao.delete(userDao.getById(2));
        assertNull(userDao.getById(2));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        userDao = new UserDao();
        List<User> users = userDao.getAll();
        assertEquals(2, users.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        userDao = new UserDao();
        List<User> users = userDao.getByPropertyLike("lastName", "Gao");
        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getId());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        userDao = new UserDao();
        List<User> users = userDao.getByPropertyLike("lastName", "G");
        assertEquals(1, users.size());
    }
}