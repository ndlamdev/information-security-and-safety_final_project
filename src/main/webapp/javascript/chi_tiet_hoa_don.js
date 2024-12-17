$(document).ready(function () {
    displayFormEditContactCustomer();
    saveEdit();

    $(".user-back").click(function () {
        window.history.back();
    });

    $("#edit").click(function () {
        displayFormEditContactCustomer();
    });

    cancelBill();

    signBill();
});

function saveEdit() {
    $("#save").click(function () {
        const dataSend = {
            "action": "save",
            "bill-id": $(this).attr("bill-id"),
            "name": $("#input-name").val(),
            "phone-number": $("#input-phone-number").val(),
            "email": $("#input-email").val(),
            "address": $("#input-address").val(),
            "province-code": $("#provinces").val(),
            "district-code": $("#districts").val(),
            "ward-code": $("#wards").val(),
            "old-signature": $("#input-old-signature").val(),
        };

        $.ajax({
            url: "bill_detail",
            data: dataSend,
            dataType: "json",
            method: "POST",
            success: function (data) {
                if (data.result === 0)
                    return
                const address = $("#address");
                $("#name").text(dataSend.name);
                $("#phone-number").text(dataSend["phone-number"]);
                $("#email").text(dataSend.email);
                address.html(data.addressDetail);
                address.attr("address", dataSend.address);
                address.attr("province-code", dataSend["province-code"]);
                address.attr("district-code", dataSend["district-code"]);
                address.attr("ward-code", dataSend["ward-code"]);
                $.notify("Thay đổi địa chỉ giao hàng thành công!", "success");
                $("#sign-bill").removeClass("d-none")
                renderStatus(data.status)
            }, error: function (e, x, h) {
                console.error(e.responseText);
                console.error(x);
                console.error(h);
                $.notify("Không thể thay đổi địa chỉ giao hàng!", "error");
            }
        });
    });
}

function cancelBill() {
    $("#cancel-bill").click(function () {
        const dataSend = {
            "action": "cancel-bill", "bill-id": $(this).attr("bill-id"),
        };

        $.ajax({
            url: "bill_detail", data: dataSend, dataType: "json", method: "POST", success: function () {
                $.notify("Hủy đơn hàng thành công!", "success");
                $(".button-show-dialog-cancel-bill").remove();
                $("#edit").remove();
            }, error: function (e, x, h) {
                console.error(e.responseText);
                console.error(x);
                console.error(h);
                $.notify("Hủy đơn hàng không thành công!", "error");
            }
        });
    });
}

function displayFormEditContactCustomer() {
    $("#input-name").val($("#name").text());
    $("#input-phone-number").val($("#phone-number").text());
    $("#input-email").val($("#email").text());
    displayProvinces();
    displayDistricts();
    displayWards();
    $("#input-address").val($("#address").attr("address"));
}

function displayProvinces() {
    const provinceId = $("#address").attr("province-code");
    $("#provinces").find(`option[value="${provinceId}"`).attr("selected", "selected");
    $("#provinces").change();
}

function displayDistricts() {
    const provinceId = $("#address").attr("province-code"), districtsId = $("#address").attr("district-code");
    $.ajax({
        url: "address", data: {
            action: "districts", code: provinceId,
        }, method: "GET", dataType: "JSON", success: function (data) {
            for (var i = 0; i < data.districts.length; i++) {
                let htmlOption = `<option `;
                if (districtsId === data.districts[i].code) {
                    htmlOption += `selected="selected"`;
                }
                htmlOption += `value="${data.districts[i].code}">${data.districts[i].fullName}</option>`;
                $("#districts").html($("#districts").html() + htmlOption);
            }
        },
    });
}

function displayWards() {
    const districtsId = $("#address").attr("district-code"), wardsId = $("#address").attr("ward-code");
    $.ajax({
        url: "address", data: {
            action: "wards", code: districtsId,
        }, method: "GET", dataType: "JSON", success: function (data) {
            $("#wards").html("");
            for (var i = 0; i < data.wards.length; i++) {
                let htmlOption = `<option `;
                if (wardsId === data.wards[i].code) {
                    htmlOption += `selected="selected"`;
                }
                htmlOption += `value="${data.wards[i].code}">${data.wards[i].fullName}</option>`;
                $("#wards").html($("#wards").html() + htmlOption);
            }
        }
    });
}

function signBill() {
    $("#submit-signature").click(function (e) {
        $.ajax({
            url: "bill_detail", method: "post", dataType: "json", data: {
                action: "sign-bill",
                "bill-id": $(this).data("id"),
                signature: $("#signature").val(),
                algorithm: $("#hashAlgorithm").val()
            }, success: function (data) {
                $("#hidden-modal-sign").click()
                renderStatus(data)
                $.notify("Ký thành công!", "success");
                $("#sign-bill").addClass("d-none")
            }, error: function (e) {
                console.log(e)
                $.notify("Ký thất bại!", "error");
            }
        })
    })
}

function renderStatus(status) {
    $("#current-status").text(status.status)
    $(".show-list-trip").append(`
                        <div class="trip d-flex justify-content-between">
                            <div class="in4-trip">
                                <div class="icon-trip d-flex justify-content-between">
                                    <span><i class="fa-solid fa-circle"></i></span>
                                    <div class="has-one-line px-2">
                                        <p>
                                            ${status.status}
                                        </p>
                                        <p>
                                            ${status.describe}
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="time-trip">
                                <span>
                                    ${formatData(status.date)}
                                </span>
                            </div>
                        </div>
    `)
}
