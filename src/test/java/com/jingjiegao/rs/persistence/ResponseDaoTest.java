package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.recipeApi.Response;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The type Response dao test.
 */
class ResponseDaoTest {
    /**
     * The Response dao.
     */
    ResponseDao responseDao;

    private final Logger logger = LogManager.getLogger(this.getClass());
    private static final Dotenv dotenv = Dotenv.load();
    /**
     * The Search url.
     */
    String searchUrl = dotenv.get("RECIPE_API_QUERY_URL");

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        responseDao = new ResponseDao();
    }

    /**
     * Test api url loaded.
     */
    @Test
    public void testAPIUrlLoaded() {
        assertNotNull(searchUrl, "API URL should not be null");
        assertFalse(searchUrl.isEmpty(), "API URL should not be empty");
        assertTrue(searchUrl.startsWith("http"), "API URL should start with http or https");
    }

    /**
     * Test recipe api json.
     *
     * @throws Exception the exception
     */
    @Test
    public void testRecipeApiJSON() throws Exception {
        String searchQuery = "chicken";
        Response response = responseDao.searchRecipe(searchQuery);

        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getMeals(), "Meals should not be null");
        assertTrue(response.getMeals().size() > 0, "Meals list should not be empty");

        logger.debug("Returned meals: " + response.getMeals());
    }
}