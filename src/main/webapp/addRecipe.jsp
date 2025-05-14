<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />
<c:import url="nav.jsp" />

<html>
<body>
<!-- Main content -->
<main class="container d-flex justify-content-center mt-5">
    <div class="card shadow-sm p-4 w-75">
        <div class="card-body">
            <h2 class="card-title text-center text-black font-weight-bold mb-5 py-2">Add Recipe</h2>

            <form id="recipeForm" action="addRecipeServlet" method="POST">
                <div class="mb-3">
                    <label for="name" class="form-label">Name:</label>
                    <input type="text" id="name" name="name" class="form-control" required/>
                </div>

                <div class="mb-3">
                    <label for="category_id" class="form-label">Category:</label>
                    <select id="category_id" name="category_id" class="form-select" required>
                        <option value="" disabled selected>Select a category</option>
                        <!-- Iterate over categories to populate the dropdown -->
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="ingredients" class="form-label">Ingredients:</label>
                    <textarea id="ingredients" name="ingredients" rows="4" class="form-control" required></textarea>
                </div>

                <div class="mb-5">
                    <label for="instructions" class="form-label">Instructions:</label>
                    <textarea id="instructions" name="instructions" rows="4" class="form-control" required></textarea>
                </div>

                <div class="text-end">
                    <button type="submit" class="btn btn-primary w-25">Submit</button>
                </div>
            </form>
        </div>
    </div>
</main>

<!-- Check if user is logged in -->
<script>
    const isLoggedIn = ${user != null};
</script>
<script src="js/checkUser.js"></script>
</body>
</html>
