$(document).ready(function () {
    editProduct();
    lockProduct();
    selectModel();
});

function editProduct() {
    $(".edit-product").click(function () {
        const productId = $(this).attr("product-id");
        $.ajax({
            url: "product_manager",
            method: "POST",
            data: {
                action: "edit-product",
                "product-id": productId,
            },
            dataType: "text",
            success: function (data) {
                window.location.replace("chinh_sua_san_pham.jsp");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR)
                console.log(textStatus)
                console.log(errorThrown)
            }
        });
    });
}

function lockProduct() {
    $(".lock-product").click(function () {
        const productId = $(this).attr("product-id");
        const lock = $(this).attr("lock");
        const buttonLock = $(this);
        $.ajax({
            url: "product_manager",
            method: "POST",
            data: {
                action: "lock-product",
                "product-id": productId,
                lock: lock
            },
            dataType: "JSON",
            success: function (data) {
                console.log(data);
                buttonLock.attr("lock", data.lock);
                buttonLock.text(data.icon);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR)
                console.log(textStatus)
                console.log(errorThrown)
            }
        });
    });
}

function selectModel() {
    $(".select-model").change(function () {
        const modelId = $(this).val();
        const select = $(this);
        $.ajax({
            url: "product_manager",
            method: "GET",
            data: {
                action: "show-model",
                "model-id": modelId,
            },
            dataType: "JSON",
            success: function (data) {
                select.parents(".product").find(".img-product img").attr("src", "../" + data.urlProductImage);
                select.parents(".product").find(".amount-product").text(data.amountProduct);
                select.parents(".product").find(".amount-product-bought").text(data.amountProductBought);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR)
                console.log(textStatus)
                console.log(errorThrown)
            }
        });
    });
}
