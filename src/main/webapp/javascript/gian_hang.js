$(document).ready(function () {
    addActionFilterMore();
    actionFastSee();
    changeAmount();
    addProductCart();
    buyNow();
    loadBanner();
});


function addActionFilterMore() {
    $("div.filter>ul>li.filter-more").click(function () {
        if ($(this).hasClass("active")) {
            $(this).parent().children("li.hidden").addClass("d-none");
            $(this).removeClass("active");
            $(this).empty();
            $(this).append('<span class="border-0">Xem thêm <i class="fa-solid fa-arrow-right"></i></span>');
        } else {
            $(this).parent().children("li.hidden").removeClass("d-none");
            $(this).addClass("active");
            $(this).empty();
            $(this).append('<span class="border-0">Rút gọn <i class="fa-solid fa-arrow-left"></i></span>');
        }
    });
}


function actionFastSee() {
    $(".show-models").click(function () {
        const productId = $(this).attr("product-id");
        $.ajax({
            url: 'show_models',
            data: {
                id: productId
            },
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                const product = data.product;
                const models = data.models;
                showModalModels(product, models);
            },
            error: function () {
                window.location.replace("error.jsp");
            }
        });
    });
}


function showModalModels(product, models) {
    let htmlModelImage = ``;
    let htmlOptionModel = ``;
    for (let i = 0; i < models.length; i++) {
        const model = models[i];
        /*Tạo HTML image*/
        htmlModelImage += `<div class="carousel-item`
        if (i === 0) {
            htmlModelImage += ` active`
        }
        htmlModelImage += `"><img src="${model.urlImage}" class="d-block w-100" alt="${model.name}.png">
                               </div>`

        /*Tạo HTML option*/
        htmlOptionModel += `<div class="product-sw-select-item">
                                <button model-id="${model.id}" type="button" 
                                       class="`
        if (i === 0) {
            htmlOptionModel += `active `
        }
        if (model.quantity > model.totalQuantitySold) {
            htmlOptionModel += `model`
        }
        htmlOptionModel += `" data-bs-target="#carousel" data-bs-slide-to="${i}" aria-label="Slide ${i}">
                                           <img src="${model.urlImage}" alt="${model.name}.png">
                                           <span>${model.name}</span>
                                       </button>
                                </div>`
    }

    $("#model-image").html(htmlModelImage);
    $("#option-model").html(htmlOptionModel);
    $("#product-name").html(`<h5>${product.name}</h5>`);
    $(".product-action").find("button").attr("product-id", product.id);
    selectModel();

    $("#show-modal").click();
}

function changeAmount() {
    $("button.minus-quantity").click(function () {
        let input = $(this).next();
        if (Number(input.val()) > 1) {
            input.val(Number(input.val()) - 1);
        }
    });

    $("button.plus-quantity").click(function () {
        let input = $(this).prev();
        input.val(Number(input.val()) + 1);
    });
}

function selectModel() {
    $(".product .product-model button.model").click(function () {
        $(".product .product-model button.model").removeClass("active");
        $(this).addClass("active")
        $("#quantity").val(1);
    });
}

function addProductCart() {
    $("#add-cart").click(function () {
        $.ajax({
            url: 'cart',
            data: {
                "action": "add",
                "product-id": $(this).attr("product-id"),
                "model-id": $("button.model.active").attr("model-id"),
                "quantity": $("#quantity").val(),
                "checked": "false"
            },
            method: 'POST',
            dataType: 'json',
            success: function (data) {
                $("#amount-product").text(data.amountProduct);
                $("#quantity").val(1);
                $.notify("Thêm sản phẩm thành công!", "success");
            },
            error: function () {
                $.notify("Sản phẩm đã hết!", "error");
            }
        });
    });
}

function buyNow() {
    $("#buy-now").click(function () {
        const productId = $(this).attr("product-id");
        const modelId = $("button.model.active").attr("model-id");
        const quantity = $("#quantity").val();
        const formBuyNow =
            `<form id="form-buy-now" hidden="" method="POST" action="buy_now">
            <input type="text" name="action" value="buy-now">
            <input type="text" name="product-id" value="${productId}">
            <input type="text" name="model-id" value="${modelId}">
            <input type="number" name="quantity" value="${quantity}">
            <input type="submit" id="submit-buy-now"> 
        </form>`;
        $("body").append(formBuyNow);
        $("#submit-buy-now").click();
        $("#form-buy-now").remove();
    });
}

function loadBanner() {
    $.ajax({
        url: "banner",
        data: {
            action: "banner"
        },
        dataType: "json",
        method: "GET",
        success: function (data) {
            let bannerInner = $("#banner-inner");
            let bannerIndicators = $("#banner-indicators");
            for (let i = 0; i < data.banners.length; i++) {
                const url = data.banners[i].urlImage;
                let aBanner = bannerInner.html() + `<div class="carousel-item w-100">
                    <img class="w-100" src="${url}" alt="banner${i}.png">
                </div>`;
                let aBannerIndicators = bannerIndicators.html() + `<button type="button" data-bs-target="#carouselIndicators" data-bs-slide-to="${i}"
                        aria-current="false" aria-label="Slide ${i + 1}"></button>`;
                bannerInner.html(aBanner);
                bannerIndicators.html(aBannerIndicators);
            }
            let banners = bannerInner.find(".carousel-item");
            if (banners.length) {
                banners.first().addClass("active");
                bannerIndicators.find("button").first().addClass("active");
                bannerIndicators.find("button").first().attr("aria-current", true);
            }
        },
        error: function (jqXHR) {
            console.log(jqXHR.responseText);
        }
    });
}

