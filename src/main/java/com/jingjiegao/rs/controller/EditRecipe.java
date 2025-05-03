package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.Category;
import com.jingjiegao.rs.persistence.RecipeDao;
import com.jingjiegao.rs.persistence.CategoryDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * The type Edit recipe.
 */
@WebServlet(
        urlPatterns = {"/editRecipeServlet"}
)
public class EditRecipe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parse recipe ID from the request
        int recipeId = Integer.parseInt(request.getParameter("recipe_id"));

        // Create DAO instances
        RecipeDao recipeDao = new RecipeDao();
        CategoryDao categoryDao = new CategoryDao();

        // Retrieve the recipe object by ID
        Recipe recipe = recipeDao.getById(recipeId);

        // Retrieve all categories for the dropdown list
        request.setAttribute("recipe", recipe);
        request.setAttribute("categories", categoryDao.getAll());

        // Forward the request to the edit form JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editRecipe.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parse form parameters
        int recipeId = Integer.parseInt(request.getParameter("recipe_id"));
        String name = request.getParameter("name");
        String ingredients = request.getParameter("ingredients");
        String instructions = request.getParameter("instructions");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));

        // Create DAO instances
        RecipeDao recipeDao = new RecipeDao();
        CategoryDao categoryDao = new CategoryDao();

        // Retrieve the existing recipe from the database
        Recipe recipeToUpdate = recipeDao.getById(recipeId);

        // Set updated values from the form
        recipeToUpdate.setName(name);
        recipeToUpdate.setIngredients(ingredients);
        recipeToUpdate.setInstructions(instructions);

        // Get and assign the selected category
        Category category = categoryDao.getById(categoryId);
        recipeToUpdate.setCategory(category);

        // Update the recipe in the database
        recipeDao.update(recipeToUpdate);

        // Set updated data in request scope for confirmation page
        request.setAttribute("recipeName", name);
        request.setAttribute("categoryName", category.getName());
        request.setAttribute("ingredients", ingredients);
        request.setAttribute("instructions", instructions);

        // Forward to the result confirmation JSP
        request.getRequestDispatcher("/editedResult.jsp").forward(request, response);
    }
}
