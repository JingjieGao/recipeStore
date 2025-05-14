package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.persistence.GenericDao;
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
    private final GenericDao<Recipe> recipeDao = new GenericDao<>(Recipe.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // If the user is not logged in, redirect to login page
            response.sendRedirect("logIn");
            return;
        }

        try {
            // Retrieve the recipe ID from the request parameter
            int recipeId = Integer.parseInt(request.getParameter("recipe_id"));

            // Retrieve the recipe object by ID
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
