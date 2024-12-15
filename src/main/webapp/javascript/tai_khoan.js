$(document).ready(function () {
    const objectIndex = {
        bill: 1,
        review: 1,
    }
    const button = $(".account-sidebar-menu").find("li>button");
    button.click(function () {
        button.removeClass("active");
        const index = $(this).attr("data-bs-target");
        $(this).addClass("active");
        displayPageContent(index);
    });

    $("#input-avatar").change(function () {
        var selectedFile = this.files[0];
        const imageUrl = window.URL.createObjectURL(selectedFile);
        $("#avatar").attr("src", imageUrl);
    });


    $(`.product-reviews`).click(function () {
        objectIndex.review = 1;
        $('#display-product-reviews').off('scroll');
        $(".main-content .display-product-reviews").html(``);
        showProductReviews(true, objectIndex)
    });

    $(`.menu-review-item`).click(function () {
        $('#display-product-reviews').off('scroll')
        $(`.menu-review-item`).removeClass("active");
        $(this).addClass("active");
        objectIndex.review = 1;
        $(".main-content .display-product-reviews").html(``);
        showProductReviews(true, objectIndex);
    });

    $(`.bill-history`).click(function () {
        objectIndex.bill = 1
        $('#display-bills').off('scroll');
        $(".main-content .display-bills").html(``);
        showBillHistory(true, objectIndex)
    });

    $(`.menu-bill-item`).click(function () {
        objectIndex.bill = 1;
        $('#display-bills').off('scroll')
        $(`.menu-bill-item`).removeClass("active");
        $(this).addClass("active");
        $(".main-content .display-bills").html(``);
        showBillHistory(true, objectIndex);
    });

    $(`#update-key`).click(function () {
        $('.body-page-content>form').attr('action', 'update-key')
        customModal('Cập nhật khóa',
          `<p>Bạn muốn cập nhật khóa không?</p>`)
    })

    $(`#delete-key`).click(function () {
        $('.body-page-content').attr('action', 'delete-key')
        console.log( $('.body-page-content>form').attr('action'))
        customModal('Hủy khóa',
            `
                <p>Vui lòng nhập thời gian lộ khóa.</p><br>
                    <div class="row d-flex">
                      <input type="date" class="mx-1 col-4 border-1 rounded-1 border-primary" id="dateInput">
                      <input type="number" placeholder="HH" aria-label="hour" class="mx-1 col-2"   min="0" max="24" step="1">
                      <input type="number" placeholder="mm" aria-label="second" class="mx-1 col-2 "   min="0" max="60" step="1">
                      <input type="number" placeholder="ss" aria-label="milli" class="mx-1 col-2 rounded-0 " value="00" min="0" max="60" step="1">
                    </div>
            `)
    })

    $('#public-key').on('input', (() => {
        let str = $('#public-key').val()
        if(!str.trim().length) $('#update-key').attr('disabled', true)
        else $('#update-key').attr('disabled', false)
    }))
});

function lazyLoadBillHistory(objectIndex) {
    const indexOld = objectIndex.bill;
    var myDiv = $('#display-bills');
    var totalScrollHeight = myDiv.prop('scrollHeight');

    // Tính tổng chiều cao đã cuộn (scrollTop + clientHeight)
    var scrolledHeight = myDiv.scrollTop() + myDiv.height();

    // Kiểm tra xem người dùng đã cuộn đến cuối chưa
    if (Math.ceil(scrolledHeight) === totalScrollHeight) {
        const indexNew = indexOld + 1;
        objectIndex.bill = indexNew;
        showBillHistory(false, objectIndex);
    }
}

function lazyLoadReviews(objectIndex) {
    const indexOld = objectIndex.review;
    var myDiv = $('#display-product-reviews');
    var totalScrollHeight = myDiv.prop('scrollHeight');

    // Tính tổng chiều cao đã cuộn (scrollTop + clientHeight)
    var scrolledHeight = myDiv.scrollTop() + myDiv.height();

    // Kiểm tra xem người dùng đã cuộn đến cuối chưa
    if (Math.ceil(scrolledHeight) === totalScrollHeight) {
        const indexNew = indexOld + 1;
        objectIndex.review = indexNew;
        showProductReviews(false, objectIndex);
    }
}

function displayPageContent(index) {
    const page = $("div.page-content");
    page.removeClass("active");
    page.eq(index).addClass("active");
}

function showBillHistory(click, objectIndex) {
    const data = {
        "action": `get`,
        "user-id": $("#main").attr("user-id"),
        "menu-item": $(".menu-bill-item.active").attr("data-action"),
        "page": objectIndex.bill,
    };
    $.ajax({
        url: "bill_history",
        data: data,
        dataType: "JSON",
        method: "GET",
        success: function (data) {
            let html = $(".main-content .display-bills").html();
            if (data.bills.length < 8) {
                $('#display-bills').off('scroll');
            }
            data.bills.forEach((bill) => {
                let httmBill = `
                 <div class="body-bill-item row  align-items-center ms-2">
                                 <div class="bill-id col-2"><span>#${bill.id}</span></div>
                                 <div class="bill-time col-2"><span>${bill.statuses[0].date}</span></div>
                                 <div class="col-3 d-flex">
                                     <div class="customer-info ms-2 w-100">
                                         <p class="customer-name">${bill.userName}</p>
                                         <p class="customer-email">${bill.email}</p>
                                     </div>
                                 </div>
                                 <div class="bill-state col-2">${bill.statuses[0].status}</div>
                                 <div class="bill-option-pay col-2">`;
                httmBill += bill.statuses[0].transfer ? `Chuyển khoản` : `Tiền mặt`;
                httmBill += `</div>
                                 <div class="bill-edit col-1">
                                 <a href="bill_history?action=see-detail&bill-id=${bill.id}">
                                    <i class="fa-solid fa-eye"></i>
                                 </a>
                                 </div>
                             </div>
                 `


                html += httmBill;
            });

            $(".main-content .display-bills").html(html);

            if (click) {
                $('#display-bills').off('scroll');
                $('#display-bills').on('scroll', function () {
                    lazyLoadBillHistory(objectIndex);
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function showProductReviews(click, objectIndex) {
    const data = {
        "action": `get-product-reviews`,
        "user-id": $("#main").attr("user-id"),
        "have-evaluated": $(".menu-review-item.active").attr("have-evaluated"),
        "page": objectIndex.review,
    };

    $.ajax({
        url: "review",
        data: data,
        dataType: "JSON",
        method: "GET",
        success: function (data) {
            const userId = data["user-id"];
            let html = $(".main-content .display-product-reviews").html();
            if (data.productReviews.length < 8) {
                $('#display-product-reviews').off('scroll');
            }
            data.productReviews.forEach((productReview) => {
                let htmlProductReview = `
                  <div class="body-product-reviews-item row  align-items-center ms-2">
                                <div class="product-review-id col-2"><span class="id">#${productReview.productId}</span></div>
                                <div class="col-4 d-flex">
                                    <img style="width: 35px; border: 1px solid #000000" class="product-review-image rounded-circle d-block"
                                         src="${productReview.urlImage}"></img>
                                    <div class="customer-info ms-2 w-100">
                                        <p class="product-review-name">${productReview.productName}</p>
                                        <p class="product-review-email">Mẫu: ${productReview.modelName}</p>
                                    </div>
                                </div>
                                <div class="product-review-price col-2"><span>${formatCurrency(productReview.price)}</span></div>
                                <div class="product-review-quantity col-1"><span>${formatNumber(productReview.quantity)}</span></div>
                                <div class="product-review-total-price col-2"><span>${formatCurrency(productReview.quantity * productReview.price)}</span></div>
                                <div class="product-review-edit col-1">
                                    <a href="review?action=${data.action}&user-id=${userId}&product-id=${productReview.productId}&model-id=${productReview.modelId}">
                                        <span class="material-symbols-outlined">rate_review</span>
                                    </a>
                                </div>
                            </div>
                 `


                html += htmlProductReview;
            });

            $(".main-content .display-product-reviews").html(html);
            if (click) {
                $('#display-bills').off('scroll');
                $('#display-product-reviews').on('scroll', function () {
                    lazyLoadReviews(objectIndex);
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function formatCurrency(number) {
    // Sử dụng hàm toLocaleString() để định dạng số
    // và ký hiệu tiền tệ là 'VND' (Việt Nam Đồng)
    return number.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}

function formatNumber(number) {
    // Sử dụng hàm toLocaleString() để định dạng số
    return number.toLocaleString('vi-VN');
}

function uploadProfile() {
    var fullName = document.getElementById('fullname_edit').value;
    var sex = document.getElementById('sex_edit').value;
    var birthday = document.getElementById('birthday_edit').value;
    var avatarInput = document.getElementById('input-avatar');
    if (avatarInput.files.length > 0) {
        var avatarFile = avatarInput.files[0];
    }

    var formData = new FormData();
    formData.append('full_name', fullName);
    formData.append('sex', sex);
    formData.append('birthday', birthday);
    formData.append('avatar', avatarFile);
    $.ajax({
        url: 'api/profile',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function (response) {
            console.log('Upload successful');
            console.log(response);
            Swal.fire({
                title: 'Thành công!',
                text: 'Cập nhật thành công',
                icon: 'susscess',
                confirmButtonText: 'Oke',
                timer: 1500
            }).then(rs => location.reload())
        },
        error: function (error) {
            console.error('Error uploading profile');
            Swal.fire({
                title: 'Thất bại!',
                text: error.responseText,
                icon: 'error',
                confirmButtonText: 'Oke'
            })
        }
    });

}

function changePassword({email}) {
    console.log(email)
    Swal.fire({
        title: 'Đổi mật khẩu',
        html:
            '<input id="password" type="password" class="swal2-input" placeholder="Mật khẩu">' +
            '<input id="confirmPassword" type="password" class="swal2-input" placeholder="Nhập lại mật khẩu">',
        showCancelButton: true,
        confirmButtonText: 'Lưu',
        cancelButtonText: 'Hủy',
        focusConfirm: false,
        preConfirm: () => {
            const password = Swal.getPopup().querySelector('#password').value;
            const confirmPassword = Swal.getPopup().querySelector('#confirmPassword').value;

            // Validate the passwords (you may want to add more validation)
            if (password !== confirmPassword) {
                Swal.showValidationMessage('Mật khẩu không hợp lệ!');
            }
            return {password: password, confirmPassword: confirmPassword};
        }
    }).then(async (result) => {
        if (result.isConfirmed) {
            const password = result.value.password;
            const rePassword = result.value.confirmPassword;
            console.log({
                email: email,
                password: password,
                rePassword: rePassword
            })
            const url = `/mat_kinh_kimi/user/changePassword`;

            try {
                const response = await fetch(url, {
                    method: "POST",
                    mode: "cors",
                    cache: "no-cache",
                    credentials: "same-origin",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        email: email,
                        password: password,
                        rePassword: rePassword
                    }),
                });

                const responseData = await response.json();
                if (response.ok) {
                    Swal.fire({
                        title: 'Thay đổi mật khẩu thành công',
                        confirmButtonText: 'OK'
                    });
                } else {
                    // Handle server response when not OK (e.g., display an error message)
                    Swal.fire({
                        icon: 'error',
                        title: 'Thay đổi mật khẩu thất bại',
                        confirmButtonText: 'OK'
                    });
                }
            } catch (err) {
                Swal.fire({
                    icon: 'error',
                    title: 'Shiba...',
                    text: err,
                    confirmButtonText: 'OK'
                });
            }


        }
    });
}

function customModal(title, content) {
    $('.modal-header>.modal-title').text(title)
    $('.modal-content>.modal-body').html(content)
}