package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.entity.Favorite;
import com.jingjiegao.rs.entity.Recipe;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.persistence.FavoriteDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type List favorite.
 */
@WebServlet(
        urlPatterns = {"/listFavoriteServlet"}
)
public class ListFavorite extends HttpServlet {
    private final FavoriteDao favoriteDao = new FavoriteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // User not logged in, redirect to login page
            response.sendRedirect("logIn");
            return;
        }

        // I tried to use the code below instead:
        // List<Favorite> favoriteRecipes = user.getFavorites();
        // but I encountered a LazyInitializationException.
        // Use favoriteDao.getFavoritesByUserId(user.getId()) to avoid the LazyInitializationException
        // and ensure that it fetches the most up-to-date data from the database.
        List<Favorite> favoriteRecipes = favoriteDao.getFavoritesByUserId(user.getId());

        // Create a map to store each recipe's favorite status
        Map<Integer, Boolean> favoriteStatusMap = new HashMap<>();

        for (Favorite favorite : favoriteRecipes) {
            Recipe recipe = favorite.getRecipe();
            boolean isFavorited = favoriteDao.isRecipeFavoritedByUser(recipe.getId(), user.getId());
            favoriteStatusMap.put(recipe.getId(), isFavorited);
        }

        // Set request attributes for all favorites page
        request.setAttribute("favoriteRecipes", favoriteRecipes);
        request.setAttribute("favoriteStatusMap", favoriteStatusMap);

        // Forward to JSP page that displays all favorites
        request.getRequestDispatcher("/favoriteRecipes.jsp").forward(request, response);
    }
}
