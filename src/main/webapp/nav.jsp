<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- navigation for all page -->
<header class="py-3 mb-4">
    <div class="container">
        <h1 class="text-center mb-4">Recipe Store</h1>

        <nav class="d-flex justify-content-between align-items-center">
            <ul class="nav d-flex justify-content-start">
                <!-- Home Link -->
                <li class="nav-item"><a class="nav-link" href="index.jsp">Home</a></li>

                <!-- Category Dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="categoryDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Category
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="categoryDropdown">
                        <li><a class="dropdown-item" href="searchByCategoryServlet?categoryId=1">Appetizer</a></li>
                        <li><a class="dropdown-item" href="searchByCategoryServlet?categoryId=2">Entree</a></li>
                        <li><a class="dropdown-item" href="searchByCategoryServlet?categoryId=3">Dessert</a></li>
                        <li><a class="dropdown-item" href="searchByCategoryServlet?categoryId=4">Beverage</a></li>
                        <li><a class="dropdown-item" href="searchByCategoryServlet?categoryId=5">Other</a></li>
                    </ul>
                </li>

                <!-- Add Recipes Link -->
                <li class="nav-item"><a class="nav-link" href="addRecipeServlet">Add Recipe</a></li>

                <!-- Favorite Recipes Link -->
                <li class="nav-item"><a class="nav-link" href="listFavoriteServlet">My Favorites</a></li>

                <!-- New Recipe Search Page Link -->
                <li class="nav-item"><a class="nav-link" href="newRecipeSearch.jsp">Explore</a></li>
            </ul>

            <!-- Login Link -->
            <ul class="nav d-flex justify-content-end">
                <c:if test="${empty sessionScope.user.username}">
                    <li class="nav-item"><a class="nav-link" href="logIn">Login</a></li>
                </c:if>
                <c:if test="${not empty sessionScope.user.username}">
                    <li class="nav-item disabled"><span class="nav-link">Welcome ${sessionScope.user.username}</span></li>
                </c:if>
            </ul>
        </nav>
    </div>
</header>
