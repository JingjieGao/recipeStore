<%@include file="charsetConfig.jsp"%>
<%@include file="head.jsp"%>
<%@include file="taglib.jsp"%>

<html>
<body>
<h2 class="center">Recipe Added Successfully</h2>
<table>
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

<div class="center">
    <a href="index.jsp">Return to Home</a>
</div>
</body>
</html>
