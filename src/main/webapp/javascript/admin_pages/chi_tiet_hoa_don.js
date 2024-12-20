$(document).ready(function () {
    displayFormEditContactCustomer();
    saveEdit();

    $("#edit").click(function () {
        displayFormEditContactCustomer();
    });

    cancelBill();

    revertBill();

    $(".user-back").click(function () {
        window.history.back();
    });

    loadProvinces();

    updateStatus();

    verifyBill();

    resign();
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
        };

        $.ajax({
            url: "bill_manager",
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
                $("#update-status").addClass("d-none")
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
            "action": "cancel-bill",
            "bill-id": $(this).attr("bill-id"),
        };

        $.ajax({
            url: "bill_manager",
            data: dataSend,
            dataType: "json",
            method: "POST",
            success: function (data) {
                $.notify("Hủy đơn hàng thành công!", "success");
                $("#bill-action").html(`
                 <div class="in4-right revert-bill">
                    <button type="button" data-bs-toggle="modal"
                            class="bg-success"
                            data-bs-target="#confirm-revert-bill">
                        <i class="fa-solid fa-clock-rotate-left button-show-dialog-cancel-bill"></i>
                        <span> Khôi phục đơn hàng</span>
                    </button>
                </div>
                `)
                renderStatus(data)
                $("#update-status").addClass("d-none")
            },
            error: function (e, x, h) {
                console.error(e.responseText);
                console.error(x);
                console.error(h);
                $.notify("Hủy đơn hàng không thành công!", "error");
            }
        });
    });
}

function revertBill() {
    $("#revert-bill").click(function () {
        const dataSend = {
            "action": "revert-bill",
            "bill-id": $(this).attr("bill-id"),
        };
        $.ajax({
            url: "bill_manager",
            data: dataSend,
            dataType: "json",
            method: "POST",
            success: function (data) {
                $.notify("Khôi phục đơn hàng thành công!", "success");
                $(".show-list-trip .trip").last().remove()
                $("#bill-action").html(`
                 <div class="in4-right cancel-bill">
                    <button type="button" data-bs-toggle="modal"
                            data-bs-target="#confirm-cancel-bill">
                        <i class="fa-solid fa-trash button-show-dialog-cancel-bill"></i> 
                        <span> Hủy đơn hàng</span>
                    </button>
                </div>
                `)
                $("#current-status").text(data['current-status'])
                $("#update-status").removeClass("d-none")
                console.log(data.status)
            },
            error: function (e, x, h) {
                console.error(e.responseText);
                console.error(x);
                console.error(h);
                $.notify("Khôi phục đơn hàng không thành công!", "error");
            }
        });
    });
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

function verifyBill() {
    $("#verify-bill").click(function () {
        const dataSend = {
            "action": "verify-bill",
            "bill-id": $(this).attr("bill-id"),
        };
        $.ajax({
            url: "bill_manager",
            data: dataSend,
            dataType: "json",
            method: "POST",
            success: function (data) {
                const nextStatus = data["next-status"];
                renderStatus(JSON.parse(data.status))
                $.notify("Xác nhận hóa đơn thành công!", "success");
                $("#cancel-verify-bill").click()
                $("#update-status").removeAttr("data-bs-target").attr("data-bs-target", "#model-update-status")
                $("#status").text(nextStatus.status)
                $("#update-bill-status").data("status", nextStatus.name)
            },
            error: function (e, x, h) {
                $.notify("Xác nhận hóa đơn thất bại!", "error");
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

function loadProvinces() {
    $.ajax({
        url: "../address",
        method: "GET",
        dataType: "json",
        data: {
            action: "provinces"
        },
        success: function (data) {
            for (var i = 0; i < data.provinces.length; i++) {
                $("#provinces").find("option").last().before(`<option value="${data.provinces[i].code}">${data.provinces[i].fullName}</option>`);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
        }
    });
}

function displayProvinces() {
    const provinceId = $("#address").attr("province-code");
    $("#provinces").find(`option[value="${provinceId}"`).attr("selected", "selected");
    $("#provinces").change();
}

function displayDistricts() {
    const provinceId = $("#address").attr("province-code"),
        districtsId = $("#address").attr("district-code");
    $.ajax({
        url: "../address",
        data: {
            action: "districts",
            code: provinceId,
        },
        method: "GET",
        dataType: "JSON",
        success: function (data) {
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
    const districtsId = $("#address").attr("district-code"),
        wardsId = $("#address").attr("ward-code");
    $.ajax({
        url: "../address",
        data: {
            action: "wards",
            code: districtsId,
        },
        method: "GET",
        dataType: "JSON",
        success: function (data) {
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

function updateStatus() {
    $("#update-bill-status").click(function (e) {
        const button = $(this);
        const data = {
            "bill-id": button.data("bill-id"),
            status: button.data("status"),
            note: $("#note-status").val(),
            action: "update-bill-status"
        }
        $.ajax({
            url: "bill_manager",
            data: data,
            dataType: "json",
            method: "POST",
            success: function (data) {
                let nextStatus = data["next-status"];
                renderStatus(data.status)
                $.notify("Cập nhật hóa đơn thành công!", "success");
                $("#cancel-update-bill-status").click()
                if (!nextStatus) {
                    $("#update-status").remove()
                    return;
                }
                $("#status").text(nextStatus.status)
                $("#note-status").val("")
                $("#update-bill-status").data("status", nextStatus.name)
            },
            error: function (e, x, h) {
                const response = e.responseJSON;
                if (response)
                    $.notify(response.message, "error");
                else
                    $.notify("Cập nhật hóa đơn thất bại!", "error");
            }
        });
    })
}

function resign() {
    $("#resign").click(function (e) {
        const button = $(this);

        Swal.fire({
            title: 'Yêu cầu ký lại',
            text: "Bạn có chắc muốn yêu cầu người dùng ký lại không!",
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
            cancelButtonText: 'Hủy',
            focusConfirm: false,
        }).then((result) => {
            if (!result.isConfirmed) return;

            $.ajax({
                url: "bill_manager",
                method: "POST",
                data: {
                    "action": "resign",
                    "bill-id": button.data("id")
                },
                dataType: "json",
                success: function (data) {
                    renderStatus(data)
                    notify("Thành công", "success", "Yêu cầu ký lại thành công!")
                    button.addClass("d-none")
                    $("#update-status").addClass("d-none")
                },
                error: function (e) {
                    notify("Thất bại", "error", "Yêu cầu ký lại thất bại!")
                }
            })
        });
    })
}

function notify(title, icon = "success", text = "", callback, time = 1000) {
    Swal.fire({
        icon: icon,
        title: title,
        text: text,
        showConfirmButton: false,
        timer: time
    }).then(rs => {
        if (callback) callback(rs)
    });
}
