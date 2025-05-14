const form = document.getElementById('recipeForm');

form.addEventListener('submit', function(event) {
    if (!isLoggedIn) {
        event.preventDefault();

        const goToLogin = confirm("You must be logged in to submit a recipe.\n\nPress OK to go to login page.\nPress Cancel to return to home page.");
        if (goToLogin) {
            window.location.href = "logIn";
        } else {
            window.location.href = "index.jsp";
        }
    }
});