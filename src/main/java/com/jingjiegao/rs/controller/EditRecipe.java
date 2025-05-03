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

@WebServlet(
        urlPatterns = {"/editRecipeServlet"}
)
public class EditRecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int recipeId = Integer.parseInt(request.getParameter("recipe_id"));
        RecipeDao recipeDao = new RecipeDao();
        CategoryDao categoryDao = new CategoryDao();

        Recipe recipe = recipeDao.getById(recipeId);
        request.setAttribute("recipe", recipe);
        request.setAttribute("categories", categoryDao.getAll());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/editRecipe.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int recipeId = Integer.parseInt(request.getParameter("recipe_id"));
        String name = request.getParameter("name");
        String ingredients = request.getParameter("ingredients");
        String instructions = request.getParameter("instructions");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));

        RecipeDao recipeDao = new RecipeDao();
        CategoryDao categoryDao = new CategoryDao();

        Recipe recipeToUpdate = recipeDao.getById(recipeId);
        recipeToUpdate.setName(name);
        recipeToUpdate.setIngredients(ingredients);
        recipeToUpdate.setInstructions(instructions);

        Category category = categoryDao.getById(categoryId);
        recipeToUpdate.setCategory(category);

        recipeDao.update(recipeToUpdate);

        request.setAttribute("recipeName", name);
        request.setAttribute("categoryName", category.getName());
        request.setAttribute("ingredients", ingredients);
        request.setAttribute("instructions", instructions);

        // Forward to the result page to display success
        request.getRequestDispatcher("/editedRecipeResult.jsp").forward(request, response);
    }
}
