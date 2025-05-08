<%@include file="head.jsp" %>
<%@include file="taglib.jsp" %>
<%@include file="nav.jsp" %>

<html>
<body>
<!-- Main content -->
<main class="container">
    <h2 class="text-center mb-4">Your Favorite Recipes</h2>

    <c:if test="${empty favoriteRecipes}">
        <p class="text-center text-muted">You don't have any favorite recipes yet.</p>
    </c:if>

    <c:if test="${not empty favoriteRecipes}">
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <c:forEach var="favorite" items="${favoriteRecipes}">
                <div class="col">
                    <div class="card h-100 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title text-primary">${favorite.recipe.category.name}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">
                                Category: <c:out value="${favorite.recipe.category.name}"/>
                            </h6>
                            <p class="card-text">
                                <strong>Ingredients:</strong><br/>
                                <c:out value="${favorite.recipe.ingredients}"/>
                            </p>
                            <p class="card-text">
                                <strong>Instructions:</strong><br/>
                                <c:out value="${favorite.recipe.instructions}"/>
                            </p>

                            <div class="text-end mt-3">
                                <!--Favorite Button -->
                                <c:if test="${not empty sessionScope.user}">
                                    <c:set var="isFavorited" value="${favoriteStatusMap[favorite.recipe.id]}" />

                                    <form action="addFavoriteServlet" method="post" class="star-button">
                                        <input type="hidden" name="recipe_id" value="${favorite.recipe.id}"/>
                                        <button type="submit" class="btn btn-link" title="${isFavorited ? 'Remove from Favorites' : 'Add to Favorites'}">
                                            <i class="${isFavorited ? 'fa-solid fa-star text-warning' : 'fa-regular fa-star'}"></i>
                                        </button>
                                    </form>
                                </c:if>

                                <!-- Update Button -->
                                <a href="editRecipeServlet?recipe_id=${favorite.recipe.id}"
                                   class="btn btn-sm btn-outline-primary">Update</a>

                                <!-- Only show Delete button if user is logged in -->
                                <c:if test="${not empty sessionScope.user}">
                                    <form action="deleteRecipeServlet" method="post"
                                          onsubmit="return confirm('Are you sure you want to delete this recipe?');"
                                          style="display:inline;">
                                        <input type="hidden" name="recipe_id" value="${favorite.recipe.id}"/>
                                        <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</main>
</body>
</html>