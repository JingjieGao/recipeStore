<%@include file="head.jsp" %>
<%@include file="taglib.jsp" %>
<%@include file="nav.jsp" %>

<html>
<body>
<!-- Main Content -->
<main class="container">
    <!-- Search By Name Form -->
    <div class="card p-4 shadow-sm">
        <h4 class="mb-3">Search Recipes</h4>
        <form action="searchByNameServlet" method="GET" class="row g-3">
            <div class="col-md-6">
                <input type="text" name="name" class="form-control" placeholder="Search by name" required>
            </div>
            <div class="col-md-6 d-flex">
                <button type="submit" class="btn btn-primary me-2">Search</button>
                <a href="searchByNameServlet" class="btn btn-secondary">All Recipes</a>
            </div>
        </form>
    </div>
</main>
</body>
</html>
