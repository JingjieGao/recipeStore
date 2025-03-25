<%@include file="head.jsp"%>
<%@include file="taglib.jsp"%>

<html>
    <body class="bg-light">
        <div class="container d-flex justify-content-center mt-5">
            <div class="card shadow-sm p-4 w-75">
                <div class="card-body">
                    <h2 class="card-title text-center text-black font-weight-bold mb-5 py-2">Add Recipe</h2>
                    <form action="addRecipeServlet" method="POST">
                        <div class="mb-3">
                            <label for="name" class="form-label">Name:</label>
                            <input type="text" id="name" name="name" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="category_id" class="form-label">Category:</label>
                            <select id="category_id" name="category_id" class="form-select" required>
                                <option value="" disabled selected>Select a category</option>
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
        </div>
    </body>
</html>