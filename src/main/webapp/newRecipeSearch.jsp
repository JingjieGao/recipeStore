<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />
<c:import url="nav.jsp" />
<!-- Page-specific CSS -->
<link rel="stylesheet" href="css/card.css">

<html>
<body>
<!-- New Recipe Search Form -->
<main class="container">
    <div class="card p-4 shadow-sm">
        <h4 class="mb-3">Explore New Recipes</h4>
        <form action="api/recipe/search" method="get" class="row g-3">
            <div class="col-md-7">
                <input type="text" name="searchRecipe" class="form-control" placeholder="Enter keywords" required>
            </div>
            <div class="col-md-5 d-flex">
                <button type="submit" class="btn btn-light custom-btn">Search</button>
            </div>
        </form>
    </div>
</main>
</body>
</html>
