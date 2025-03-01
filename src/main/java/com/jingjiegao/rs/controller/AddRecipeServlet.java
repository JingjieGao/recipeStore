package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.persistence.RecipeDao;
import com.jingjiegao.rs.persistence.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Add recipe servlet.
 */
@WebServlet(
        urlPatterns = {"/addRecipeServlet"}
)
public class AddRecipeServlet extends HttpServlet {
    private RecipeDao recipeDao = new RecipeDao();
    private UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String ingredients = request.getParameter("ingredients");
        String instructions = request.getParameter("instructions");

        User user = userDao.getById(userId);

        if (user != null) {
            // Create a new Recipe object
            Recipe recipe = new Recipe(user, name, category, ingredients, instructions);

            // Insert the recipe into the database
            int recipeId = recipeDao.insert(recipe);

            // Set the response message
            response.getWriter().write("Recipe added successfully with ID: " + recipeId);

        } else {
            // Handle the case where the user is not found
            response.getWriter().write("User not found.");
        }
    }
}
