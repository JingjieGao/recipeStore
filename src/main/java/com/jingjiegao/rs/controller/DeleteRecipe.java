package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.persistence.RecipeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The type Delete recipe.
 */
@WebServlet(
        urlPatterns = {"/deleteRecipeServlet"}
)
public class DeleteRecipe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in by retrieving the session
        HttpSession session = request.getSession(false);
        // Retrieve the userName from the session, or null if session doesn't exist
        String userName = (session != null) ? (String) session.getAttribute("userName") : null;

        // If user is not logged in, redirect to login page with a message
        if (userName == null || userName.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/logIn?message=Please+login+to+delete+recipes");
            return;
        }

        try {
            // Retrieve the recipe ID from the request parameter
            int recipeId = Integer.parseInt(request.getParameter("recipe_id"));

            // Create an instance of RecipeDao to interact with the database
            RecipeDao recipeDao = new RecipeDao();
            // Get the Recipe object by its ID
            Recipe recipe = recipeDao.getById(recipeId);

            // If the recipe is found, delete it
            if (recipe != null) {
                recipeDao.delete(recipe);
                response.sendRedirect(request.getContextPath() + "/searchByNameServlet");
            } else {
                // If the recipe is not found, set the error message and forward to error page
                request.setAttribute("errorMessage", "Recipe not found");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            // If recipe ID is not a valid integer, set the error message and forward to error page
            request.setAttribute("errorMessage", "Invalid recipe ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
