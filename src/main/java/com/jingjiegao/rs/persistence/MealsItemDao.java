package com.jingjiegao.rs.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jingjiegao.rs.recipeApi.MealsItem;
import com.jingjiegao.rs.recipeApi.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * The type Meals item dao.
 */
public class MealsItemDao {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final ResponseDao responseDao = new ResponseDao();


    /**
     * Gets meals by search.
     *
     * @param searchString the search string
     * @return the meals by search
     * @throws JsonProcessingException the json processing exception
     */
    public List<MealsItem> getMealsBySearch(String searchString) throws JsonProcessingException {
        Response response = responseDao.searchRecipe(searchString);
        List<MealsItem> mealsItem = response.getMeals();
        logger.debug("No meals found: " + mealsItem);
        return mealsItem;
    }
}
