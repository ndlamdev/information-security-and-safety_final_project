$(document).ready(function () {
    uploadFile();
    addFile();
    selectAllSlide();
    eventRemoveSlides();
    disableButtonSelectAllSlide();
    $(".slide-management").change(function () {
        disableButtonRemove();
    });
});

function uploadFile() {
    $(".frame-banner .upload-img input").change(function () {
        const bannerId = $(this).attr("id");
        var file = $(this)[0].files[0];
        const formData = new FormData();
        formData.append('action', 'upload-file');
        formData.append(bannerId, file);
        $.ajax({
            url: 'banner_manager',
            data: formData,
            method: 'POST',
            dataType: 'json',
            processData: false,
            contentType: false,
            success: function (data) {
                $(`img[data-banner="${bannerId}"]`).attr("src", "../" + data.url);
                if (bannerId === "banner-logo") {
                    $('.navbar-brand img').attr("src", "../" + data.url);
                }
            },
            error: function () {
                console.log("error uploading file");
            }
        });
    });
}

function addFile() {
    /*Sao t hk tìm thấy input file này. */
    $(".add-img input").change(function () {
        var file = $(this)[0].files[0];
        const formData = new FormData();
        formData.append('action', 'add-file');
        formData.append("path-file", file);
        $.ajax({
            url: 'banner_manager',
            data: formData,
            method: 'POST',
            dataType: 'json',
            processData: false,
            contentType: false,
            success: function (data) {
                let show_slides = $('#show-slides').html();
                let slide = '<div class="slide-management p-3" slide-id = "slide-' + data.slideId + '">\n' +
                    '                        <div class="item-img col">\n' +
                    '                            <img class="img-fluid" data-banner="slide-' + data.slideId + '" src="../' + data.url + '"  alt="">\n' +
                    '                            <div class="check-box-img">\n' +
                    '                                <input class="form-check-input" type="checkbox" id="check-img-' + data.slideId + '">\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                    </div>'
                show_slides += slide;
                $('#show-slides').html(show_slides);
                $(".slide-management").change(function () {
                    disableButtonRemove();
                });
                disableButtonSelectAllSlide();
            },
            error: function () {
                console.log("error");
            }
        })
    })

}

function selectAllSlide() {
    $("#select-all-img").click(function () {
        $('.slide-management input[type=\"checkbox\"]').prop("checked", true);
        disableButtonRemove();
    });
}

function eventRemoveSlides() {
    $('.remove-img').click(function () {
        $('.btn-show-model').click();
    });

    $('#remove-img').click(function () {
        $('.slide-management input[type="checkbox"]:checked').each(function (index) {
            const slideId = $(this).parents('.slide-management').attr('slide-id');
            const filePath = $(this).parents('.slide-management').find('img').attr('src');
            removeSlides(slideId, filePath);
        });
    });
}


/*vô hiệu hóa nút nhấn.*/
function disableButtonRemove() {
    let buttonDelete = $('.remove-img');
    if (!$(`.slide-management`).length || !$(`.slide-management input[type="checkbox"]:checked`).length) {
        buttonDelete.attr('disabled', "true");
    } else {
        buttonDelete.removeAttr('disabled');
    }
}

function disableButtonSelectAllSlide() {
    const buttonSelectAllSlide = $("#select-all-img");
    if ($(`.slide-management`).length) {
        buttonSelectAllSlide.removeAttr("disabled");
    } else {
        buttonSelectAllSlide.attr("disabled", "true");
    }
}

/*ajax xóa 1 hình ảnh có id và đường dẫn hình đó*/
function removeSlides(slideId, filePath) {
    const formData = new FormData();
    formData.append('slideId', slideId);
    formData.append("file-path", filePath);
    $.ajax({
        url: 'banner_manager',
        data: formData,
        method: 'DELETE',
        dataType: "text",
        processData: false,
        contentType: false,
        success: function (data) {
            $('div[slide-id="' + data + '"]').remove();
            disableButtonRemove();
            disableButtonSelectAllSlide();
        },
        error: function () {
            console.log("error");
        }
    });
}


