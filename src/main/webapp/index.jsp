<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />
<c:import url="nav.jsp" />
<!-- Page-specific CSS -->
<link rel="stylesheet" href="css/card.css">

<html>
<body>
<!-- Main Content -->
<main class="container mt-5">
    <!-- Search By Name Form -->
    <div class="card p-4">
        <h4 class="mb-3">Search Recipes</h4>
        <form action="searchByNameServlet" method="GET" class="row g-3">
            <div class="col-md-7">
                <input type="text" name="name" class="form-control" placeholder="Search by name" required>
            </div>
            <div class="col-md-5 d-flex">
                <button type="submit" class="btn btn-light custom-btn me-2">Search</button>
                <a href="searchByNameServlet" class="btn btn-light custom-btn">All Recipes</a>
            </div>
        </form>
    </div>
</main>
</body>
</html>
