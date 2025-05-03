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
public class AddRecipe extends HttpServlet {
    private final RecipeDao recipeDao = new RecipeDao();
    private final CategoryDao categoryDao = new CategoryDao();

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
        // Get recipe info from the form
        String name = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        String ingredients = request.getParameter("ingredients");
        String instructions = request.getParameter("instructions");

        // Fetch the Category object from the database using the category ID
        Category category = categoryDao.getById(categoryId);

        // Create a new Recipe object with form data and associated category
        Recipe recipe = new Recipe(name, category, ingredients, instructions);

        // Insert the recipe into the database and get generated ID
        int recipeId = recipeDao.insert(recipe);

        // Set recipe details in request scope to show on result page
        request.setAttribute("recipeName", name);
        request.setAttribute("categoryName", category.getName());
        request.setAttribute("ingredients", ingredients);
        request.setAttribute("instructions", instructions);

        // Forward to JSP page that displays the result
        request.getRequestDispatcher("/addedResult.jsp").forward(request, response);
    }
}
