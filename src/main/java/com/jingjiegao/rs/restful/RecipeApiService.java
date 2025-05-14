package com.jingjiegao.rs.restful;

import com.jingjiegao.rs.persistence.MealsItemDao;
import com.jingjiegao.rs.recipeApi.MealsItem;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The type Recipe api service.
 */
@Path("/recipe")
public class RecipeApiService {

    /**
     * Gets meals by search.
     *
     * @param searchString the search string
     * @return the meals by search
     * @throws Exception the exception
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("search")
    public Response getMealsBySearch(@QueryParam("searchRecipe") String searchString) throws Exception {
        MealsItemDao mealsItemDao = new MealsItemDao();

        List<MealsItem> meals = mealsItemDao.getMealsBySearch(searchString);

        if (meals == null || meals.isEmpty()) {
            return Response.status(404).entity("No recipes found for the search term: " + searchString).build();
        }

        StringBuilder htmlResponse = new StringBuilder();

        for (MealsItem meal : meals) {
            htmlResponse.append("<b>Meal: </b>").append(meal.getStrMeal()).append("<br>");
            htmlResponse.append("<b>Category: </b>").append(meal.getStrCategory()).append("<br>");
            htmlResponse.append("<b>Area: </b>").append(meal.getStrArea()).append("<br>");
            htmlResponse.append("<b>Instructions: </b>").append(meal.getStrInstructions()).append("<br>");
            htmlResponse.append("<b>YouTube: </b>").append(meal.getStrYoutube()).append("<br><br><hr>");
        }

        return Response.status(200).entity(htmlResponse.toString()).build();
    }
}
