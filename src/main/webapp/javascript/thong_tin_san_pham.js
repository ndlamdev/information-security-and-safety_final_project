class Review {
    constructor(avatar, name, numberOfStar, comment, listImg, date) {
        this.avatar = avatar;
        this.name = name;
        this.numberOfStar = numberOfStar;
        this.comment = comment;
        this.listImg = listImg;
        this.date = date;
    }
}

$(document).ready(function () {
    showMoreInfor();
    selectModel();
    buyProductNow();
    addProductCartNow();
});

/******Nút xổ thêm thông tin mô tả của sản phẩm******/
function showMoreInfor() {
    $("#main .productTabsContent .tab-content .tab-pane a.readmore div").click(function () {
        $(this).parent().addClass("d-none");
        $("#main .productTabsContent .tab-content .tab-pane a.readless").removeClass("d-none");
        $("#main .productTabsContent .tab-content .tab-pane>div").removeClass("overflow-hidden").attr("style", "");
    });

    $("#main .productTabsContent .tab-content .tab-pane a.readless  div").click(function () {
        $(this).parent().addClass("d-none");
        $("#main .productTabsContent .tab-content .tab-pane a.readmore").removeClass("d-none");
        $("#main .productTabsContent .tab-content .tab-pane>div").addClass("overflow-hidden").attr("style", "max-height: 500px;");
    });
}

/******Lựa chọn mẫu******/
function selectModel() {
    $(".button-model").click(function () {
        $(".button-model").removeClass("active");
        $(this).addClass("active");
        $("#product-quantity").val(1);
    });
}

function addProductCartNow(){
    $("#add-product-cart").click(function () {
        const productId = $(this).attr("product-id");
        const modelId = $("button.button-model.active").attr("model-id");
        const quantity = $("#product-quantity").val();
        $.ajax({
            url: 'cart',
            data: {
                "action": "add",
                "product-id": productId,
                "model-id": modelId,
                "quantity": quantity,
                "checked": "false"
            },
            method: 'POST',
            dataType: 'json',
            success: function (data) {
                $("#amount-product").text(data.amountProduct);
                $("#product-quantity").val(1);
                $.notify("Thêm sản phẩm thành công!", "success");
            },
            error: function () {
                $.notify("Sản phẩm đã hết!", "error");
            }
        });
    });
}

/******Mua ngay******/
function buyProductNow() {
    $("#buy-product-now").click(function () {
        const productId = $(this).attr("product-id");
        const modelId = $("button.button-model.active").attr("model-id");
        const quantity = $("#product-quantity").val();
        const formBuyNow =
            `<form id="form-buy-now" hidden="" method="POST" action="buy_now">
            <input type="text" name="product-id" value="${productId}">
            <input type="text" name="action" value="buy-now">
            <input type="text" name="model-id" value="${modelId}">
            <input type="number" name="quantity" value="${quantity}">
            <input type="submit" id="submit-buy-now"> 
        </form>`;
        $("body").append(formBuyNow);
        $("#submit-buy-now").click();
        $("#form-buy-now").remove();
    });
}