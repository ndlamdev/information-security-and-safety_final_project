<%@ page import="model.bean.User" %>
<%@ page import="model.bean.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% BannerImage logo = (BannerImage) session.getAttribute("logo");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="../css/menu_footer.css">
    <link rel="stylesheet" href="../css/danh_sach_hoa_don.css">
    <link rel="icon" href="../<%=logo.getUrlImage()%>">

    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>

    <title>Quản lý hóa đơn</title>
</head>
<body>
<header id="menu">
    <nav class="navbar navbar-expand-lg pb-0">
        <div class="container-xxl m-md-auto mt-2">
            <div class="row">
                <div class="logo col-lg-2 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                    <a href="quan_ly_tai_khoan.jsp" class="navbar-brand me-5">
                        <img src="../<%=logo.getUrlImage()%>" alt="logo.png">
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
                    <a href="../dang_nhap.jsp">
                        <button type="button" class="btn d-flex float-lg-end  me-xl-4 me-lg-2">
                            <span class="d-lg-inline d-md-none d-sm-none">Đăng nhập</span>
                            <span class="d-lg-none d-md-line material-symbols-outlined ms-1">login</span>
                        </button>
                    </a>
                </div>
                <div class="sign-up col-lg-1 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="../dang_ky.jsp">
                        <button type="button" class="btn d-flex float-lg-none">
                            <span class="d-lg-inline d-md-none  d-sm-none">Đăng ký</span>
                            <span class="d-lg-none d-md-line material-symbols-outlined ms-1">logout</span>
                        </button>
                    </a>
                </div>
                <div class="menu-product col-lg-12 col-md-1 col-sm-1">
                    <!--Icon 3 dấu gạch mang hiển thị menu-->
                    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas"
                            data-bs-target="#offcanvasNavbarAdmin"
                            aria-controls="offcanvasNavbarAdmin" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <!--Các mục trong menu-->
                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbarAdmin"
                         aria-labelledby="offcanvasNavbarAdminLabel">
                        <div class="offcanvas-body">
                            <ul class="navbar-nav m-auto">
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="dashboard.jsp" class="nav-link px-4 rounded">DashBoard</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_tai_khoan.jsp" class="nav-link px-4 rounded">Quản lý tài khoản</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_san_pham.jsp" class="nav-link px-4 rounded">Quản lý sản
                                        phẩm</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_hoa_don.jsp" class="nav-link px-4 rounded active">Quản lý hóa
                                        đơn</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_banner.jsp" class="nav-link px-4 rounded">Quản lý banner</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>

<div id="main" class="mt-5 pb-5">
    <div class="container ">
        <!--        thanh tìm kiếm-->
        <div class="search pb-4">
            <div class="search_header ms-4">
                <span>Tìm kiếm</span>
            </div>

            <div class="row text-center option-search">
                <div class="search-bill col">
                    <div class= "search-item rounded">
                        <input class="ps-2" type="text" name="bill-id" id="bill-id"
                               placeholder="Nhập mã hóa đơn">
                        <button class="d-flex align-items-center p-1 input-search d-inline-block border-0" style="background: none"><span
                                class="material-symbols-outlined ps-1 fs-3">search</span></button>
                    </div>
                </div>
                <div class="search-customer col">
                    <div class="search-item rounded">
                        <input class="ps-2" type="text" name="customer-name" id="customer-name"
                               placeholder="Nhập tên khách hàng">
                        <button for="customer-name" class="d-flex align-items-center p-1 input-search  d-inline-block border-0" style="background: none"><span
                                class="material-symbols-outlined ps-1 fs-3">search</span></button>
                    </div>
                </div>
                <div class="col float-start d-flex align-items-center">
                    <label for="status"></label>
                    <select name="status" id="status" class="ps-1 py-2 rounded w-50 select-search">
                        <option value="">Tất cả</option>
                        <option value="Chờ xác nhận">Chờ xác nhận</option>
                        <option value="Vận chuyển">Vận chuyển</option>
                        <option value="Thành công">Thành công</option>
                        <option value="Đã hủy">Đã hủy</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="show-list-bill p-0">
            <!--            tên cột-->
            <div class="name-col row  align-items-center ps-4">
                <div class="col-2">Mã hóa đơn</div>
                <div class="col-2">Thời gian đặt</div>
                <div class="col-3">Thông tin khách hàng</div>
                <div class="col-2">Trạng thái</div>
                <div class="col-2">Hình thức thanh toán</div>
                <div class="col-1 emty"></div>
            </div>
            <!--            hiện thị danh sách sản phẩm-->
            <div class="list-bill" id="display-bills">
                <!-- bắt đầu một hàng hiện thị sản phẩm-->
                <div class="item-bill row  align-items-center py-md-3 ps-4" value="Nguyễn Đình A">
                    <div class="id-item col-2"><span>#12345</span></div>
                    <div class="time-order col-2"><span>dd/mm/yy, </span><span>hh:mm</span></div>
                    <div class="col-3 d-flex">
                        <div class="info-customer ms-2 w-100">
                            <p class="name-customer">Nguyễn Đình A</p>
                            <p class="email-customer">kiminonawa1305@gmail.com</p>
                        </div>
                    </div>
                    <div class="state col-2">Đang giao</div>
                    <div class="option-pay col-2">Tiền mặt/chuyển khoản</div>
                    <div class="option-edit col-1"><a href="chi_tiet_hoa_don.jsp"><i class="fa-solid fa-eye"></i></a></div>
                </div>
                <!-- kết thúc một hàng hiện thị sản phẩm-->
            </div>
        </div>
        <!--        Tổng tiền và hiển thị nút phân trang-->
        <div class="footer-table row p-4 d-flex align-items-center">
            <div class="text-amount-account col-6">
                <span class="ps-0 pe-0">Tổng số khách hàng: </span>
                <span class="amount ps-0 pe-0">100</span>
                <span class="ps-0 pe-0"> Hóa đơn</span>
            </div>
            <div class="change-page-display-list col-6 d-flex ps-5 justify-content-end">
            </div>
        </div>
    </div>
</div>

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
        </div>
        <div class="row footer-bot text-center border-3">
            <div class="logo col-lg-3 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                <a href="quan_ly_tai_khoan.jsp">
                    <img src="../<%=logo.getUrlImage()%>" alt="logo.png">
                    <span>KIMI</span>
                </a>
            </div>
            <div class="col"><span>© 2023 - Tất cả các quyền thuộc về KIMI</span></div>
        </div>
    </div>
</footer>

<script src="../javascript/menu_footer.js"></script>
<script src="../javascript/admin_pages/admin_page.js"></script>
<script src="../javascript/admin_pages/danh_sach_hoa_don.js"></script>
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