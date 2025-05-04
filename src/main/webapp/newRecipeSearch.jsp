<%@include file="head.jsp" %>
<%@include file="taglib.jsp" %>
<%@include file="nav.jsp" %>

<html>
<body>
<!-- New Recipe Search Form -->
<main class="container">
    <div class="card p-4 shadow-sm">
        <h4 class="mb-3">Explore New Recipes</h4>
        <form action="api/recipe/search" method="get" class="row g-3">
            <div class="col-md-6">
                <input type="text" name="searchRecipe" class="form-control" placeholder="Enter keywords">
            </div>
            <div class="col-md-6 d-flex">
                <button type="submit" class="btn btn-primary me-2">Search</button>
            </div>
        </form>
    </div>
</main>
</body>
</html>
