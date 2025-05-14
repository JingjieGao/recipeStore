<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />
<c:import url="nav.jsp" />

<html>
<body>
<main class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="text-center mb-4">
                <h2 class="text-success">Recipe Added Successfully</h2>
            </div>
            <table class="table table-bordered">
                <tr>
                    <th>Name</th>
                    <td>${recipeName}</td>
                </tr>
                <tr>
                    <th>Category</th>
                    <td>${categoryName}</td>
                </tr>
                <tr>
                    <th>Ingredients</th>
                    <td>${ingredients}</td>
                </tr>
                <tr>
                    <th>Instructions</th>
                    <td>${instructions}</td>
                </tr>
            </table>
        </div>
    </div>
</main>
</body>
</html>
