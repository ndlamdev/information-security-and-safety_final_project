<%@ page import="model.bean.User" %>
<%@ page import="model.bean.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.bean.Model" %>
<%@ page import="model.bean.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) session.getAttribute("logo");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="../notify/notify-metro.css"/>
    <link rel="stylesheet" href="../css/menu_footer.css">
    <link rel="stylesheet" href="../css/admin_pages.css">
    <link rel="stylesheet" href="../css/quan_ly_san_pham.css">
    <link rel="icon" href="../<%=logo.getUrlImage()%>">

    <%--jquery--%>
    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>

    <%--notify--%>
    <script src="../notify/notify.js"></script>
    <title>Quản lý sản phẩm</title>
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
                                <li class="nav-item dropdown pe-lg-5 pe-md-0 ">
                                    <a href="quan_ly_tai_khoan.jsp" class="nav-link px-4 rounded">Quản lý tài khoản</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0 ">
                                    <a href="quan_ly_san_pham.jsp" class="nav-link px-4 rounded active">Quản lý sản
                                        phẩm</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_hoa_don.jsp" class="nav-link px-4 rounded">Quản lý hóa đơn</a>
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

<%String requestString = (String) request.getAttribute("request");%>
<main id="main" class=" mt-5 pb-5">
    <div class="container">
        <div class="display-product">
            <div class="filter pb-4">
                <div class="filter_header ms-4">
                    <span>Bộ lọc</span>
                </div>
                <div class="filter-body row">
                    <div class="option-filter col-4">
                        <div class="filter-item">
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button"
                                        data-bs-toggle="dropdown" aria-expanded="false" id="button-status">
                                    <%=request.getAttribute("available-string")%>
                                </button>
                                <ul class="dropdown-menu">
                                    <div class="dropdown-title text-secondary">
                                        ---------------Trạng thái---------------
                                    </div>
                                    <li><a class="dropdown-item" href="<%=requestString%>&available=0&page=1">Tất cả</a></li>
                                    <li><a class="dropdown-item" href="<%=requestString%>&available=1&page=1">Còn hàng</a></li>
                                    <li><a class="dropdown-item" href="<%=requestString%>&available=-1&page=1">Hết hàng</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="option-filter col-4">
                        <div class="filter-item">
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button"
                                        data-bs-toggle="dropdown" aria-expanded="false" id="button-brand-name">
                                    <%=request.getAttribute("brand-name-string")%>
                                </button>
                                <ul class="dropdown-menu" id="list-brand-name">
                                    <div class="dropdown-title text-secondary">
                                        ---------------Thương hiệu---------------
                                    </div>
                                    <li><a class="dropdown-item"
                                           href="<%=response.encodeURL(requestString + "&brand-name=")%>">Tất cả</a>
                                    </li>
                                    <%
                                        List<String> brandName = (List<String>) request.getAttribute("list-brand-name");
                                        for (String brand : brandName) {
                                    %>
                                    <li>
                                        <a class="dropdown-item"
                                           href="<%=response.encodeURL(requestString + "&brand-name=" + brand + "&page=1")%>"><%=brand%>
                                        </a>
                                    </li>
                                    <%}%>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="option-filter col-4">
                        <div class="filter-item">
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button"
                                        data-bs-toggle="dropdown" aria-expanded="false" id="button-category">
                                    <%=request.getAttribute("category")%>
                                </button>
                                <ul class="dropdown-menu">
                                    <div class="dropdown-title text-secondary">---------------Danh mục---------------
                                    </div>
                                    <li><a class="dropdown-item"
                                           href="<%=requestString%>&category-group-id=-1&category-id=-1&page=1">Tất
                                        cả</a></li>
                                    <li><a class="dropdown-item"
                                           href="<%=requestString%>&category-group-id=1&category-id=-1&page=1">Kính
                                        mát</a></li>
                                    <li>
                                        <ul>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=1&page=1">Kính
                                                mát nam</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=2&page=1">Kính
                                                mát nữ</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=3&page=1">Kính
                                                đi ngày và đêm</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=4">Kính
                                                đổi màu</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=5&page=1">Kính
                                                lọc ánh sáng xanh</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=6&page=1">Kính
                                                mắt clip on 2 lớp</a></li>
                                        </ul>
                                    </li>
                                    <li><a class="dropdown-item"
                                           href="<%=requestString%>&category-group-id=2&category-id=-1&page=1">Mắt
                                        kính trẻ em</a></li>
                                    <li>
                                        <ul>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=7&page=1">Gọng
                                                kính trẻ en</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=8&page=1">Kính
                                                mát trẻ em</a></li>
                                        </ul>
                                    </li>
                                    <li><a class="dropdown-item"
                                           href="<%=requestString%>&category-group-id=3&category-id=-1&page=1">Gọng
                                        kính</a></li>
                                    <li>
                                        <ul>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=9&page=1">Gọng
                                                kính nữa khung</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=10&page=1">Gọng
                                                kính khoan</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=11&page=1">Gọng
                                                kính tròn</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=12&page=1">Gọng
                                                kính titan</a></li>
                                        </ul>
                                    </li>
                                    <li><a class="dropdown-item"
                                           href="<%=requestString%>&category-group-id=4&category-id=-1&page=1">Tròng
                                        kính</a></li>
                                    <li>
                                        <ul>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=13&page=1">Tròng
                                                kính chống ánh sáng xanh</a>
                                            </li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=14&page=1">Tròng
                                                kính đổi màu</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=15&page=1">Tròng
                                                kính màu</a></li>
                                            <li><a class="dropdown-item"
                                                   href="<%=requestString%>&category-group-id=-1&category-id=16&page=1">Tròng
                                                kính cho gọng khoan</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="search pb-4">
                <div class="search_header ms-4">
                    <span>Tìm kiếm</span>
                </div>

                <div class="search-body row">
                    <div class="option-search col-9">
                        <form action="product_manager" method="GET" accept-charset="UTF-8">
                            <div class="search-item rounded">
                                <input type="text" name="name" id="search-name-product"
                                       placeholder="Nhập tên sản phẩm" value="<%=request.getAttribute("name")%>">
                                <label for="search-name-product" class="d-flex align-items-center p-1"><span
                                        class="material-symbols-outlined ps-1 fs-3">search</span></label>
                            </div>
                            <input type="text" name="category-group-id"
                                   value="<%=request.getAttribute("category-group-id")%>" hidden>
                            <input type="text" name="category-id" value="<%=request.getAttribute("category-id")%>"
                                   hidden>
                            <input type="text" name="brand-name" value="<%=request.getAttribute("brand-name")%>" hidden>
                            <input type="text" name="available" value="<%=request.getAttribute("available")%>" hidden>
                            <input type="text" name="page"
                                   value="1" hidden>
                            <input type="submit" value="" hidden="">
                        </form>
                    </div>

                    <div class="addProduct  ms-5 col-3">
                        <a href="product_manager?action=add">
                            <button class="btn d-flex" data-bs-toggle="modal" data-bs-target="#form-add-product">
                                <span>Thêm sản phẩm</span>
                                <span class="material-symbols-outlined">add</span>
                            </button>
                        </a>
                    </div>
                </div>
            </div>

            <div class="table">
                <div class="row header-table  align-items-center ps-4">
                    <div class="col-4">Sản phẩm</div>
                    <div class="col-2">Dòng sản phẩm</div>
                    <div class="col-1">Số lượng</div>
                    <div class="col-1">Đã bán</div>
                    <div class="col-2">Giá tiền</div>
                    <div class="col-1 px-2">Trạng thái</div>
                    <div class="col-1"></div>
                </div>

                <!--phần thân-->
                <div class="body-table">
                    <%
                        NumberFormat nfNumber = NumberFormat.getNumberInstance(Locale.of("vi", "VN"));
                        NumberFormat nfCurrency = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        for (Product product : products) {%>
                    <div class="product row ps-4">
                        <div class="col-4 d-flex">
                            <div class="img-product">
                                <img src="../<%=product.getModels().get(0).getUrlImage()%>" alt="hinh_anh.png">
                            </div>
                            <div class="info-product ms-2 w-100">
                                <p class="name-product"><%=product.getName()%>
                                </p>
                                <p class="id-product">#<%=product.getId()%>
                                </p>
                                <select class="select-model">
                                    <%for (Model model : product.getModels()) {%>
                                    <option value="<%=model.getId()%>"><%=model.getName()%>
                                    </option>
                                    <%}%>
                                </select>
                            </div>
                        </div>
                        <div class="col-2 type-product">
                            <%=product.getCategoryName()%>
                        </div>
                        <div class="col-1 amount-product">
                            <%=nfNumber.format(product.getModels().get(0).getQuantity())%>
                        </div>
                        <div class="col-1 amount-product-bought">
                            <%=nfNumber.format(product.getModels().get(0).getTotalQuantitySold())%>
                        </div>
                        <div class="col-2 price">
                            <%=nfCurrency.format(product.getPrice())%>
                        </div>
                        <%if (product.getModels().get(0).available()) {%>
                        <div class="col-1 status">Còn hàng</div>
                        <%} else {%>
                        <div class="col-1 status">Hết hàng</div>
                        <%}%>
                        <div class="col-1">
                            <span class="material-symbols-outlined d-inline-block lock-product"
                                  product-id="<%=product.getId()%>"
                                  lock="<%=product.getDelete()%>"><%=product.isLock() ? "lock" : "lock_open"%></span>
                            <span product-id="<%=product.getId()%>"
                                  class="material-symbols-outlined d-inline-block edit-product">edit</span>
                        </div>
                    </div>
                    <%}%>
                </div>
                <!--kết thúc thân-->

                <!--Phần footer, coppy-->
                <div class="footer-table row p-4 d-flex align-items-center">
                    <div class="text-amount-account col-6">
                        <span class="ps-0 pe-0">Tổng số sản phẩm: </span>
                        <span class="amount ps-0 pe-0"><%=nfNumber.format(Integer.parseInt(String.valueOf(request.getAttribute("total-product"))))%></span>
                        <span class="ps-0 pe-0"> sản phẩm</span>
                    </div>
                    <div class="change-page-display-list col-6 d-flex ps-5 justify-content-end">
                        <%
                            int totalPage = Integer.parseInt(String.valueOf(request.getAttribute("total-page"))),
                                    currentPage = Integer.parseInt(String.valueOf(request.getAttribute("page"))),
                                    indexPage = 1;
                            if (totalPage != 1) {
                        %>
                        <%if (currentPage != 1) {%>
                        <a href="<%=requestString%>&page=1">
                            <button class="d-flex align-items-center justify-content-center">
                                <span class="material-symbols-outlined">keyboard_double_arrow_left</span>
                            </button>
                        </a>
                        <a href="<%=requestString%>&page=<%=currentPage-1%>">
                            <button id="prev" class="d-flex align-items-center justify-content-center">
                                <span class="material-symbols-outlined">chevron_left</span>
                            </button>
                        </a>
                        <%}%>
                        <%
                            for (indexPage = currentPage - 2; indexPage < currentPage; indexPage++) {
                                if (indexPage > 0) {
                        %>
                        <a href="<%=requestString%>&page=<%=indexPage%>">
                            <button class="d-flex align-items-center justify-content-center button-number"
                                    data-target="<%=indexPage%>">
                                <%=indexPage%>
                            </button>
                        </a>
                        <%
                                }
                            }
                        %>
                        <%for (indexPage = currentPage; indexPage <= totalPage && (indexPage - currentPage) < (4 - ((currentPage - 3) > 0 ? 0 : (currentPage - 3))); indexPage++) {%>
                        <a href="<%=requestString%>&page=<%=indexPage%>">
                            <button class="d-flex align-items-center justify-content-center button-number <%if(currentPage == indexPage){%>active<%}%>"
                                    data-target="<%=indexPage%>">
                                <%=indexPage%>
                            </button>
                        </a>
                        <%}%>
                        <%if (totalPage != 0 && currentPage != totalPage) {%>
                        <a href="<%=requestString%>&page=<%=currentPage+1%>">
                            <button id="next" class="d-flex align-items-center justify-content-center">
                                <span class="material-symbols-outlined">chevron_right</span>
                            </button>
                        </a>
                        <a href="<%=requestString%>&page=<%=totalPage%>">
                            <button class="d-flex align-items-center justify-content-center">
                                <span class="material-symbols-outlined">keyboard_double_arrow_right</span>
                            </button>
                        </a>
                        <%}%>
                        <%}%>
                    </div>
                </div>
                <!--kết thúc footer-->
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
<script src="../javascript/admin_pages/quan_ly_san_pham.js"></script>
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
    <%String message = (String) session.getAttribute("message");
    if (message != null){%>
    $.notify("<%=message%>", "success");
    <%}
    session.removeAttribute("message");
    %>
</script>
</body>
</html>