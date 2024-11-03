<%@ page import="model.service.CartService" %>
<%@ page import="model.bean.User" %>
<%@ page import="model.bean.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    BannerImage logo = (BannerImage) session.getAttribute("logo");
    BannerImage signupBanner = (BannerImage) session.getAttribute("signupBanner");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="css/dang_nhap_va_dang_ky.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Đăng ký</title>
</head>
<body>
<header id="menu">
    <nav class="navbar navbar-expand-lg pb-0">
        <div class="container-xxl m-md-auto mt-2">
            <div class="row">
                <div class="logo col-lg-2 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                    <a href="index.jsp" class="navbar-brand me-5">
                        <img src="images/logo/logo.png" alt="logo.png">
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
<%User user = (User) request.getAttribute("user");%>
<main class="my-4 main-login">
    <div class="modal-body form-login">
        <div class="row">
            <div class="col-6">
                <div class="tab-pane fade active show" id="modalLogin" role="tabpanel" aria-labelledby="modalLoginTab">
                    <h5 class="text-center mb-2">
                        ĐĂNG KÝ
                    </h5>
                    <div class="login-form-body">
                        <form accept-charset="UTF-8" action="signup_with_google" id="customer_login" method="post">
                            <input name="utf8" type="hidden" value="✓">
                            <div class="form-group mb-3">
                                <label for="signup-fullName">Họ và tên*</label>
                                <input type="text" id="signup-fullName" placeholder="Họ và tên" class="form-control"
                                       name="customer[fullName]" required="">
                            </div>
                            <div class="form-group mb-3">
                                <div class="row pb-1">
                                    <div class="col-5"><label>Giới tính</label></div>
                                    <div class="col p-0"><label>Ngày sinh</label></div>
                                </div>

                                <div class="row">
                                    <div class="col-2 mt-1">
                                        <input type="radio" id="register-gender-0" value="Nữ" name="customer[gender]"
                                               checked="">
                                        <label for="register-gender-0">Nữ</label>
                                    </div>
                                    <div class="col-3 mt-1">
                                        <input type="radio" id="register-gender-1" value="Nam" name="customer[gender]"
                                               data-gtm-form-interact-field-id="0">
                                        <label for="register-gender-1">Nam</label>
                                    </div>

                                    <div class="ngay_sinh col-7 p-0 d-flex">
                                        <input class="col-2  p-1" type="number" name="day" id="day" placeholder="DD">
                                        <select class="col-5 p-1" name="month" id="month">
                                            <option value="1">Tháng 1</option>
                                            <option value="2">Tháng 2</option>
                                            <option value="3">Tháng 3</option>
                                            <option value="4">Tháng 4</option>
                                            <option value="5">Tháng 5</option>
                                            <option value="6">Tháng 6</option>
                                            <option value="7">Tháng 7</option>
                                            <option value="8">Tháng 8</option>
                                            <option value="9">Tháng 9</option>
                                            <option value="10">Tháng 10</option>
                                            <option value="11">Tháng 11</option>
                                            <option value="12">Tháng 12</option>
                                        </select>
                                        <input class="col-4  p-1" type="number" name="year" id="year"
                                               placeholder="YYYY">
                                    </div>
                                    <%String error_birthday = (String) request.getAttribute("signup_error_birthday");%>
                                    <%
                                        if (error_birthday != null) {
                                    %>
                                    <div class="error_birthday text-end">
                                        <small class="text-danger "><%=error_birthday%>
                                        </small>
                                    </div>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                            <div class="form-group mb-3 d-none">
                                <label for="signup-email">Email*</label>
                                <input type="email" id="signup-email" placeholder="Email" class="form-control"
                                       name="customer[email]" value="<%=user.getEmail()%>" required="">
                                <%String error_email = (String) request.getAttribute("signup_error_email");%>
                                <%
                                    if (error_email != null) {
                                %>
                                <small style="color: red"><%=error_email%>
                                </small>
                                <%
                                    }
                                %>
                            </div>
                            <div class="form-group  mb-3">
                                <label for="signup-password">Mật khẩu*</label>
                                <input type="password" id="signup-password" placeholder="Mật khẩu" class="form-control"
                                       name="customer[password]" required="">
                                <%String error_pass = (String) request.getAttribute("signup_error_pass");%>
                                <%
                                    if (error_pass != null) {
                                %>
                                <small style="color: red"><%=error_pass%>
                                </small>
                                <%
                                    }
                                %>
                            </div>
                            <div class="form-group ">
                                <label for="signup-repassword">Nhập mật khẩu*</label>
                                <input type="password" id="signup-repassword" placeholder="Nhập lại mật khẩu"
                                       class="form-control" name="customer[repassword]" required="">
                                <%
                                    if (error_pass != null) {
                                %>
                                <small style="color: red"><%=error_pass%>
                                </small>
                                <%
                                    }
                                %>
                            </div>

                            <div class="form-group mt-2 d-flex-center">
                                <button type="submit" style="font-size: 13px;" class="btn btn-primary hoverOpacity ">
                                    Đăng ký
                                </button>
                            </div>
                            <p style="text-align: center; font-size: 13px; margin-bottom: 2px;" class="mt-2">Hoặc</p>
                            <p style="text-align: center; font-size: 13px;">Bạn đã có tài khoản? <a
                                    style="font-size: 13px; color: blue;" href="dang_nhap.jsp">Đăng nhập</a></p>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-6 banner-account" style="background-image: url('<%=signupBanner.getUrlImage()%>');">
            </div>
        </div>
    </div>
</main>
<hr>
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
                            online</a>
                        </li>
                        <li><a class="hover" href="policy_pages/chinh_sach_thanh_toan_va_giao_nhan.jsp">Chính sách thanh
                            toán,
                            giao nhận</a></li>
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
                    <img src="images/logo/logo.png" alt="logo.png">
                    <span>KIMI</span>
                </a>
            </div>
            <div class="col"><span>© 2023 - Tất cả các quyền thuộc về KIMI</span></div>
        </div>
    </div>
</footer>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/dang_ky.js"></script>
</body>
</html>