class User {
    constructor() {
    }

    init(id, fullname, avatar, email, password, birthday, sex) {
        this.id = id;
        this.fullname = fullname;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
    }

    setId(id) {
        this.id = id;
    }

    setFullName(fullName) {
        this.fullName = fullName;
    }

    setAvatar(avatar) {
        this.avatar = avatar;
    }

    setEmail(email) {
        this.email = email;
    }

    setPassword(password) {
        this.password = password;
    }

    setBirthday(birthday) {
        this.birthday = birthday;
    }

    setSex(sex) {
        this.sex = sex;
    }
}

$(document).ready(function () {
    $("input#search").each(function () {
        displayPlaceholder($(this));
    });

    linkMenuDisplayProduct();
    $("#search").parent().after(`
                    <div id="display-product-search" hidden="" class="border border-top-0 border-secondary">
                        
                    </div>
                `);
    search();

    $(document).on("click", function(e) {
        var target = $(e.target);

        // Kiểm tra nếu phần tử được nhấn không thuộc thẻ div
        if (!target.closest("#menu .search").length) {
            $("#display-product-search").attr("hidden", "true");
            // Thêm mã xử lý tại đây
        }
    });

    $("#search").on("click", function() {
        $("#display-product-search").removeAttr("hidden");
    })
});

function displayPlaceholder(element) {
    if (!element.val()) {
        const string = "Nhập tên sản phẩm!       ";
        let val = "";
        let index = 0;
        setInterval(function (char) {
            val += string.charAt(index);
            element.attr("placeholder", val);
            index++;
            if (index === string.length) {
                index = 0;
                val = "";
                element.attr("placeholder", "");
            }
        }, 150);
    }
}

function displayMenuAccount(user) {
    $("#menu").find(".login").addClass("d-none");
    $("#menu").find(".sign-up").addClass("d-none");
    $("#menu").find(".sign-up").after(`<div idUser="${user.id}" class="account col-lg-3 col-md-2 col-sm-2 border-0 px-lg-0">
        <a href="tai_khoan.jsp">
            <button type="button" class="btn d-flex float-lg-end  me-xl-4 me-lg-2" id="button-account">
                <div class="avatar p-1 bg-white rounded-circle d-flex align-items-center justify-content-center" style="width: 25px; height: 25px">
                    <img src="${user.avatar}" alt="avatar.png"  class="d-md-line rounded-circle" width="18" height="18">
                </div>
                <span class="d-lg-block d-md-none ms-2 overflow-x-hidden d-block" style="max-width: 130px; white-space: nowrap">${user.fullName}</span>
            </button>
        </a>
    </div>`);
}

function hiddenMenuAccount() {
    $("#menu").find(".account").remove();
    $("#menu").find(".login").removeClass("d-none");
    $("#menu").find(".sign-up").removeClass("d-none");
}

function linkMenuDisplayProduct() {
    $("#offcanvasNavbar").html(`
                        <div class="offcanvas-body">
                            <ul class="navbar-nav m-auto">
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="1" href="product-booth?id-category-group=1&id-category=0&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        <span>Kính mát</span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=1&id-category=1&page=1">Kính mát
                                            nam</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=1&id-category=2&page=1">Kính mát
                                            nữ</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=1&id-category=3&page=1">Kính
                                            đi ngày và đêm</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=1&id-category=4&page=1">Kính đổi
                                            màu</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=1&id-category=5&page=1">Kính lọc ánh sáng
                                            xanh</a></li>
                                        <li><a class="dropdown-item"  href="product-booth?id-category-group=1&id-category=6&page=1">Kính Mắt Clip on 2
                                            Lớp</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="2" href="product-booth?id-category-group=2&id-category=0&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded" type="button">
                                        Mắt kính trẻ em
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item"
                                               href="product-booth?id-category-group=2&id-category=7&page=1">Gọng Kính Trẻ Em</a>
                                        </li>
                                        <li><a class="dropdown-item"
                                               href="product-booth?id-category-group=2&id-category=8&page=1">Kính Mát Trẻ Em</a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="3" href="product-booth?id-category-group=3&id-category=0&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        Gọng kính
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=3&id-category=9&page=1">Gọng
                                            kính nữa khung</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=3&id-category=10&page=1">Gọng
                                            kính khoan</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=3&id-category=11&page=1">Gọng
                                            kính tròn</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=3&id-category=12&page=1">Gọng
                                            kính titan</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="4" href="product-booth?id-category-group=4&id-category=0&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        Tròng kính
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item"
                                               href="product-booth?id-category-group=4&id-category=13&page=1">Tròng kính
                                            chống ánh sáng xanh</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=4&id-category=14&page=1">Tròng
                                            kính đổi màu</a></li>
                                        <li><a class="dropdown-item" href="product-booth?id-category-group=4&id-category=15&page=1">Tròng
                                            kính màu</a></li>
                                        <li><a class="dropdown-item"
                                               href="product-booth?id-category-group=4&id-category=16&page=1">Tròng kính cho
                                            gọng khoan</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="0" href="product-booth?id-category-group=0&id-category=0&page=1" class="menu-item nav-link px-4 rounded">Khuyến mãi</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="lien_he.jsp" class="nav-link px-4 rounded">Liên hệ</a>
                                </li>
                            </ul>
                        </div>
        `);

    $("#offcanvasNavbarPolicy").html(`
                        <div class="offcanvas-body">
                            <ul class="navbar-nav m-auto">
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="1" href="../product-booth?id-category-group=1&id-category=0&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        <span>Kính mát</span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=1&id-category=1&page=1">Kính mát
                                            nam</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=1&id-category=2&page=1">Kính mát
                                            nữ</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=1&id-category=3&page=1">Kính
                                            đi ngày và đêm</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=1&id-category=4&page=1">Kính đổi
                                            màu</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=1&id-category=5&page=1">Kính lọc ánh sáng
                                            xanh</a></li>
                                        <li><a class="dropdown-item"  href="../product-booth?id-category-group=1&id-category=6&page=1">Kính Mắt Clip on 2
                                            Lớp</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="2" href="../product-booth?id-category-group=2&id-category=0&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded" type="button">
                                        Mắt kính trẻ em
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item"
                                               href="../product-booth?id-category-group=2&id-category=7&page=1">Gọng Kính Trẻ Em</a>
                                        </li>
                                        <li><a class="dropdown-item"
                                               href="../product-booth?id-category-group=2&id-category=8&page=1">Kính Mát Trẻ Em</a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="3" href="../product-booth?id-category-group=3&id-category=0&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        Gọng kính
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=3&id-category=9&page=1">Gọng
                                            kính nữa khung</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=3&id-category=10&page=1">Gọng
                                            kính khoan</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=3&id-category=11&page=1">Gọng
                                            kính tròn</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=3&id-category=12&page=1">Gọng
                                            kính titan</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="4" href="../product-booth?id-category-group=4&id-category=0&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        Tròng kính
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item"
                                               href="../product-booth?id-category-group=4&id-category=13&page=1">Tròng kính
                                            chống ánh sáng xanh</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=4&id-category=14&page=1">Tròng
                                            kính đổi màu</a></li>
                                        <li><a class="dropdown-item" href="../product-booth?id-category-group=4&id-category=15&page=1">Tròng
                                            kính màu</a></li>
                                        <li><a class="dropdown-item"
                                               href="../product-booth?id-category-group=4&id-category=16&page=1">Tròng kính cho
                                            gọng khoan</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a id-category-group="0" href="../product-booth?id-category-group=0&id-category=0&page=1" class="menu-item nav-link px-4 rounded">Khuyến mãi</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="../lien_he.jsp" class="nav-link px-4 rounded">Liên hệ</a>
                                </li>
                            </ul>
                        </div>
        `);
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

function search() {
    function debounce(func, wait, immediate) {
        let timeout;
        return function () {
            let context = this, args = arguments;
            let later = function () {
                timeout = null;
                if (!immediate) func.apply(context, args);
            };
            var callNow = immediate && !timeout;
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
            if (callNow) func.apply(context, args);
        };
    }


    $("#search").on("input", debounce(async function () {
        const name = $(this).val();
        if (name.replaceAll(" ", "") === "") return;
        $.ajax({
            url: "search",
            dataType: "json",
            data: {
                "name": name,
            },
            method: "GET",
            success: function (data) {
                const products = data.products;
                $("#display-product-search").removeAttr("hidden");
                let htmlProduct = ``;
                products.forEach(function (product) {
                    htmlProduct += `
                    <a href="more_info_product?id=${product.id}">
                        <div class="product-search row mx-0">
                            <div class="col-12 border border-top-0 border-end-0 border-start-0 border-secondary py-2 d-flex">
                                <img src="${product.productImages[0].urlImage}" alt="product.png" class="me-2" style="width: 50px; height: 50px">
                                    <div class="w-100">
                                        <p class="overflow-x-hidden overflow-y-hidden" style="height: 25px">${product.name}</p>
                                        <p class="text-secondary">${product.brandName}</p>
                                    </div>
                            </div>
                        </div>
                    </a>
                    `;
                })

                if (products.length) {
                    $("#display-product-search").html(htmlProduct);
                } else {
                    $("#display-product-search").html(`
                    <div class="col-12 text-center py-2">
                        <p>Không tìm thấy sản phẩm phù hợp!</p>
                    </div>`);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
            }
        });
    }, 300));


}

