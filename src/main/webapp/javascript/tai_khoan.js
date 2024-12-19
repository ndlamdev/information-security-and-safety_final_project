$(document).ready(function () {
    const objectIndex = {
        bill: 1,
        review: 1,
    }
    const button = $(".account-sidebar-menu").find("li button");
    button.click(function () {
        button.removeClass("active");
        const index = $(this).attr("data-bs-target");
        $(this).addClass("active");
        displayPageContent(index);
    });

    $("#input-avatar").change(function () {
        let selectedFile = this.files[0];
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
        Swal.fire({
            title: "Cập nhật khóa",
            text: "Bạn có chắc chắn muốn cập nhật khóa không?",
            icon: "info",
            showCancelButton: true,
            confirmButtonText: 'Ok',
        }).then(() => {
            // Create FormData and append the selected file
            let formData = new FormData();
            let fileInput = $('#public-key')[0].files[0];
            formData.append("action", "upload-key");
            formData.append("publicKeyFile", fileInput);
            $.ajax({
                url: "public-key",
                data: formData,
                contentType: false,
                processData: false,
                method: "POST",
                dataType: "JSON",
                success: (data) => {
                    console.log(data)
                    const message = data.message;
                    notify("Thành công!", "success", message);
                    $('.workspace-key').trigger('click');
                },
                error: (jqXHR, textStatus, errorThrown) => {
                    if (jqXHR.responseJSON)
                        notify("Thất bại!", "error", jqXHR.responseJSON.message, null, 3000);
                    else notify("Thất bại!", "error");
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            })
        })
    })

    $(`#delete-key`).click(function () {
        $.ajax({
            url: "public-key",
            data: {"action": "send-otp-delete-key"},
            dataType: "JSON",
            method: "POST",
            success: function (data) {
                notifyCancelKey(data.time)
            },
            error: function (data) {
                notify("Lỗi xóa khóa!", "error", data.responseJSON.message);
            }
        })
    })

    $('#public-key').on('input', (() => {
        let str = $('#public-key').val()
        if (!str.trim().length) $('#update-key').attr('disabled', true)
        else $('#update-key').attr('disabled', false)
    }))

    $('.workspace-key').click(() => {
        $.ajax({
            url: "public-key",
            data: {'action': 'exists-key'},
            dataType: "JSON",
            method: "GET",
            success: (data) => {
                if (data.existsKey) {
                    $('#status-key').html(`<i class="has-key fa-solid fa-check text-success"></i>`)
                    $('.delete-key').attr('disabled', false)
                } else {
                    $('#status-key').html(`<i class="non-key fa-solid fa-x text-danger">`)
                    $('.delete-key').attr('disabled', true)
                }
            },
            error: (jqXHR, textStatus, errorThrown) => {
                notify("Thất bại!", "success", error.responseText);
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }
        })
    })

    $(`button[name=delete-bills]`).click(() => {
        // Get all checked checkboxes
        const checkedItems = $("input[type=checkbox]:checked");

        // Array to store the id order of checked checkboxes
        const values = [];

        // Loop through each checked checkbox
        checkedItems.each(function () {
            values.push($(this).attr("data-index-id"));
        });

        if (!values.length) notify("Vui lòng chọn đơn hàng muốn hủy!", "error");

        $.ajax({
            url: "set-bills-status-cancel",
            data: {
                "billIds": `${values}`
            },
            dataType: "JSON",
            method: "POST",
            success: (data) => {
                if (data.result) {
                    notify("Thành công!", "success");
                    $(`button[data-bs-dismiss=modal]`).click()
                } else notify("Thất bại!", "error");
            },
            error: (jqXHR, textStatus, errorThrown) => {
                notify("Thất bại!", "error");
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }
        })
    })

    $(`button[name="display-history-bought"]`).click(() => {
        $(`a[href="#histories"] > button`).click()
    })

    const hash = window.location.hash;
    if (hash)
        $(`a[href="${hash}"] > button`).click()
});

function lazyLoadBillHistory(objectIndex) {
    const indexOld = objectIndex.bill;
    let myDiv = $('#display-bills');
    let totalScrollHeight = myDiv.prop('scrollHeight');

    // Tính tổng chiều cao đã cuộn (scrollTop + clientHeight)
    let scrolledHeight = myDiv.scrollTop() + myDiv.height();

    // Kiểm tra xem người dùng đã cuộn đến cuối chưa
    if (Math.ceil(scrolledHeight) === totalScrollHeight) {
        objectIndex.bill = indexOld + 1;
        showBillHistory(false, objectIndex);
    }
}

function lazyLoadReviews(objectIndex) {
    const indexOld = objectIndex.review;
    let myDiv = $('#display-product-reviews');
    let totalScrollHeight = myDiv.prop('scrollHeight');

    // Tính tổng chiều cao đã cuộn (scrollTop + clientHeight)
    let scrolledHeight = myDiv.scrollTop() + myDiv.height();

    // Kiểm tra xem người dùng đã cuộn đến cuối chưa
    if (Math.ceil(scrolledHeight) === totalScrollHeight) {
        objectIndex.review = indexOld + 1;
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
            const frame = $(".main-content .display-bills");
            let html = frame.html();
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

            frame.html(html);

            if (click) {
                const bill = $('#display-bills');
                bill.off('scroll');
                bill.on('scroll', function () {
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
            const frame = $(".main-content .display-product-reviews");
            let html = frame.html();
            if (data.productReviews.length < 8) {
                $('#display-product-reviews').off('scroll');
            }
            data.productReviews.forEach((productReview) => {
                let htmlProductReview = `
                  <div class="body-product-reviews-item row  align-items-center ms-2">
                                <div class="product-review-id col-2"><span class="id">#${productReview.productId}</span></div>
                                <div class="col-4 d-flex">
                                    <img alt="" style="width: 35px; border: 1px solid #000000" class="product-review-image rounded-circle d-block"
                                         src="${productReview.urlImage}" />
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

            frame.html(html);
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
    let fullName = document.getElementById('fullname_edit').value;
    let sex = document.getElementById('sex_edit').value;
    let birthday = document.getElementById('birthday_edit').value;
    let avatarInput = document.getElementById('input-avatar');
    let avatarFile
    if (avatarInput.files.length > 0) {
        avatarFile = avatarInput.files[0];
    }

    let formData = new FormData();
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
            notify('Thành công!', "success", 'Cập nhật thành công', () => location.reload());
        },
        error: function (error) {
            console.error('Error uploading profile');
            notify('Thất bại!', 'error', error.responseText)
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

function insertDataIntoTable(data) {
    $(`button[name=showModal]`).click()
    const $tableBody = $("#show-bills-will-delete tbody");
    $tableBody.empty();
    $.each(data, function (index, item) {
        const formattedDate = new Date(item.date).toLocaleString();
        let row = `
                        <tr>
                            <th scope="row">${item.id}</th>
                            <td>${formattedDate}</td>
                            <td>${item.status}</td>
                            <td><input type="checkbox" data-index-id="${item.id}" /></td>
                        </tr>
                    `;
        if (!data.length) row = `<tr><td colspan="4">Không có dữ liệu</td></tr>`
        $tableBody.append(row);
    });
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

function notifyCancelKey(sendMailAt) {
    const date = new Date(Date.parse(sendMailAt))
    const now = Date.now();
    let time = (5 * 60 * 1000 - (now - date)) / 1000
    let id = 0
    Swal.fire({
        title: "Hủy khóa",
        input: 'text',
        inputLabel: 'Kiểm tra email của bạn.',
        inputPlaceholder: 'Nhập mã xác thực',
        icon: "question",
        showCancelButton: true,
        showDenyButton: true,
        denyButtonText: 'Gửi lại mã',
        confirmButtonText: 'OK',
        preConfirm: (value) => {
            if (!value) Swal.showValidationMessage("Bạn chưa nhập dữ liệu!");
            else {
                $.ajax({
                    url: "public-key",
                    data: {
                        "action": "lock-key",
                        "verifyCode": value,
                    },
                    dataType: "JSON",
                    method: "POST",
                    success: (data) => {
                        if (data.lockKey) {
                            notify("Thành công!", "success");
                            $('.workspace-key').trigger('click');
                            $.ajax({
                                url: "bill-will-delete",
                                method: "GET",
                                success: (data) => {
                                    insertDataIntoTable(data.BillsWillDelete)
                                },
                                error: (jqXHR, textStatus, errorThrown) => {
                                    console.log(jqXHR);
                                    console.log(textStatus);
                                    console.log(errorThrown);
                                }
                            })

                        } else {
                            notify("Thất bại!", "error");
                        }
                    },
                    error: (jqXHR, textStatus, errorThrown) => {
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                        notify("Thất bại!", "error");
                    }
                })
            }
            return value
        },
        preDeny: () => {
            if (id) clearInterval(id)
            id = setInterval(() => {
                if (time > 0) {
                    Swal.showValidationMessage("Vui lòng kiểm tra email của bạn!");
                    clearInterval(id)
                    return;
                }

                clearInterval(id)
                $.ajax({
                    url: "public-key",
                    data: {
                        "action": "send-otp-delete-key",
                    },
                    dataType: "JSON",
                    method: "POST",
                    success: function () {
                        $.notify("Gửi lại mã thành công!", "success");
                    },
                    error: function (data) {
                        $.notify("Gửi mail thất bại", "error");
                    }
                })

                time = time - 1;
            }, 1000)
            return false;
        },
    })
}