package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type User dao test.
 */
class UserDaoTest {
    private GenericDao<User> userDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        userDao = new GenericDao<>(User.class);
    }

    /**
     * Gets by id.
     */
    @Test
    void getById() {
        // id:1 username:Alice
        User retrievedUser = userDao.getById(1);
        assertNotNull(retrievedUser);
        assertEquals("Alice", retrievedUser.getUsername());
        assertEquals("Alice@example.com", retrievedUser.getEmail());
    }


    /**
     * Update.
     */
    @Test
    void update() {
        // id:2 username:Bob
        User user = userDao.getById(2);
        user.setFirstName("Robert");
        userDao.Update(user);
        User updatedUser = userDao.getById(2);
        assertEquals("Robert", updatedUser.getFirstName());
    }


    /**
     * Insert.
     */
    @Test
    void insert() {
        User newUser = new User("sub-charlie-003", "Charlie", "Charlie@example.com", "", "");
        int id = userDao.insert(newUser);
        assertNotEquals(0, id);
        User insertedUser = userDao.getById(id);
        assertEquals("Charlie", insertedUser.getUsername());
        assertEquals("Charlie@example.com", insertedUser.getEmail());
    }


    /**
     * Delete.
     */
    @Test
    void delete() {
        User newUser = new User("sub-delete-004", "DeleteMe", "deleteme@example.com", "", "");
        int id = userDao.insert(newUser);
        User inserted = userDao.getById(id);
        assertNotNull(inserted);
        userDao.delete(inserted);
        assertNull(userDao.getById(id));
    }


    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<User> users = userDao.getAll();
        assertNotNull(users);
        // Alice and Bob
        assertEquals(2, users.size());
    }


    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        List<User> users = userDao.getByPropertyEqual("username", "Alice");
        assertEquals(1, users.size());
        assertEquals("Alice@example.com", users.get(0).getEmail());
        assertEquals("sub-alice-001", users.get(0).getCognitoSub());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        List<User> users = userDao.getByPropertyLike("username", "B");
        assertEquals(1, users.size());
        assertEquals("Bob@example.com", users.get(0).getEmail());
        assertEquals("sub-bob-002", users.get(0).getCognitoSub());
    }


}