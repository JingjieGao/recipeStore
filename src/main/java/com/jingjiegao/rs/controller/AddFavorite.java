package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Favorite;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.persistence.FavoriteDao;
import com.jingjiegao.rs.persistence.RecipeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The type Add favorite.
 */
@WebServlet(
        urlPatterns = {"/addFavoriteServlet"}
)
public class AddFavorite extends HttpServlet {
    private final RecipeDao recipeDao = new RecipeDao();
    private final FavoriteDao favoriteDao = new FavoriteDao();

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

        // Get recipe ID from request parameter
        String recipeIdParam = request.getParameter("recipe_id");

        if (recipeIdParam != null) {
            try {
                // Parse recipe ID and get the Recipe object
                int recipeId = Integer.parseInt(recipeIdParam);
                Recipe recipe = recipeDao.getById(recipeId);

                // If recipe not found, forward to error page
                if (recipe == null) {
                    request.setAttribute("errorMessage", "Recipe not found.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }

                // Check if recipe already favorited
                boolean alreadyFavorited = favoriteDao.isRecipeFavoritedByUser(recipeId, user.getId());
                if (alreadyFavorited) {
                    request.setAttribute("errorMessage", "This recipe is already in your favorites.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }

                // Create a new Favorite and associate it with the user and recipe
                Favorite favorite = new Favorite();
                favorite.setUser(user);
                favorite.setRecipe(recipe);

                // Insert the favorite into the database
                int favoriteId = favoriteDao.insert(favorite);

                // Redirect or show error based on the insert result
                if (favoriteId > 0) {
                    response.sendRedirect("searchByNameServlet");
                } else {
                    request.setAttribute("errorMessage", "Failed to add to favorites.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                // Handle invalid recipe ID format
                request.setAttribute("errorMessage", "Invalid recipe ID.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            // Handle missing recipe ID parameter
            request.setAttribute("errorMessage", "Missing recipe ID.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
