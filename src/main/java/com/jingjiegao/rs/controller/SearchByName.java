package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.persistence.RecipeDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Search recipe.
 */
@WebServlet(
        urlPatterns = {"/searchByNameServlet"}
)
public class SearchByName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the parameter from the search input
        String name = request.getParameter("name");

        // Create DAO instance for accessing recipe data
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes;

        // Get all recipes if no name is entered
        // Or search by recipe name
        if (name == null || name.trim().isEmpty()) {
            recipes = recipeDao.getAll();
        } else {
            recipes = recipeDao.getByPropertyLike("name", name);
        }

        // Set the resulting recipe list in the request scope
        request.setAttribute("recipes", recipes);

        // Forward the request to the searchByName.jsp page for display
        RequestDispatcher dispatcher = request.getRequestDispatcher("/searchByName.jsp");
        dispatcher.forward(request, response);
    }
}