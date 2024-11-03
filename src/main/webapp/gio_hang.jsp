<%@ page import="model.bean.User" %>
<%@ page import="model.service.CartService" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="model.bean.ProductCart" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.service.BillService" %>
<%@ page import="model.bean.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    session.setAttribute("bill", new BillService());
    BannerImage logo = (BannerImage) session.getAttribute("logo");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="notify/notify-metro.css"/>
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="css/gio_hang.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <%--jquery--%>
    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <%--notify--%>
    <script src="notify/notify.js"></script>

    <title>Giỏ hàng</title>
</head>
<body>
<header id="menu">
    <nav class="navbar navbar-expand-lg pb-0">
        <div class="container-xxl m-md-auto mt-2">
            <div class="row">
                <div class="logo col-lg-2 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                    <a href="index.jsp" class="navbar-brand me-5">
                        <img src="<%=logo.getUrlImage()%>" alt="logo.png">
                        KIMI
                    </a>
                </div>
                <div class="search col-lg-5 col-md-6 col-sm-6 border-0 px-lg-0 px-md-5">
                    <form class="d-flex input-group">
                        <input class="form-control border-0 ps-3" type="text" name="search" id="search"
                               placeholder="Nhập tên sản phẩm!">
                        <span class="input-group-text  border-0"><a class="nav-link" href="#"><i
                                class="fa-solid fa-magnifying-glass"></i></a></span>
                    </form>
                </div>
                <div class="login col-lg-2 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="dang_nhap.jsp">
                        <button type="button" class="btn d-flex float-lg-end  me-xl-4 me-lg-2">
                            <span class="d-lg-inline d-md-none d-sm-none">Đăng nhập</span>
                            <span class="d-lg-none d-md-line material-symbols-outlined ms-1">login</span>
                        </button>
                    </a>
                </div>
                <div class="sign-up col-lg-1 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="dang_ky.jsp">
                        <button type="button" class="btn d-flex float-lg-none">
                            <span class="d-lg-inline d-md-none  d-sm-none">Đăng ký</span>
                            <span class="d-lg-none d-md-line material-symbols-outlined ms-1">logout</span>
                        </button>
                    </a>
                </div>
                <div class="cart col-lg-2 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="gio_hang.jsp">
                        <button type="button" class="btn d-flex float-lg-none">
                            <span class="d-lg-inline d-md-none  d-sm-none">Giỏ hàng</span>
                            <span class="icon d-flex">
                                <span class="material-symbols-outlined">
                                    shopping_cart
                                </span>
                                 <span id="amount-product" class="amount-product">
                                    <%
                                        CartService cart = (CartService) session.getAttribute("cart");
                                        if (cart == null) cart = new CartService();
                                    %>
                                    <%=cart.getTotalProduct()%>
                                </span>
                            </span>
                        </button>
                    </a>
                </div>
                <div class="menu-product col-lg-12 col-md-1 col-sm-1">
                    <!--Icon 3 dấu gạch mang hiển thị menu-->
                    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas"
                            data-bs-target="#offcanvasNavbar"
                            aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <!--Các mục trong menu-->
                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar"
                         aria-labelledby="offcanvasNavbarLabel">
                        <div class="offcanvas-body">
                            <ul class="navbar-nav m-auto">
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="DisplayProduct?idCategoryGroup=1&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        <span>Kính mát</span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=1&page=1">Kính mát
                                            nam</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=2&page=1">Kính mát
                                            nữ</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=3&page=1">Kính
                                            đi ngày và đêm</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=4&page=1">Kính đổi
                                            màu</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=5&page=1">Kính lọc
                                            ánh sáng
                                            xanh</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=6&page=1">Kính Mắt
                                            Clip on 2
                                            Lớp</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="DisplayProduct?idCategoryGroup=2&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded" type="button">
                                        Mắt kính trẻ em
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item"
                                               href="DisplayProduct?idCategory=7&page=1">Gọng Kính Trẻ Em</a>
                                        </li>
                                        <li><a class="dropdown-item"
                                               href="DisplayProduct?idCategory=8&page=1">Kính Mát Trẻ Em</a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="DisplayProduct?idCategoryGroup=3&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        Gọng kính
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=9&page=1">Gọng
                                            kính nữa khung</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=10&page=1">Gọng
                                            kính khoan</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=11&page=1">Gọng
                                            kính tròn</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=12&page=1">Gọng
                                            kính titan</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="DisplayProduct?idCategoryGroup=4&page=1"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        Tròng kính
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item"
                                               href="DisplayProduct?idCategory=13&page=1">Tròng kính
                                            chống ánh sáng xanh</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=14&page=1">Tròng
                                            kính đổi màu</a></li>
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=15&page=1">Tròng
                                            kính màu</a></li>
                                        <li><a class="dropdown-item"
                                               href="DisplayProduct?idCategory=16&page=1">Tròng kính cho
                                            gọng khoan</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="DisplayProduct?idCategory=0&page=1"
                                       class="menu-item nav-link px-4 rounded">Khuyến mãi</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="lien_he.jsp" class="nav-link px-4 rounded">Liên hệ</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>

<main id="main" class="mt-5 pb-5">
    <div class="container rounded cart">
        <div class="row no-gutters">
            <div class="col-md-8">
                <div class="product-details mr-2">
                    <!--Quay về-->
                    <div class="d-flex flex-row align-items-center">
                        <a href="index.jsp">
                            <i class="fa fa-long-arrow-left"></i>
                            <span class="ms-2">Quay lại trang chủ</span>
                        </a>
                    </div>
                    <hr>
                    <!--Phần header-->
                    <div class="cart-header">
                        <h6 class="mb-0">Giỏ hàng</h6>
                        <div class="d-flex justify-content-between">
                            <span>Bạn đang có <span
                                    class="amount-product"><%=cart.getTotalProduct()%></span> sản phẩm</span>
                        </div>
                    </div>
                    <!--Danh sách sản phẩm-->
                    <div class="list-product cart-body" id="list-product">
                        <%
                            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
                            List<ProductCart> productCartList = cart.getAllProductCart();
                            for (ProductCart productCart : productCartList) {
                        %>
                        <!--Sản phẩm-->
                        <div class="product">
                            <input class="product-checkbox" type="checkbox"
                                   product-id="<%=productCart.getProductId()%>"
                                   model-id="<%=productCart.getModel().getId()%>">
                            <div class="info-product">
                                <img class="rounded" src="<%=productCart.getModel().getUrlImage()%>">
                                <div class="ms-2">
                                    <span class="fw-bold d-block name-product"><%=productCart.getName()%></span>
                                    <span class="option"><%=productCart.getModel().getName()%></span>
                                </div>
                            </div>
                            <div class="change-amount">
                                <button type="button"
                                        product-id="<%=productCart.getProductId()%>"
                                        model-id="<%=productCart.getModel().getId()%>"
                                        class="down">
                                    <span class="material-symbols-outlined">arrow_left</span>
                                </button>
                                <input type="number" name="amount-product-item"
                                       product-id="<%=productCart.getProductId()%>"
                                       model-id="<%=productCart.getModel().getId()%>"
                                       class="amount-product-item" min="1"
                                       max="<%=productCart.getQuantity()%>"
                                       value="<%=productCart.getQuantity()%>" disabled>
                                <button type="button"
                                        product-id="<%=productCart.getProductId()%>"
                                        model-id="<%=productCart.getModel().getId()%>"
                                        class="up">
                                    <span class="material-symbols-outlined">arrow_right</span>
                                </button>
                            </div>
                            <div class="price">
                                    <span class="price">
                                      <%if (productCart.hasDiscount()) {%>
                                      <%=nf.format(productCart.getDiscount())%>
                                      <%} else {%>
                                      <%=nf.format(productCart.getPrice())%>
                                      <%}%>
                                    </span>
                            </div>
                            <div class="total-price">
                                    <span class="total-money">
                                      <%=nf.format(productCart.totalPrice())%>
                                    </span>
                            </div>
                            <button type="button" product-id="<%=productCart.getProductId()%>"
                                    model-id="<%=productCart.getModel().getId()%>" class="cancel text-danger">x
                            </button>
                        </div>
                        <!--Kết thúc sản phẩm-->
                        <%}%>
                    </div>
                </div>
            </div>
            <!--Thanh toán-->
            <div class="col-md-4" id="pay">
                <div class="payment-info">
                    <form accept-charset="UTF-8" action="bill" method="POST" id="form-info-customer">
                        <div class="d-flex justify-content-between align-items-center">
                            <span>Thanh toán</span>
                        </div>
                        <!--Hình thức thanh toán-->
                        <div class="pay-option">
                            <span class="type d-block mt-3 mb-1">Hình thức thanh toán</span>
                            <div class="list-option-pay pb-2">
                                <label class="radio" for="cash">
                                    <input type="radio" name="pay-option" value="cash" id="cash" checked>
                                    <span>
                                    <img src="images/icon/cash.png" alt="cash.png">
                                </span>
                                </label>
                                <label for="transfer" class="radio" data-bs-toggle="modal" data-bs-target="#qr-pay">
                                    <input type="radio" name="pay-option" value="transfer" id="transfer">
                                    <span>
                                    <img src="images/icon/transfer.png" alt="cash.png">
                                </span>
                                </label>
                            </div>
                        </div>
                        <div class="form-info">
                            <div>
                                <label for="full-name">Họ và tên <span class="text-danger">*</span></label>
                                <input required type="text" class="info-payer" placeholder="Họ và tên" name="full-name"
                                       id="full-name">
                            </div>
                            <div>
                                <label for="phone-number">Email <span class="text-danger">*</span></label>
                                <input type="email" class="info-payer" required placeholder="Nhập email" name="email"
                                       id="email">
                            </div>
                            <div>
                                <label for="phone-number">Số điện thoại <span class="text-danger">*</span></label>
                                <input type="tel" class="info-payer" pattern="[0-9]{4}[0-9]{3}[0-9]{3}" required
                                       placeholder="Nhập số điện thoại" name="phone-number" id="phone-number">
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="provinces">Thành phố/Tỉnh <span class="text-danger">*</span></label>
                                    <select class="info-payer" name="provinces" id="provinces" required>
                                        <option selected value="" disabled style="color: #fff">Chọn thành phố/tỉnh
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label for="districts">Quận/Huyện <span class="text-danger">*</span></label>
                                    <select id="districts" name="districts" class="info-payer" required>
                                        <option selected value="" disabled style="color: #fff">Chọn quận/huyện</option>
                                    </select>
                                </div>

                                <div class="col-md-12">
                                    <label for="wards">Phường/Xã <span class="text-danger">*</span></label>
                                    <select class="info-payer" id="wards" name="wards" required>
                                        <option selected value="" disabled style="color: #fff">Chọn phường/xã</option>
                                    </select>
                                </div>
                            </div>
                            <div>
                                <label for="full-address">Địa chỉ cụ thể <span class="text-danger">*</span></label>
                                <input type="text" name="full-address" class="info-payer" required
                                       placeholder="số xx, thôn xx, xã xx, huyện xx, tỉnh xx" id="full-address">
                            </div>
                        </div>
                        <hr class="line">
                        <div class="money">
                            <div class="d-flex justify-content-between information">
                                <span>Tổng hóa đơn</span>
                                <p class="m-0 p-0" id="totalBill">
                                    <%=nf.format(0)%>
                                </p>
                            </div>
                            <div class="d-flex justify-content-between information">
                                <span>Giảm</span>
                                <p class="m-0 p-0" id="totalPriceReduced">
                                    <%=nf.format(0)%>
                                </p>
                            </div>
                            <div class="d-flex justify-content-between information">
                                <span>Phí ship</span>
                                <p class="m-0 p-0" id="shippingFee">
                                    <%=nf.format(0)%>
                                </p>
                            </div>
                            <div class="d-flex justify-content-between information">
                                <span>Tổng trả: </span>
                                <p class="m-0 p-0" id="totalPay">
                                    <%=nf.format(0)%>
                                </p>
                            </div>
                        </div>
                        <button id="button-pay"
                                class="btn btn-primary btn-block d-flex justify-content-center mt-2 w-100"
                                type="submit">
                            <span>Thanh toán<i class="fa fa-long-arrow-right ms-1"></i></span>
                        </button>
                    </form>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="qr-pay" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                 aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">Mã thanh toán</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <img src="images/qr.png" alt="qr.png">
                        </div>
                    </div>
                </div>
            </div>

            <button hidden="" id="button-error-select-product" data-bs-toggle="modal"
                    data-bs-target="#error-select-product"></button>
            <div class="modal fade" id="error-select-product" data-bs-backdrop="static" data-bs-keyboard="false"
                 tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5">Lỗi</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <h3><%=session.getAttribute("title")%>
                            </h3>
                            <p><%=session.getAttribute("message")%>
                            </p>
                        </div>
                        <%if (session.getAttribute("title") != null && ((String) session.getAttribute("title")).contains("đăng nhập")) {%>
                        <div class="modal-footer">
                            <a href="dang_nhap.jsp" style="color:#fff;">
                                <button type="button" class="btn btn-primary">
                                    OK
                                </button>
                            </a>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<footer id="footer" class="footer">
    <div class="container ">
        <div class="footer-top row">
            <div class="footer-top-item col">
                <h5>Mắt Kính KIMI
                </h5>
                <div class="footer-content footer-contact">
                    <div class="ft_map">

                    </div>
                    <ul>
                        <li class="contact-1"><i class="fa-solid fa-map-location-dot px-1"></i><span class="px-1">Khu phố 6, Phường Linh Trung, Quận Thủ Đức, TP. Hồ Chí Minh</span>
                        </li>
                        <li class="contact-2"><i class="fa-solid fa-phone px-1"></i><b><span
                                class="px-1">0855354919</span></b> ( 9:00 - 21:00 )
                        </li>
                        <li class="contact-3"><i class="fa-solid fa-business-time px-1"></i><span class="px-1">9:00 - 20:00 ( Kể cả T7 và CN )</span>
                        </li>
                        <li class="contact-4"><i class="fa-solid fa-envelope px-1"></i><span class="px-1">matkinhkimi@gmail.com</span>
                        </li>
                        <li class="contact-5"><a href="https://www.facebook.com/profile.php?id=100045667640701"><i
                                class="fa-brands fa-facebook-f px-1"></i><span
                                class="px-1 hover"><b>KIMI</b> </span></a></li>
                        <li class="contact-6"><p>Kiểm tra thị lực miễn phí &amp; cắt kính lấy liền.</p></li>
                        <li class="contact-7"><p>Hỗ trợ trả góp lãi suất 0% thẻ tín dụng.</p></li>
                    </ul>
                </div>
            </div>

            <div class="footer-top-item col">
                <h5>HỖ TRỢ KHÁCH HÀNG
                </h5>
                <div class="footer-content footer-contact">
                    <div class="ft_map">

                    </div>
                    <ul>
                        <li><a class="hover" href="policy_pages/huong_dan_mua_hang_online.jsp">Hướng dẫn mua hàng
                            online</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_thanh_toan_va_giao_nhan.jsp">Chính sách thanh
                            toán, giao nhận</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_bao_mat.jsp">Chính sách bảo mật</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_bao_hanh.jsp">Chính sách bảo hành</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_doi_tra_va_hoan_tien.jsp">Chính sách đổi trả
                            và
                            hoàn tiền</a></li>
                        <li><a class="hover" href="policy_pages/kiem_tra_don_hang.jsp">Kiểm tra đơn hàng</a></li>
                    </ul>
                </div>

            </div>
        </div>
        <div class="row footer-bot text-center border-3">
            <div class="logo col-lg-3 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                <a href="index.jsp">
                    <img src="<%=logo.getUrlImage()%>" alt="logo.png">
                    <span>KIMI</span>
                </a>
            </div>
            <div class="col"><span>© 2023 - Tất cả các quyền thuộc về KIMI</span></div>
        </div>
    </div>
</footer>
<script src="javascript/menu_footer.js"></script>
<script src="javascript/gio_hang.js"></script>
<script type="text/javascript">
    <%User user = (User) session.getAttribute("user");
    if(user != null){%>
    const user = new User();
    user.setId(<%=user.getId()%>);
    user.setAvatar("<%=user.getAvatar()%>");
    user.setFullName("<%=user.getFullName()%>");
    displayMenuAccount(user);
    <%} else{%>
    hiddenMenuAccount();
    <%}%>

    <%if(session.getAttribute("title") != null){%>
    $("#button-error-select-product").click();
    <%session.removeAttribute("title");
    session.removeAttribute("message");%>
    <%}%>
</script>
</body>
</html>
