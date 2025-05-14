package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Category;
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
import java.util.List;

/**
 * The type Add recipe servlet.
 */
@WebServlet(
        urlPatterns = {"/addRecipeServlet"}
)
public class AddRecipe extends HttpServlet {
    private final GenericDao<Recipe> recipeDao = new GenericDao<>(Recipe.class);
    private final GenericDao<Category> categoryDao = new GenericDao<>(Category.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve list of all categories from the database
        List<Category> categories = categoryDao.getAll();

        // Set categories in request scope for display in the form
        request.setAttribute("categories", categories);

        // Forward to the JSP form page
        request.getRequestDispatcher("/addRecipe.jsp").forward(request, response);
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
        String name = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        String ingredients = request.getParameter("ingredients");
        String instructions = request.getParameter("instructions");

        // Get category from database
        Category category = categoryDao.getById(categoryId);

        // Create and save the recipe
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setCategory(category);
        recipe.setUser(user);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);

        recipeDao.insert(recipe);

        // Set request attributes for result page
        request.setAttribute("recipeName", name);
        request.setAttribute("categoryName", category.getName());
        request.setAttribute("ingredients", ingredients);
        request.setAttribute("instructions", instructions);

        // Forward to JSP page that displays the result
        request.getRequestDispatcher("/addedResult.jsp").forward(request, response);
    }
}