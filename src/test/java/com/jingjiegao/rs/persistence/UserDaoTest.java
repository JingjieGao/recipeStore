package edu.matc.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.matc.testUtils.Database;
import edu.matc.entity.Author;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Author dao test.
 */
class AuthorDaoTest {
    /**
     * The Author dao.
     */
    AuthorDao authorDao;

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
        authorDao = new AuthorDao();
        Author retrievedAuthor = authorDao.getById(1);
        assertNotNull(retrievedAuthor);
        assertEquals("Kathy", retrievedAuthor.getFirstName());
    }

    /**
     * Update.
     */
    @Test
    void update() {
        authorDao = new AuthorDao();
        Author authorToUpdate = authorDao.getById(1);
        authorToUpdate.setLastName("LastNameForTest");
        authorDao.update(authorToUpdate);

        // retrieve the author and check that the name change worked
        Author actualAuthor = authorDao.getById(1);
        assertEquals("LastNameForTest", actualAuthor.getLastName());
    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        authorDao = new AuthorDao();
        Author authorToInsert = new Author("Kia", "Yang");
        int insertedAuthorId = authorDao.insert(authorToInsert);
        assertNotEquals(0, insertedAuthorId);
        Author insertedAuthor = authorDao.getById(insertedAuthorId);
        assertEquals("Kia", insertedAuthor.getFirstName());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        authorDao = new AuthorDao();
        authorDao.delete(authorDao.getById(2));
        assertNull(authorDao.getById(2));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        authorDao = new AuthorDao();
        List<Author> authors = authorDao.getAll();
        assertEquals(3, authors.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        authorDao = new AuthorDao();
        List<Author> authors = authorDao.getByPropertyLike("lastName", "Sierra");
        assertEquals(1, authors.size());
        assertEquals(1, authors.get(0).getId());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        authorDao = new AuthorDao();
        List<Author> authors = authorDao.getByPropertyLike("lastName", "O");
        assertEquals(1, authors.size());
    }
}