package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.persistence.FavoriteDao;
import com.jingjiegao.rs.persistence.GenericDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Search recipe.
 */
@WebServlet(
        urlPatterns = {"/searchByNameServlet"}
)
public class SearchByName extends HttpServlet {
    private final GenericDao<Recipe> recipeDao = new GenericDao<>(Recipe.class);
    private final FavoriteDao favoriteDao = new FavoriteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the parameter from the search input
        String name = request.getParameter("name");

        // Get all recipes if no name is entered or search by recipe name
        List<Recipe> recipes;
        if (name == null || name.trim().isEmpty()) {
            recipes = recipeDao.getAll();
        } else {
            recipes = recipeDao.getByPropertyLike("name", name);
        }

        // Create a map to store each recipe's favorite status
        Map<Integer, Boolean> favoriteStatusMap = new HashMap<>();

        // Check if the user is logged in to manage favorite status
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Only fetch the favorite status if the user is logged in
            for (Recipe recipe : recipes) {
                boolean isFavorited = favoriteDao.isRecipeFavoritedByUser(recipe.getId(), user.getId());
                favoriteStatusMap.put(recipe.getId(), isFavorited);
            }
        }

        // Set the resulting recipe list in the request scope
        request.setAttribute("recipes", recipes);
        request.setAttribute("favoriteStatusMap", favoriteStatusMap);

        // Forward the request to the searchByName.jsp page for display
        request.getRequestDispatcher("/searchByName.jsp").forward(request, response);
    }
}