package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.persistence.FavoriteDao;
import com.jingjiegao.rs.persistence.RecipeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Search by category.
 */
@WebServlet(
        urlPatterns = {"/searchByCategoryServlet"}
)
public class SearchByCategory extends HttpServlet {
    private final RecipeDao recipeDao = new RecipeDao();
    private final FavoriteDao favoriteDao = new FavoriteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the category ID from the query parameter
        String categoryIdParam = request.getParameter("categoryId");

        // Check if the category ID is missing or empty
        if (categoryIdParam == null || categoryIdParam.isEmpty()) {
            request.setAttribute("errorMessage", "Category is empty");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        try {
            // Convert the category ID from String to int
            int categoryId = Integer.parseInt(categoryIdParam);

            // Get all recipes that match the provided category ID
            List<Recipe> recipes = recipeDao.getByCategoryId(categoryId);

            // Create a map to store each recipe's favorite status
            Map<Integer, Boolean> favoriteStatusMap = new HashMap<>();

            // Check if the user is logged in to manage favorite status
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");

            if (user != null) {
                // Only fetch the favorite status if the user is logged in
                for (Recipe recipe : recipes) {
                    boolean isFavorited = favoriteDao.isRecipeFavoritedByUser(recipe.getId(), user.getId());
                    favoriteStatusMap.put(recipe.getId(), isFavorited);
                }
            }

            // Set recipes and other info to request attributes to send to JSP
            request.setAttribute("recipes", recipes);
            request.setAttribute("favoriteStatusMap", favoriteStatusMap);
            request.setAttribute("selectedCategoryId", categoryId);

            // Forward the request to searchByCategory.jsp to display the results
            request.getRequestDispatcher("/searchByCategory.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // If the category ID is not a valid integer, show an error message
            request.setAttribute("errorMessage", "Invalid category ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
