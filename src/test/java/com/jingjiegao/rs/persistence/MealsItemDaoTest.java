package com.jingjiegao.rs.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jingjiegao.rs.recipeApi.MealsItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Meals item dao test.
 */
class MealsItemDaoTest {
    /**
     * The Meals item dao.
     */
    MealsItemDao mealsItemDao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mealsItemDao = new MealsItemDao();
    }

    /**
     * Test get meals by search with results.
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testGetMealsBySearchWithResults() throws JsonProcessingException {
        String searchQuery = "chicken";

        List<MealsItem> meals = mealsItemDao.getMealsBySearch(searchQuery);

        assertNotNull(meals, "Meals should not be null for a valid search");
        assertFalse(meals.isEmpty(), "Meals list should not be empty for a valid search");
        assertEquals("Chicken Handi", meals.get(0).getStrMeal(), "First meal name should match expected value");

        logger.debug("First meal name: " + meals.get(0).getStrMeal());
    }

    /**
     * Test get meals by search no results.
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testGetMealsBySearchNoResults() throws JsonProcessingException {
        String searchQuery = "someNonExistentFood12345";

        List<MealsItem> meals = mealsItemDao.getMealsBySearch(searchQuery);

        assertNull(meals, "Meals should be null when no results found");
    }
}