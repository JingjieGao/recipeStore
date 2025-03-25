package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Category;
import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.persistence.CategoryDao;
import com.jingjiegao.rs.persistence.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Add recipe servlet.
 */
@WebServlet(
        urlPatterns = {"/addRecipeServlet"}
)
public class AddRecipeServlet extends HttpServlet {
    private RecipeDao recipeDao = new RecipeDao();
    private CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryDao.getAll();

        request.setAttribute("categories", categories);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        String ingredients = request.getParameter("ingredients");
        String instructions = request.getParameter("instructions");

        Category category = categoryDao.getById(categoryId);

        if (category != null) {
            // Create a new Recipe object
            Recipe recipe = new Recipe(name, category, ingredients, instructions);

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
