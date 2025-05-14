<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />
<c:import url="nav.jsp" />

<html>
<body>
<main class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="alert alert-danger text-center" role="alert">
                <h1 class="mb-3">Oops! Something went wrong.</h1>
                <p>
                    <c:choose>
                        <c:when test="${not empty errorMessage}">
                            ${errorMessage}
                        </c:when>
                        <c:otherwise>
                            An unexpected error occurred.
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>
        </div>
    </div>
</main>
</body>
</html>
