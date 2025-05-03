<%@include file="head.jsp" %>
<%@include file="taglib.jsp" %>
<%@include file="nav.jsp" %>

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
                    <div class="card h-100 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title text-primary">${recipe.name}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">
                                Category: <c:out value="${recipe.category.name}"/>
                            </h6>
                            <p class="card-text">
                                <strong>Ingredients:</strong><br/>
                                <c:out value="${recipe.ingredients}"/>
                            </p>
                            <p class="card-text">
                                <strong>Instructions:</strong><br/>
                                <c:out value="${recipe.instructions}"/>
                            </p>

                            <!-- Update Button -->
                            <div class="text-end mt-3">
                                <a href="editRecipeServlet?recipe_id=${recipe.id}"
                                   class="btn btn-sm btn-outline-primary">Update</a>

                                <!-- Only show Delete button if user is logged in -->
                                <c:if test="${not empty sessionScope.userName}">
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
