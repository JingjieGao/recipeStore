<%@include file="head.jsp" %>
<%@include file="taglib.jsp" %>
<%@include file="nav.jsp" %>

<html>
<body>
<main class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="alert alert-danger text-center" role="alert">
                <h1 class="mb-3">Oops! Something went wrong.</h1>
                <p>
                    <%= request.getAttribute("errorMessage") != null
                            ? request.getAttribute("errorMessage")
                            : "An unexpected error occurred." %>
                </p>
            </div>
        </div>
    </div>
</main>
</body>
</html>
