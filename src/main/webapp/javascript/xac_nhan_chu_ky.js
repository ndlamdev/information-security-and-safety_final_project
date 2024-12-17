/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:36 AM - 15/12/2024
 *  User: kimin
 **/

$(document).ready(function (e) {
    $("#submit-signature").click(function (e) {
        let signature = $("signature").text();
        if (!signature) {
            $.notify("Vui lòng nhập chữ ký", "error");
            return
        }

        $.ajax({
            url: "bill_history",
            data: {
                signature: signature,
                algorithm: $("#hashAlgorithm").val()
            },
            dataType: "JSON",
            method: "POST",
            success: function () {
                $.notify("Thành công!", "success");
                window.url = "thanh_toan_thanh_cong"
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $.notify("Lỗi chữ ký", "error");
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }
        });
    })

    $("#cancel-bill").click(function () {
        const button = $(this);
        $.ajax({
            url: "bill_detail",
            type: "post",
            datatype: "application/json",
            data: {
                "action": "cancel-bill",
                "bill-id": button.data("id")
            },
            success: function () {
                window.location = button.data("back")
            }
        })
    })
})