package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.persistence.RecipeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * The type Search by category.
 */
@WebServlet(
        urlPatterns = {"/searchByCategoryServlet"}
)
public class SearchByCategory extends HttpServlet {
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

            // Create a RecipeDao instance to interact with the database
            RecipeDao recipeDao = new RecipeDao();

            // Get all recipes that match the provided category ID
            List<Recipe> recipes = recipeDao.getByCategoryId(categoryId);

            // Set the list of recipes and selected category ID as request attributes
            request.setAttribute("recipes", recipes);
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
