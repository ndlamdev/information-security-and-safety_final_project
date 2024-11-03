$(document).ready(function () {
    selectProvinces()
    selectProvince();
    selectDistrict();
    upValueInputNumber();
    downValueInputNumber();
    checkProduct();
    removeProduct();
})

function selectProvinces(){
    $.ajax({
        url: "address",
        method: "GET",
        dataType: "json",
        data: {
            action: "provinces"
        },
        success: function (data) {
            for (var i = 0; i < data.provinces.length; i++) {
                $("#provinces").find("option").last().before(`<option value="${data.provinces[i].code}">${data.provinces[i].fullName}</option>`);
            }
        }
    });
}

function selectProvince() {
    $("#provinces").change(function (e) {
        const code = e.target.value;
        $.ajax({
            url: "address",
            data: {
                action: "districts",
                code: code,
            },
            method: "GET",
            dataType: "JSON",
            success: function (data) {
                $("#districts").html(` <option selected value="" disabled style="color: #fff">Chọn quận/huyện</option>`);
                $("#wards").html('<option selected value="" disabled style="color: #fff">Chọn phường/xã</option>');
                for (var i = 0; i < data.districts.length; i++) {
                    $("#districts").find("option").last().before(`<option value="${data.districts[i].code}">${data.districts[i].fullName}</option>`);
                }
            }
        });
    });
}

function selectDistrict() {
    $("#districts").change(function (e) {
        const code = e.target.value;
        $.ajax({
            url: "address",
            data: {
                action: "wards",
                code: code,
            },
            method: "GET",
            dataType: "JSON",
            success: function (data) {
                $("#wards").html('<option selected value="" disabled style="color: #fff">Chọn phường/xã</option>');
                for (var i = 0; i < data.wards.length; i++) {
                    $("#wards").find("option").last().before(`<option value="${data.wards[i].code}">${data.wards[i].fullName}</option>`);
                }
            }
        });
    });
}

function upValueInputNumber() {
    $(".product .change-amount button.up").click(function () {
        const product = $(this).parent().parent();
        const input = $(this).prev();
        $.ajax({
            url: "buy_now",
            data: {
                action: "increase",
                productId: $(this).attr("product-id"),
                modelId: $(this).attr("model-id"),
            },
            method: "POST",
            dataType: "json",
            success: function (data) {
                product.find(".total-money").text(data.totalPriceProduct);
                input.val(data.quantity);
                setUpMoney(data);
            },
            error: function (xhr, status, error) {
                console.log(xhr);
                console.log(status);
                console.log(error);
            }
        });
    });
}

function downValueInputNumber() {
    $(".product .change-amount button.down").click(function () {
        const product = $(this).parent().parent();
        const input = $(this).next();
        $.ajax({
            url: "buy_now",
            data: {
                action: "reduce",
                productId: $(this).attr("product-id"),
                modelId: $(this).attr("model-id"),
            },
            method: "POST",
            dataType: "json",
            success: function (data) {
                product.find(".total-money").text(data.totalPriceProduct);
                input.val(data.quantity);
                setUpMoney(data);
            },
            error: function (xhr, status, error) {
                console.log(xhr);
                console.log(status);
                console.log(error);
            }
        });
    });
}

function setUpMoney(data) {
    $("#totalBill").text(data.totalBill);
    $("#totalPriceReduced").text(data.totalPriceReduced);
    $("#shippingFee").text(data.shippingFee);
    $("#totalPay").text(data.totalPay);
}