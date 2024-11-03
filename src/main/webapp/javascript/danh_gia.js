$(document).ready(function () {
    $(".account").find("a").attr("href", "tai_khoan.jsp");
    uploadImageReview();

    setStar();

    $("#back").click(function () {
        window.history.back();
    });

    $("#remove-review").click(function () {
        const reviewId = $("#main").attr("review-id");
        const data = {
            "action": "remove-review",
            "review-id": reviewId,
        }
        $.ajax({
            url: "review",
            dataType: "text",
            data: data,
            method: "POST",
            success: function () {
                window.history.back();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error(jqXHR);
                console.error(textStatus);
                console.error(errorThrown);
            }
        });
    });

    $("#label-upload-image").parent().find(".cancel").click(function () {
        removeReviewImage($(this).attr("review-image-id"), $(this).prev().attr("src"), $(this).parent());
    });
});

function uploadImageReview() {
    $("#input-img-review").change(function () {
        const file = document.getElementById("input-img-review").files[0];
        const label = $("#label-upload-image");

        const formData = new FormData();
        formData.append("input-img-review", file);
        formData.append("action", "add-image");
        formData.append("review-id", $(`#main`).attr(`review-id`));
        $.ajax({
            url: "review",
            data: formData,
            method: "POST",
            dataType: "JSON",
            contentType: false, // Thiết lập contentType là false để không xử lý dữ liệu tự động
            processData: false, // Thiết lập processData là false để không xử lý dữ liệu tự động
            success: function (data) {
                label.before(`
                            <div class="a-img-review-product">
                               <img src="${data.path}" alt="img-review">
                               <button review-image-id="${data.id}" type="button" class="text-danger cancel">x</button>
                            </div>
                        `);

                label.prev().find(".cancel").click(function () {
                    removeReviewImage($(this).attr("review-image-id"), $(this).prev().attr("src"), $(this).parent());
                });

                const amountImg = $("#input-img-review").parent().find("div.a-img-review-product").length;
                if (amountImg === 5) {
                    label.addClass("d-none");
                }
            },
            error: function () {
                console.log("error");
            }
        })
    });
}

function setStar() {
    const listStar = $("#input-star>ul>li>i");
    const textQuality = ["Tệ", "Không hài lòng", "Bình thường", "Hài lòng", "Tuyệt vời"];

    listStar.click(function () {
        listStar.attr("class", "fa-regular fa-star");
        listStar.parent().removeClass("checked");

        const amountStar = $(this).attr("star");
        for (var i = 0; i < amountStar; i++) {
            listStar.eq(i).attr("class", "fa-solid fa-star");
            listStar.eq(i).parent().addClass("checked");
        }

        $("#text-quality-product").text(textQuality[amountStar - 1]);
        $("#star").val(amountStar);
    });
}

function removeReviewImage(id, path, element) {
    const data = {
        "action": "remove-review-image",
        "review-image-id": id,
        "path": path
    }

    $.ajax({
        url: "review",
        data: data,
        method: "POST",
        dataType: "text",
        success: function (data) {
            element.remove();

            const amountImg = $("#input-img-review").parent().find("div.a-img-review-product").length;
            if (amountImg !== 5) {
                $("#label-upload-image").removeClass("d-none");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error(jqXHR.responseText);
            console.error(textStatus);
            console.error(errorThrown);
        }
    });
}