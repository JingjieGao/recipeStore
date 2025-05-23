<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />
<c:import url="nav.jsp" />

<html>
<body>
<!-- Main content -->
<main class="container mt-5">
    <h2 class="text-center mb-4">Recipes in Selected Category</h2>

    <c:if test="${empty recipes}">
        <p class="text-center text-muted">No recipes found in this category.</p>
    </c:if>

    <c:if test="${not empty recipes}">
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <c:forEach var="recipe" items="${recipes}">
                <div class="col">
                    <div class="card h-100 shadow-sm position-relative">
                        <!--Favorite Button -->
                        <c:if test="${not empty sessionScope.user}">
                            <c:set var="isFavorited" value="${favoriteStatusMap[recipe.id]}" />

                            <form action="addFavoriteServlet" method="post" class="star-button">
                                <input type="hidden" name="recipe_id" value="${recipe.id}"/>
                                <button type="submit" class="btn btn-link" title="${isFavorited ? 'Remove from Favorites' : 'Add to Favorites'}">
                                    <i class="${isFavorited ? 'fa-solid fa-star text-warning' : 'fa-regular fa-star'}"></i>
                                </button>
                            </form>
                        </c:if>

                        <div class="card-body">
                            <h5 class="card-title text-center fw-bold">${recipe.name}</h5>
                            <h6 class="card-subtitle mt-3 mb-3">
                                <strong>Category:</strong> <c:out value="${recipe.category.name}"/>
                            </h6>
                            <p class="card-text">
                                <strong>Ingredients:</strong><br/>
                                <c:out value="${recipe.ingredients}"/>
                            </p>
                            <p class="card-text">
                                <strong>Instructions:</strong><br/>
                                <c:out value="${recipe.instructions}"/>
                            </p>

                            <div class="text-end mt-3">
                                <!-- Update Button -->
                                <a href="editRecipeServlet?recipe_id=${recipe.id}"
                                   class="btn btn-sm btn-outline-primary">Update</a>

                                <!-- Only show Delete button if user is logged in -->
                                <c:if test="${not empty sessionScope.user}">
                                    <form action="deleteRecipeServlet" method="post"
                                          onsubmit="return confirm('Are you sure you want to delete this recipe?');"
                                          style="display:inline;">
                                        <input type="hidden" name="recipe_id" value="${recipe.id}"/>
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