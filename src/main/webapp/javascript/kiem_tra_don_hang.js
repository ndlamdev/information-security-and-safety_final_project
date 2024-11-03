$(document).ready(function () {
    displayInputCheck();
});

function displayInputCheck() {
    var arryType = $("input[type='radio']");
    var arrayInput = $("div.input");
    arryType.on("change", function () {
        var key = $(this).attr("id");
        for(var input of arrayInput){
            if(input.getAttribute("data-bs-target") === key){
                input.setAttribute("class","input active");
            }else{
                input.setAttribute("class", "input");
            }
        }
    });
}