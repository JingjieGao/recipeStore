package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.Category;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.persistence.GenericDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * The type Edit recipe.
 */
@WebServlet(
        urlPatterns = {"/editRecipeServlet"}
)
public class EditRecipe extends HttpServlet {
    private final GenericDao<Recipe> recipeDao = new GenericDao<>(Recipe.class);
    private final GenericDao<Category> categoryDao = new GenericDao<>(Category.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parse recipe ID from the request
        int recipeId = Integer.parseInt(request.getParameter("recipe_id"));

        // Retrieve the recipe and categories
        Recipe recipe = recipeDao.getById(recipeId);
        List<Category> categories = categoryDao.getAll();

        // Set attributes to forward to the edit form
        request.setAttribute("recipe", recipe);
        request.setAttribute("categories", categories);

        // Forward the request to the edit form JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editRecipe.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // User not logged in, redirect to login page
            response.sendRedirect("logIn");
            return;
        }

        // Get form data
        int recipeId = Integer.parseInt(request.getParameter("recipe_id"));
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        String name = request.getParameter("name");
        String ingredients = request.getParameter("ingredients");
        String instructions = request.getParameter("instructions");

        // Get category from database
        Category category = categoryDao.getById(categoryId);

        // Retrieve the recipe to update
        Recipe recipe = recipeDao.getById(recipeId);

        // Update the recipe fields
        recipe.setName(name);
        recipe.setCategory(category);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);

        // Update the recipe in the database
        recipeDao.Update(recipe);

        // Set updated data in request scope for confirmation page
        request.setAttribute("recipeName", name);
        request.setAttribute("categoryName", category.getName());
        request.setAttribute("ingredients", ingredients);
        request.setAttribute("instructions", instructions);

        // Forward to the result confirmation JSP
        request.getRequestDispatcher("/editedResult.jsp").forward(request, response);
    }
}
