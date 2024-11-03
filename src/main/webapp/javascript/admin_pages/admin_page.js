$(document).ready(function () {
    $("#menu .account a").attr("href", "");
    $("#menu").find(".account").attr("class", "account col-lg-2 col-md-1 col-sm-1 border-0 px-lg-0");
    displayButtonSignOut();

    $(".filter-item .dropdown a").click(function () {
        const title = $(this).text();
        const button = $(this).parents(".dropdown").find("button");
        button.text(title);
    });

    $(".navbar-brand").attr("href", "dashboard.jsp");
});

function displayButtonSignOut() {
    $("#menu").find(".account").after(`
        <div id="signout" class="sign_out col-lg-1 col-md-1 col-sm-1 border-0 px-lg-0">
            <button type="button" class="btn d-flex float-lg-end  me-xl-4 me-lg-2">
                <span class="material-symbols-outlined">logout</span>
            </button>
        </div>
    `);

    $("#signout").click(function () {
        $.get("/maven_war/logout", function (){
            window.location.replace("../index.jsp");
        });
    });
}






