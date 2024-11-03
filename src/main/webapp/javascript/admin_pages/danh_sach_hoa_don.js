/**find  name customer****/
$(document).ready(function () {
    loadBill(1);

    $(".input-search").click(function () {
        console.log("running");
        loadBill(1);
    });
    $(".select-search").change(function () {
        loadBill(1);
    });
});

function loadBill(page) {
    const id = $("#bill-id").val();
    const name = $("#customer-name").val();
    const status = $("#status").val();

    const data = {
        action: "get",
        id: id,
        name: name,
        status: status,
        page: page
    }

    $.ajax({
        url: "bill_manager",
        data: data,
        dataType: "json",
        method: "GET",
        success: function (data) {
            renderBill(data.bills);
            renderPagination(page, data.pages);
            $(".amount").text(data.total)
        },
        error: function (e, t, h) {
            console.error(e);
        }
    });
}

function renderBill(bills) {
    let html = ``;
    bills.forEach(function (bill) {
        html += `
                <div class="item-bill row  align-items-center py-md-3 ps-4">
                    <div class="id-item col-2"><span>#${bill.billId}</span></div>
                    <div class="time-order col-2">${formatData(bill.date)}</div>
                    <div class="col-3 d-flex">
                        <div class="info-customer ms-2 w-100">
                            <p class="name-customer">${bill.name}</p>
                            <p class="email-customer">${bill.email}</p>
                        </div>
                    </div>
                    <div class="state col-2">${bill.status}</div>
                    <div class="option-pay col-2">${bill.transfer == 1 ? "Chuyển khoản" : "Tiền mặt"}</div>
                    <div class="option-edit col-1">
                        <a href="bill_manager?action=see-detail&bill-id=${bill.billId}">
                            <i class="fa-solid fa-eye"></i>
                        </a>
                    </div>
                </div>
        `;
    });

    $("#display-bills").html(html);
}

function renderPagination(currentPage, totalPage) {
    let html = ``;
    let indexPage = 1;

    if (totalPage != 1) {
        if (currentPage != 1) {
            html += `
            <button class="d-flex align-items-center justify-content-center button-page" page="${1}">
                <span class="material-symbols-outlined">keyboard_double_arrow_left</span>
            </button>
            <button id="prev" class="d-flex align-items-center justify-content-center button-page" page="${(currentPage - 1)}">
                <span class="material-symbols-outlined">chevron_left</span>
            </button>
            `;
        }
        for (indexPage = currentPage - 2; indexPage < currentPage; indexPage++) {
            if (indexPage > 0) {
                html += `
                <button class="d-flex align-items-center justify-content-center  button-page button-number" page="${indexPage}"
                        data-target="${indexPage}">${indexPage}
                </button>
               `;
            }
        }
        for (indexPage = currentPage; indexPage <= totalPage && (indexPage - currentPage) < (4 - ((currentPage - 3) > 0 ? 0 : (currentPage - 3))); indexPage++) {
            html += `
                <button class="d-flex align-items-center justify-content-center  button-page button-number ${currentPage == indexPage ? "active" : ""}"
                    data-target="${indexPage}"  page="${indexPage}">
                    ${indexPage}
                </button>
               `;
        }
        if (totalPage != 0 && currentPage != totalPage) {
            html += `
                <button id="next" class="d-flex align-items-center justify-content-center button-page"  page="${(currentPage + 1)}">
                    <span class="material-symbols-outlined">chevron_right</span>
                </button>
                <button class="d-flex align-items-center justify-content-center button-page"  page="${totalPage}">
                    <span class="material-symbols-outlined">keyboard_double_arrow_right</span>
                </button>
            `;
        }
    }

    $(".change-page-display-list").html(html);
    $(".button-page").click(function () {
        const page = $(this).attr("page");
        loadBill(page);
    });
}

function formatData(dateString) {

// Chuyển đổi thành đối tượng Date
    const date = new Date(dateString);

// Lấy thông tin ngày, tháng, năm, giờ, phút, giây
    const day = date.getDate();
    const month = date.getMonth() + 1; // Tháng bắt đầu từ 0, nên cộng thêm 1
    const year = date.getFullYear();
    const hours = date.getHours();
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();

    // Hàm để thêm số 0 trước các số từ 1 đến 9
    const addLeadingZero = (number) => (number < 10 ? "0" : "") + number;

    // Định dạng ngày tháng theo "dd/mm/yyyy hh:mm:ss"
    const formattedDate =
        `${addLeadingZero(day)}/${addLeadingZero(month)}/${year} ` +
        `${addLeadingZero(hours)}:${addLeadingZero(minutes)}:${addLeadingZero(seconds)}`;

    return formattedDate;
}
