<!-- navigation for all page -->
<header class="bg-light py-3 shadow-sm mb-4">
    <div class="container d-flex justify-content-between align-items-center">
        <h1 class="h3 m-0">Recipe Store</h1>
        <nav>
            <ul class="nav">
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
                    </ul>
                </li>

                <li class="nav-item"><a class="nav-link" href="addRecipeServlet">Add Recipe</a></li>

                <!-- New Recipe Search Page Link -->
                <li class="nav-item"><a class="nav-link" href="newRecipeSearch.jsp">Explore</a></li>

                <c:if test="${empty sessionScope.userName}">
                    <li class="nav-item"><a class="nav-link" href="logIn">Login</a></li>
                </c:if>

                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item disabled"><span class="nav-link">Welcome ${sessionScope.userName}</span></li>
                </c:if>
            </ul>
        </nav>
    </div>
</header>
