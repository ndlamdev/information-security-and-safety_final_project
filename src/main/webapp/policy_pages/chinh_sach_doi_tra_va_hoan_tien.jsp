<%@ page import="model.bean.User" %>
<%@ page import="model.service.CartService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="../css/menu_footer.css">
    <link rel="icon" href="../images/logo/logo_icon.png">

    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>

    <title>Thế giới mắt kính</title>
</head>
<body>
<header id="menu">
    <nav class="navbar navbar-expand-lg pb-0">
        <div class="container-xxl m-md-auto mt-2">
            <div class="row">
                <div class="logo col-lg-2 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                    <a href="../index.jsp" class="navbar-brand me-5">
                        <img src="../images/logo/logo.png" alt="logo.png">
                        KIMI
                    </a>
                </div>
                <div class="search col-lg-5 col-md-6 col-sm-6 border-0 px-lg-0 px-md-5">
                    <form class="d-flex input-group">
                        <input class="form-control border-0 ps-3" type="text" name="search" id="search"
                               placeholder="Nhập tên sản phẩm!">
                        <p class="input-group-text  border-0"><a class="nav-link" href="#"><i
                                class="fa-solid fa-magnifying-glass"></i></a></p>
                    </form>
                </div>
                <div class="login col-lg-2 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="../dang_nhap.jsp">
                        <button type="button" class="btn d-flex float-lg-end  me-xl-4 me-lg-2">
                            <p class="d-lg-inline d-md-none d-sm-none">Đăng nhập</p>
                            <p class="d-lg-none d-md-line material-symbols-outlined ms-1">login</p>
                        </button>
                    </a>
                </div>
                <div class="sign-up col-lg-1 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="../dang_ky.jsp">
                        <button type="button" class="btn d-flex float-lg-none">
                            <p class="d-lg-inline d-md-none  d-sm-none">Đăng ký</p>
                            <p class="d-lg-none d-md-line material-symbols-outlined ms-1">logout</p>
                        </button>
                    </a>
                </div>
                <div class="cart col-lg-2 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="../gio_hang.jsp">
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
                            data-bs-target="#offcanvasNavbarPolicy"
                            aria-controls="offcanvasNavbarPolicy" aria-label="Toggle navigation">
                        <p class="navbar-toggler-icon"></p>
                    </button>
                    <!--Các mục trong menu-->
                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbarPolicy"
                         aria-labelledby="offcanvasNavbarPolicyLabel">
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
                                        <li><a class="dropdown-item" href="DisplayProduct?idCategory=5&page=1">Kính lọc ánh sáng
                                            xanh</a></li>
                                        <li><a class="dropdown-item"  href="DisplayProduct?idCategory=6&page=1">Kính Mắt Clip on 2
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
                                    <a href="DisplayProduct?idCategory=0&page=1" class="menu-item nav-link px-4 rounded">Khuyến mãi</a>
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
    <div class="container">
        <div class="title mb-4">
            <p class="h1 fw-bold">CHÍNH SÁCH ĐỔI TRẢ VÀ HOÀN TIỀN</p>
            <span>THÔNG TIN CHI TIẾT CHÍNH SÁCH ĐỔI TRẢ VÀ HOÀN TIỀN Mắt Kính Mắt kính Miki</span>
        </div>
        <div class="content mt-5">
            <p>Nhằm giúp quý khách an tâm chọn lựa cho mình một sản phẩm và phục vụ khách hàng chu đáo, Mắt kính Miki
                thông báo đến quý khách hàng CHÍNH SÁCH ĐỔI TRẢ SẢN PHẨM chi tiết sau:</p>
            <div class="mt-3">
                <p class="fw-bold h5">I. ĐIỀU KIỆN ĐỔI - TRẢ</p>
                <p class="ms-4">Không có (luôn luôn chấp nhận Đổi – Trả trong thời gian quy định Đổi – Trả)</p>
            </div>
            <div class="mt-4">
                <p class="fw-bold h5">II. THỜI GIAN ĐỔI - TRẢ</p>
                <div class="mt-2">
                    <p class="fw-bold">2.1/ Mua trực tiếp tại cửa hàng :</p>
                    <p class="ms-4">+ San phẩm đổi/trả trong 30 ngày kể từ ngày mua hàng .</p>
                </div>
                <div class="mt-2">
                    <p class="fw-bold">2.2/ Online :</p>
                    <p class="ms-4">+ Sản phẩm đổi/trả trong 30 ngày (Tính từ ngày khách nhận hàng).</p>
                </div>
            </div>
            <div class=" mt-4">
                <p class="fw-bold h5">III. QUY TRÌNH ĐỔI TRẢ</p>
                <p class="mt-2 fw-bold">3.1/ Cho phép Đổi Trả chéo giữa mua Online và cửa hàng.</p>
                <p class="mt-2 fw-bold">3.2/ Cùng mã sản phẩm (chỉ đổi màu): Đổi miễn phí</p>
                <div class="mt-2">
                    <p class="fw-bold">3.3/ Đổi khác mã sản phẩm: Tính theo giá trị tại thời điểm đổi hàng:</p>
                    <p class="ms-4">- Giá trị SP mới tại thời điểm đổi hàng >Giá trị SP cũ (dựa theo giá trị trên hóa
                        đơn thanh toán): khách hàng sẽ bù thêm tiền phần chênh lệch.</p>
                    <p class="ms-4">Ví dụ: SP mua với giá 250.000d . SP muốn đổi giá trị 300.000d => KH sẽ bù
                        50.000d</p>
                    <br>
                    <p class="ms-4">- Giá trị SP mới tại thời điểm đổi hàng < Giá trị SP cũ (dựa trên giá trị trên hóa
                        đơn thanh toán): Mắt kính Miki sẽ hoàn lại tiền thừa.</p>
                    <p class="ms-4"> Ví dụ: SP mua với giá 250.000d, SP muốn đổi giá trị 200.000d => Mắt kính Miki sẽ
                        hoàn lại 50.000d cho khách hàng</p>
                </div>
                <div class="mt-2">
                    <p class="fw-bold">3.4/ Trường hợp TRẢ sản phẩm:</p>
                    <p class="ms-4">Khách hàng được quyền trả lại sản phẩm và nhận lại 100% số tiền đã thanh toán bằng
                        tiền mặt ngay tại cửa hàng Mắt kính Miki hoặc bằng chuyển khoản nếu khách mua Online.</p>
                    <p class="ms-4"> Lưu ý:</p>
                    <p class="ms-4">- Online: (khách hàng gửi hình ảnh hoặc sản phẩm về cho kho hàng
                        online)</p>
                    <p class="ms-5">+ Sản phẩm do lỗi sản xuất: mọi chi phí vận chuyển trả hàng do Mắt kính
                        Miki chi trả.</p>
                    <p class="ms-5">+ Lý do khác: mọi chí phí vận chuyển do khách hàng chi trả.</p>
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
                        <li><a class="hover" href="huong_dan_mua_hang_online.jsp">Hướng dẫn mua hàng online</a>
                        </li>
                        <li><a class="hover" href="chinh_sach_thanh_toan_va_giao_nhan.jsp">Chính sách thanh toán,
                            giao nhận</a></li>
                        <li><a class="hover" href="chinh_sach_bao_mat.jsp">Chính sách bảo mật</a></li>
                        <li><a class="hover" href="chinh_sach_bao_hanh.jsp">Chính sách bảo hành</a></li>
                        <li><a class="hover" href="chinh_sach_doi_tra_va_hoan_tien.jsp">Chính sách đổi trả và hoàn
                            tiền</a></li>
                        <li><a class="hover" href="kiem_tra_don_hang.jsp">Kiểm tra đơn hàng</a></li>
                    </ul>
                </div>

            </div>
        </div>
        <div class="row footer-bot text-center border-3">
            <div class="logo col-lg-3 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                <a href="../index.jsp">
                    <img src="../images/logo/logo.png" alt="logo.png">
                    <span>KIMI</span>
                </a>
            </div>
            <div class="col"><span>© 2023 - Tất cả các quyền thuộc về KIMI</span></div>
        </div>
    </div>
</footer>

<script src="../javascript/menu_footer.js"></script>
<script src="../javascript/chinh_sach.js"></script>
<script type="text/javascript">
    <%User user = (User) session.getAttribute("user");
    if(user != null){%>
    const user = new User();
    user.setId(<%=user.getId()%>);
    user.setAvatar("../<%=user.getAvatar()%>");
    user.setFullName("<%=user.getFullName()%>");
    displayMenuAccount(user);
    <%} else{%>
    hiddenMenuAccount();
    <%}%>
</script>
</body>
</html>