<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.bean.User" %>
<%@ page import="model.bean.BannerImage" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.bean.Product" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.service.CartService" %>
<%User user = (User) session.getAttribute("user");%>
<%BannerImage logo = (BannerImage) session.getAttribute("logo");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="notify/notify-metro.css"/>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/gian_hang.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">
    <link rel="stylesheet" href="css/menu_footer.css">
    <%--jquery--%>
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <%--notify--%>
    <script src="notify/notify.js"></script>

    <title>Trag chủ</title>
</head>
<%! public String currentVietnames(double amount) {
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
    return currencyVN.format(amount);
}
%>
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
<!--    section 1-->
<section id="silder-section">
    <%
        List<BannerImage> bannerImages = (List<BannerImage>) request.getAttribute("banner-images");
    %>
    <div id="autoCarouselIndicators" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
        <%for (int i = 0; i < bannerImages.size(); i++) {%>
            <button type="button" data-bs-target="#autoCarouselIndicators" data-bs-slide-to="<%=i%>"
                    <%if(i == 0) {%>class="active"<%}%>
                    aria-current="true" aria-label="Slide <%=i+1%>"></button>
        <%}%>
        </div>
        <div class="carousel-inner">
            <%for (int i = 0; i < bannerImages.size(); i++) { %>
            <div class="carousel-item <%if (i == 0) {%>active<%}%>">
                <img src="<%=bannerImages.get(i).getUrlImage()%>" class="d-block w-100" alt="banner-<%=i%>.png">
            </div>
            <%}%>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#autoCarouselIndicators"
                data-bs-slide="prev">
            <span class="material-symbols-outlined">arrow_back </span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#autoCarouselIndicators"
                data-bs-slide="next">
            <span class="material-symbols-outlined">arrow_forward </span>
        </button>
    </div>
</section>

<section class="home-product-slider">
    <div class="container">
        <div class="home-product-slider-wrap">
            <div class="home-product-slider-wrap-header">
                <div class="section-title-all">
                    <div>
                        <h2>Sản phẩm</h2>
                    </div>
                </div>
            </div>
            <%
                List<Product> prominentProducts = (List<Product>) request.getAttribute("list-product-prominent");
                int amountPageProminentProducts = prominentProducts.size() % 4 == 0 ? prominentProducts.size() / 4 : (prominentProducts.size() / 4) + 1;
            %>
            <div id="carouselExampleIndicators" class="carousel slide mb-5 d-block position-relative ">
                <!--Các nút bên dưới hình-->
                <div class="carousel-indicators">
                    <%for (int indexPage = 0; indexPage < amountPageProminentProducts; indexPage++) {%>
                    <button type="button" data-bs-target="#carouselExampleIndicators"
                            data-bs-slide-to="<%=indexPage%>"
                            class="<%=indexPage == 0 ? "active": ""%>"
                            aria-current="true" aria-label="Slide <%=indexPage + 1%>"></button>
                    <%}%>
                </div>
                <!--Danh sách các hình-->
                <div class="carousel-inner">
                    <%
                        for (int indexPage = 0; indexPage < amountPageProminentProducts; indexPage++) {
                    %>
                    <div class="carousel-item <%if(indexPage == 0) {%>active<%}%>">
                        <div class="show-item-sale  row row-cols-xl-4 row-cols-lg-3 row-cols-md-2 row-cols-sm-1 mb-4 d-flex justify-content-center py-3">
                            <%
                                for (int indexProduct = 0; indexProduct + indexPage * 4 < prominentProducts.size() && indexProduct < 4; indexProduct++) {
                                    Product product = prominentProducts.get(indexProduct + indexPage * 4);
                            %>
                            <div class="pro-loop col rounded-3">
                                <!--Phạm vi hiển thị trong ô-->
                                <div class="pro-loop-wrap position-relative">
                                    <!--Phần hình ảnh-->
                                    <div class="pro-loop-image position-relative">

                                        <!--Hiển thị hêt hàng-->
                                        <%if (!product.available()) {%>
                                        <div class="pro-loop-sd z-2 position-absolute">
                                            <span>Hết hàng</span>
                                        </div>
                                        <%}%>

                                        <!--Hiển thị hình ảnh-->
                                        <div class="pro-loop-image-item">
                                            <a href="more_info_product?id=<%=product.getId()%>">
                                                <!--Ảnh khi chưa horver vào phần "Ô hiển thị"-->
                                                <picture class="img-hidden-when-hover">
                                                    <img class="lazyloaded rounded-3"
                                                         src="<%=product.getProductImages().get(0).getUrlImage()%>"
                                                         alt="<%=product.getName()%>.jsp">
                                                </picture>
                                                <!--Ảnh khi horver vào phẩn "Ô hiển thị"-->
                                                <picture class="img-show-when-hover">
                                                    <img class="lazyloaded  rounded-3"
                                                         src="<%=product.getProductImages().get(1).getUrlImage()%>"
                                                         alt="<%=product.getName()%>.jsp">
                                                </picture>
                                            </a>
                                        </div>
                                    </div>

                                    <!--hiển thị sản phẩm đang là sản phẩm hot-->
                                    <div class="pro-loop-sold position-absolute">
                                        <label>
                                            <img src="images/hot.jpg" alt="pro-loop-sold">
                                        </label>
                                    </div>

                                    <!--Hiển thị tên thương hiệu-->
                                    <div class="pro-loop-brand text-center">
                                        <span class="pro-loop-vendor d-block"><%=prominentProducts.get(indexProduct).getBrandName()%></span>
                                    </div>

                                    <!--Hiển thị tên sản phẩm-->
                                    <h3 class="pro-loop-name text-center">
                                        <a href="more_info_product?id=<%=prominentProducts.get(indexProduct).getId()%>"
                                           title="Tên sản phẩm"><%=prominentProducts.get(indexProduct).getName()%>
                                        </a>

                                    </h3>

                                    <!--hiển thị giá-->
                                    <div class="pro-loop-price text-center mt-0">
                                        <%
                                            if (prominentProducts.get(indexProduct).getDiscount() > 0) {
                                        %>
                                        <p class="fw-bold d-inline me-3"><%=currentVietnames(prominentProducts.get(indexProduct).getDiscount())%>
                                        </p>
                                        <del><%=currentVietnames(prominentProducts.get(indexProduct).getPrice())%>
                                        </del>
                                        <%
                                        } else {
                                        %>
                                        <p class="fw-bold d-inline me-3"><%=currentVietnames(prominentProducts.get(indexProduct).getPrice())%>
                                        </p>
                                        <%
                                            }
                                        %>
                                    </div>

                                    <!--Hiển thị đánh giá và số lượng bán-->
                                    <div class="sold_qty text-center">
                                        <!--Phần đánh giá sao-->
                                        <div class="prod-review-loop   d-inline-block">
                                            <!--Danh sách ngôi sao-->
                                            <div class="onirvapp--shape-container d-inline-block">
                                                <ul class="list-group list-group-horizontal">
                                                    <!--Các li có class checked là sao hoàn thiện-->
                                                    <%
                                                        for (int j = 1; j <= 5; j++) {
                                                            if (prominentProducts.get(indexProduct).getStarNumber() >= j) {
                                                    %>
                                                    <li class="checked">
                                                        <i class="fa-solid fa-star" style="color: #fdd836;"></i>
                                                    </li>
                                                    <%
                                                    } else {
                                                    %>
                                                    <li class="checked">
                                                        <i class="fa-regular fa-star" style="color: #fdd836;"></i>
                                                    </li>
                                                    <%
                                                            }
                                                        }
                                                    %>
                                                </ul>
                                            </div>

                                            <!--số lượng đánh giá-->
                                            <span class="onireviewapp-loopitem-title">(<%=prominentProducts.get(indexProduct).getTotalReview()%> đánh giá)</span>
                                        </div>

                                        <!--Đường cắt ngang-->
                                        <span class="h-line d-inline-block"></span>

                                        <!--Phần hiển thị số lượng đã bán-->
                                        <div class="sold_qty_num  d-inline-block">
                                            <p class="m-0">
                                                Đã bán:
                                                <span><%=prominentProducts.get(indexProduct).getTotalQuantitySold()%></span>
                                            </p>
                                        </div>
                                    </div>

                                    <!--2 nút thao tác-->
                                    <div class="pro-loop-bottom">
                                        <button type="button"
                                                product-id="<%=prominentProducts.get(indexProduct).getId()%>"
                                                class="f-button setAddCartLoop <%=prominentProducts.get(indexProduct).available() ? "show-models" : ""%>"
                                                data-type="show-models">
                                            Xem nhanh
                                        </button>
                                        <button type="button"
                                                product-id="<%=prominentProducts.get(indexProduct).getId()%>"
                                                class="f-button setBuyNow <%=prominentProducts.get(indexProduct).available() ? "show-models" : ""%>"
                                                data-type="buy-now" data-id="">
                                            Mua ngay
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <%}%>
                        </div>
                    </div>
                    <%}%>
                </div>

                <%if (amountPageProminentProducts > 1) {%>
                <!--2 Nút chuyển qua và lại-->
                <button class="carousel-control-prev carousel-control-color " type="button"
                        data-bs-target="#carouselExampleIndicators"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                </button>
                <button class="carousel-control-next carousel-control-color" type="button"
                        data-bs-target="#carouselExampleIndicators"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                </button>
                <%}%>
            </div>
        </div>
    </div>
</section>

<section class="home-banner">
    <div class="container">
        <div class="home-banner-wrap row text-center">
            <div class="home-banner-wrap-left col">
                <h2>TRÒNG KÍNH CRIZAL ROCK GIẢM 20%</h2>
                <p>BỀN BỈ GẤP 3 LẦN THẾ HỆ TRƯỚC
                </p>
                <a href="product-booth?id-category-group=4&id-category=0&page=1">Mua ngay</a>
            </div>
            <div class="home-banner-wrap-right col">
                <%
                    BannerImage prImage = (BannerImage) session.getAttribute("bannerPRImages");
                %>
                <img class=" ls-is-cached lazyloaded" src="<%=prImage.getUrlImage()%>"
                     alt="home-banner-right">
            </div>
        </div>
    </div>
</section>


<section class="home-product">
    <div class="container">
        <div class="home-product-slider-wrap">
            <div class="home-product-slider-wrap-header">
                <div class="section-title-all">
                    <div>
                        <h2>Sản phẩm khuyến mãi</h2>
                    </div>
                    <a class="" href="khuyen_mai.jsp" data-hover="Xem thêm">Xem thêm <i
                            class="fa-solid fa-arrow-right"></i></a>
                </div>
            </div>
            <%
                List<Product> discountProducts = (List<Product>) request.getAttribute("list-product-discount");
                int amountPageDiscountProducts = discountProducts.size() % 4 == 0 ? discountProducts.size() / 4 : (discountProducts.size() / 4) + 1;
            %>
            <div id="carouselExampleIndicators-01" class="carousel slide mb-5 d-block position-relative ">
                <!--Các nút bên dưới hình-->
                <div class="carousel-indicators">
                    <%for (int i = 0; i < amountPageDiscountProducts; i++) { %>
                    <button type="button" data-bs-target="#carouselExampleIndicators-01" data-bs-slide-to="<%=i%>"
                            class="<%=i == 0 ? "active" : ""%>"
                            aria-current="true" aria-label="Slide <%=i+1%>"></button>
                    <%}%>
                </div>
                <!--Danh sách các hình-->
                <div class="carousel-inner">
                    <%
                        for (int indexPage = 0; indexPage < amountPageDiscountProducts; indexPage++) {
                    %>
                    <div class="carousel-item <%if(indexPage == 0) {%>active <%}%>">
                        <div class="show-item-sale  row row-cols-xl-4 row-cols-lg-3 row-cols-md-2 row-cols-sm-1 mb-4 d-flex justify-content-center py-3">
                            <%
                                for (int indexProduct = 0; indexProduct < 4 && (indexProduct + indexPage * 4) < discountProducts.size(); indexProduct++) {
                                    Product product = discountProducts.get(indexProduct + indexPage * 4);
                            %>
                            <div class="pro-loop col rounded-3">
                                <!--Phạm vi hiển thị trong ô-->
                                <div class="pro-loop-wrap position-relative">
                                    <!--Phần hình ảnh-->
                                    <div class="pro-loop-image position-relative">

                                        <!--Hiển thị hêt hàng-->
                                        <%if (!product.available()) {%>
                                        <div class="pro-loop-sd z-2 position-absolute">
                                            <span>Hết hàng</span>
                                        </div>
                                        <%}%>

                                        <!--Hiển thị quà tặng-->
                                        <div class="gift product_gift_label d-none z-1" data-id="1012829436">
                                            <img class="lazyload" src="images/qua_tang.jpg" alt="icon quà tặng">
                                        </div>

                                        <!--Hiển thị hình ảnh-->
                                        <div class="pro-loop-image-item">
                                            <a href="more_info_product?id=<%=product.getId()%>">
                                                <!--Ảnh khi chưa horver vào phần "Ô hiển thị"-->
                                                <picture class="img-hidden-when-hover">
                                                    <img class="lazyloaded rounded-3"
                                                         src="<%=product.getProductImages().get(0).getUrlImage()%>"
                                                         alt="<%=product.getName()%>.jsp">
                                                </picture>
                                                <!--Ảnh khi horver vào phẩn "Ô hiển thị"-->
                                                <picture class="img-show-when-hover">
                                                    <img class="lazyloaded  rounded-3"
                                                         src="<%=product.getProductImages().get(1).getUrlImage()%>"
                                                         alt="<%=product.getName()%>.jsp">
                                                </picture>
                                            </a>
                                        </div>
                                    </div>

                                    <!--hiển thị sản phẩm đang là sản phẩm hot-->
                                    <div class="pro-loop-sold position-absolute">
                                        <label>
                                            <img src="images/hot.jpg" alt="pro-loop-sold">
                                        </label>
                                    </div>

                                    <!--Hiển thị tên thương hiệu-->
                                    <div class="pro-loop-brand text-center">
                                        <span class="pro-loop-vendor d-block"><%=discountProducts.get(indexProduct).getBrandName()%></span>
                                    </div>

                                    <!--Hiển thị tên sản phẩm-->
                                    <h3 class="pro-loop-name text-center">
                                        <a href="more_info_product?id=<%=discountProducts.get(indexProduct).getId()%>"
                                           title="Tên sản phẩm"><%=discountProducts.get(indexProduct).getName()%>
                                        </a>

                                    </h3>

                                    <!--hiển thị giá-->
                                    <div class="pro-loop-price text-center mt-0">
                                        <%
                                            if (discountProducts.get(indexProduct).getDiscount() > 0) {
                                        %>
                                        <p class="fw-bold d-inline me-3"><%=currentVietnames(discountProducts.get(indexProduct).getDiscount())%>
                                        </p>
                                        <del><%=currentVietnames(discountProducts.get(indexProduct).getPrice())%>
                                        </del>
                                        <%
                                        } else {
                                        %>
                                        <p class="fw-bold d-inline me-3"><%=currentVietnames(discountProducts.get(indexProduct).getPrice())%>
                                        </p>
                                        <%
                                            }
                                        %>
                                    </div>

                                    <!--Hiển thị đánh giá và số lượng bán-->
                                    <div class="sold_qty text-center">
                                        <!--Phần đánh giá sao-->
                                        <div class="prod-review-loop   d-inline-block">
                                            <!--Danh sách ngôi sao-->
                                            <div class="onirvapp--shape-container d-inline-block">
                                                <ul class="list-group list-group-horizontal">
                                                    <!--Các li có class checked là sao hoàn thiện-->
                                                    <%
                                                        for (int j = 1; j <= 5; j++) {
                                                            if (discountProducts.get(indexProduct).getStarNumber() >= j) {
                                                    %>
                                                    <li class="checked">
                                                        <i class="fa-solid fa-star" style="color: #fdd836;"></i>
                                                    </li>
                                                    <%
                                                    } else {
                                                    %>
                                                    <li class="checked">
                                                        <i class="fa-regular fa-star" style="color: #fdd836;"></i>
                                                    </li>
                                                    <%
                                                            }
                                                        }
                                                    %>
                                                </ul>
                                            </div>

                                            <!--số lượng đánh giá-->
                                            <span class="onireviewapp-loopitem-title">(<%=discountProducts.get(indexProduct).getTotalReview()%> đánh giá)</span>
                                        </div>

                                        <!--Đường cắt ngang-->
                                        <span class="h-line d-inline-block"></span>

                                        <!--Phần hiển thị số lượng đã bán-->
                                        <div class="sold_qty_num  d-inline-block">
                                            <p class="m-0">
                                                Đã bán:
                                                <span><%=discountProducts.get(indexProduct).getTotalQuantitySold()%></span>
                                            </p>
                                        </div>
                                    </div>

                                    <!--2 nút thao tác-->
                                    <div class="pro-loop-bottom">
                                        <button type="button"
                                                product-id="<%=discountProducts.get(indexProduct).getId()%>"
                                                class="f-button setAddCartLoop <%=discountProducts.get(indexProduct).available() ? "show-models" : ""%>"
                                                data-type="show-models">
                                            Xem nhanh
                                        </button>
                                        <button type="button"
                                                product-id="<%=discountProducts.get(indexProduct).getId()%>"
                                                class="f-button setBuyNow <%=discountProducts.get(indexProduct).available() ? "show-models" : ""%>"
                                                data-type="buy-now" data-id="">
                                            Mua ngay
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <%}%>
                        </div>
                    </div>
                    <%}%>
                </div>

                <%if (amountPageDiscountProducts != 0) {%>
                <!--2 Nút chuyển qua và lại-->
                <button class="carousel-control-prev carousel-control-color " type="button"
                        data-bs-target="#carouselExampleIndicators-01"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                </button>
                <button class="carousel-control-next carousel-control-color" type="button"
                        data-bs-target="#carouselExampleIndicators-01"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                </button>
                <%}%>
            </div>
        </div>
    </div>
</section>
<%--Hiển thị modal các model sản phẩm--%>
<button hidden="" type="button" id="show-modal" data-bs-toggle="modal" data-bs-target="#modal"></button>
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="modalLabel">Chọn mẫu bạn mong muốn</h1>
                <button id="close-modal" type="button" class="btn-close" data-bs-dismiss="modal"
                        aria-label="Close"></button>
            </div>

            <div class="modal-body position-relative">
                <div class="product-swatch">
                    <div class="product-sw-line">
                        <div id="carousel" class="carousel slide">
                            <%--Phần hiển thị hình ảnh--%>
                            <div class="carousel-inner" id="model-image">
                            </div>
                            <button class="carousel-control-prev" type="button"
                                    data-bs-target="#carousel" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next" type="button"
                                    data-bs-target="#carousel" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                    </div>
                </div>

                <!--Phần số lượng và đặt mua-->
                <div class="product position-absolute">
                    <div class="product-name" id="product-name">
                    </div>
                    <%--Phần hiển thị các option--%>
                    <div class="product-model" id="option-model">
                    </div>
                    <div class="group-add d-flex flex-column align-items-center mb-2 position-absolute">
                        <div id="input-amount" class="input-group item-quantity mb-2">
                            <button class="input-group-text minus-quantity" data-type="minus">-</button>
                            <input type="number" disabled class="input-group-text form-control quantity-selector"
                                   id="quantity" value="1">
                            <button class="input-group-text plus-quantity" data-type="plus">+</button>
                        </div>
                        <div class="product-action d-flex">
                            <button type="button" class="hover-opacity" id="add-cart">Thêm vào giỏ hàng</button>
                            <button type="button" class="hover-opacity " id="buy-now">Mua ngay</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<footer id="footer" class="footer">
    <div class="container">
        <div class="footer-top row">
            <div class="footer-top-item col">
                <h5>Mắt Kính KIMI
                </h5>
                <div class="footer-content footer-contact">
                    <div class="ft_map">

                    </div>
                    <ul>
                        <li class="contact-1"><i class="fa-solid fa-map-location-dot px-1"></i><span
                                class="px-1">Khu phố 6, Phường Linh Trung, Quận Thủ Đức, TP. Hồ Chí Minh</span>
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
                        <li><a class="hover" href="policy_pages/chinh_sach_thanh_toan_va_giao_nhan.jsp">Chính sách
                            thanh toán, giao nhận</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_bao_mat.jsp">Chính sách bảo mật</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_bao_hanh.jsp">Chính sách bảo hành</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_doi_tra_va_hoan_tien.jsp">Chính sách đổi
                            trả và hoàn tiền</a></li>
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
<script src="javascript/index.js"></script>
<script src="javascript/gian_hang.js"></script>
<script type="text/javascript">
    <%if(user != null){%>
    const user = new User();
    user.setId(<%=user.getId()%>);
    user.setAvatar("<%=user.getAvatar()%>");
    user.setFullName("<%=user.getFullName()%>");
    displayMenuAccount(user);
    <%} else{%>
    hiddenMenuAccount();
    <%}%>
</script>
</body>
</html>