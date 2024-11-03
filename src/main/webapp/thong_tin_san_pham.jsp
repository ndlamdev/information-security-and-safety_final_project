<%@ page import="java.text.NumberFormat" %>
<%@ page import="model.service.CartService" %>
<%@ page import="model.bean.*" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) session.getAttribute("logo");%>
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
    <link rel="stylesheet" href="css/thong_tin_san_pham.css">

    <link rel="icon" type="image/x-icon" href="<%=logo.getUrlImage()%>">

    <%--jquery--%>
    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <%--notify--%>
    <script src="notify/notify.js"></script>

    <%! public String currentVietnames(double amount) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(amount);
    }%>
    <title>Thông tin sản phẩm</title>
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
    <%
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
        Product product = (Product) request.getAttribute("product");%>
    <!--Phần đặt mua-->
    <section class="productWrap">
        <div class="container d-flex">
            <!--Phần hiển thị hình ảnh tự động chuyển-->
            <div id="carouselExampleAutoplaying" class="carousel" data-bs-ride="carousel">
                <%--Khung hien thi anh nho--%>
                <div class="carousel-indicators">
                    <ul class="list-group">
                        <%
                            List<ProductImage> productImages = (List<ProductImage>) product.getProductImages();
                            for (int i = 0; i < productImages.size(); i++) {
                        %>
                        <li class="list-group-item">
                            <button type="button" data-bs-target="#carouselExampleAutoplaying"
                                    data-bs-slide-to="<%=i%>"
                                    class=" <%=i == 0 ? "active" : ""%>" aria-current="true"
                                    aria-label="Slide <%=i%>">
                                <img src="<%=productImages.get(i).getUrlImage()%>"
                                     class="d-block w-100" alt="<%=product.getName()%>.png">
                            </button>
                        </li>
                        <%}%>
                    </ul>
                </div>

                <%--khung hien thi anh lon--%>
                <div class="carousel-inner">
                    <%for (int i = 0; i < productImages.size(); i++) {%>
                    <div class="carousel-item <%=i==0 ? "active" : ""%>">
                        <img src="<%=productImages.get(i).getUrlImage()%>"
                             class="d-block w-100" alt="<%=product.getName()%>.png">
                    </div>
                    <%}%>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>

            <!--Phần hiển thị thông tin và button thao tác-->
            <div class="productWrapDetail ms-5 w-100">
                <div class="productWrapDetailTitle">
                    <h1 class="productTitle"><%=product.getName()%>
                    </h1>
                </div>

                <!--Phần đánh giá-->
                <div class="sold_qty d-flex align-items-center mt-3 mb-1">
                    <div class="prod-review-loop d-flex">
                        <div class="onirvapp--shape-container me-1">
                            <ul class="list-group list-group-horizontal">
                                <!--Các li có class checked là sao hoàn thiện-->
                                <%
                                    int index = 0;
                                    for (; index < product.getStarNumber(); index++) {
                                %>
                                <li>
                                    <i class="fa-solid fa-star" style="color: #fdd836;"></i>
                                </li>
                                <%
                                    }
                                    if (index < 5) {
                                        for (; index < 5; index++) {
                                %>
                                <li>
                                    <i class="fa-regular fa-star" style="color: #fdd836;"></i>
                                </li>
                                <%
                                        }
                                    }
                                %>
                            </ul>
                        </div>

                        <span class="onireviewapp-loopitem-title">(<%=product.getTotalReview()%> đánh giá)</span>
                    </div>

                    <span class="h-line mx-2"></span>

                    <div class="sold_qty_num">
                        <p class="m-0">
                            Đã bán: <span><%=product.getTotalQuantitySold()%></span>
                        </p>
                    </div>
                </div>

                <!--Phàn thông tin-->
                <div class="productInfoMain row row-cols-2 pb-3 mb-3">
                    <div class="checkProduct productAvailable col">
                        <strong>Tình trạng: </strong>
                        <%if (product.available()) {%>
                        Còn hàng
                        <%} else {%>
                        Hết hàng
                        <%}%>
                    </div>
                    <div class="productSku col"><span><strong>Mã sản phẩm:</strong> </span><%=product.getId()%>
                    </div>
                    <div class="productVendor col">
                        <strong>Thương hiệu: </strong>Palm Angels
                    </div>
                    <div class="productCategory col">
                        <strong>Dòng sản phẩm: </strong><%=product.getCategoryName()%>
                    </div>
                    <div class="productType col">
                        <strong>Kiểu dáng: </strong><%=product.getType()%>
                    </div>
                    <div class="productMaterial col">
                        <strong>Chất liệu: </strong><%=product.getMaterial()%>
                    </div>
                </div>

                <!--Phần giá-->
                <div class="productPrice pb-3 mb-3">
                    <%if (product.hasDiscount()) {%>
                    <p class="fw-bold d-inline me-3">
                        <%=nf.format(product.getDiscount())%>
                    </p>
                    <del>
                        <%=nf.format(product.getPrice())%>
                    </del>
                    <%} else {%>
                    <p class="fw-bold d-inline me-3">
                        <%=nf.format(product.getPrice())%>
                    </p>
                    <%}%>
                </div>

                <!--Phần lựa chọn option-->
                <%
                    List<Model> models = product.getModels();
                    if (!models.isEmpty()) {
                %>
                <div class="product-swatch mb-5">
                    <div class="product-sw-line">
                        <div class="dflex-new">
                            <div class="product-sw-title fw-bold">
                                Mẫu sản phẩm
                            </div>
                        </div>
                        <!--Phần button select-->
                        <ul class="product-sw-select">
                            <%
                                if (models.size() == 1) {%>
                            <li class="product-sw-select-item">
                                <button type="button"
                                        class="<%=models.get(0).available() ? "active button-model" : ""%>"
                                        model-id="<%=models.get(0).getId()%>">
                                    <img src="<%=models.get(0).getUrlImage()%>"
                                         alt="<%=models.get(0).getName()%>.png">
                                    <span><%=models.get(0).getName()%></span>
                                </button>
                            </li>
                            <% } else {
                                boolean haveActive = false;
                                for (int i = 0; i < models.size(); i++) {
                                    for (index = 0; index < productImages.size(); index++) {
                                        if (models.get(i).getUrlImage().equals(productImages.get(index).getUrlImage())) {
                            %>
                            <li class="product-sw-select-item">
                                <button type="button" data-bs-target="#carouselExampleAutoplaying"
                                        data-bs-slide-to="<%=index%>"
                                        class="<%if(models.get(i).available()) {%>
                                            <%="button-model"%>
                                            <%if(!haveActive) {
                                            haveActive = true;
                                            %>
                                            <%=" active"%>
                                            <%}%>
                                        <%}%>"
                                        aria-label="Slide 0"
                                        model-id="<%=models.get(i).getId()%>">
                                    <img src="<%=models.get(i).getUrlImage()%>"
                                         alt="<%=models.get(i).getName()%>.png">
                                    <span><%=models.get(i).getName()%></span>
                                </button>
                            </li>
                            <% break;
                            }
                            }
                            }
                            }
                            %>
                        </ul>
                    </div>
                </div>
                <%}%>

                <!--Phần số lượng và đặt mua-->
                <div class="productActionMain">
                    <div class="groupAdd d-flex align-items-center mb-2">
                        <div class="input-group itemQuantity">
                            <button class="input-group-text qtyBtn minus-quantity" data-type="minus">-</button>
                            <input type="number" class="input-group-text form-control quantitySelector"
                                   id="product-quantity"
                                   aria-label="Username" value="1">
                            <button class="input-group-text qtyBtn plus-quantity" data-type="plus">+</button>
                        </div>
                        <div class="productAction">
                            <button type="button" product-id="<%=product.getId()%>" id="add-product-cart">Thêm vào
                                giỏ
                                hàng
                            </button>
                            <button type="button" product-id="<%=product.getId()%>" id="buy-product-now">Mua ngay
                            </button>
                        </div>
                    </div>

                    <div class="hotline-product text-center">
                        <span>Gọi đặt mua hàng<a href="0855354919">&nbsp;<strong>0855.354.919</strong></a>&nbsp;( 9:00 - 20:00 )</span>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </section>

    <!--Phần thêm mô tả thông tin-->
    <section class="productTabsContent mt-5">
        <div class="container">
            <!--phần nút chuyển tab. Muốn làm thêm thì tự viết nha-->
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#tabOne" role="tab"
                       aria-controls="home" aria-selected="true">Thông tin</a>
                </li>
            </ul>

            <!--phần khung hiển thị nội dung-->
            <div class="tab-content mt-3" id="tabContent">
                <!--Phần nội dung-->
                <div class="tab-pane fade show more-info_pro active" id="tabOne" role="tabpanel"
                     aria-labelledby="home-tab">
                    <div style="max-height: 500px" class="active overflow-hidden product-describe">
                        <strong>THÔNG TIN SẢN PHẨM:</strong>
                        <%=product.getDescribe()%>
                    </div>
                    <a href="javascript:void(0);" class="readmore open">
                        <div class="readmore_content d-flex align-items-center justify-content-center mx-auto">
                            <span>Xem thêm</span>
                            <span class="material-symbols-outlined">expand_more</span>
                        </div>
                    </a>
                    <a href="#tabContent" class="readless close d-none">
                        <div class="readless_content d-flex align-items-center justify-content-center mx-auto">
                            <span>Rút gọn</span>
                            <span class="material-symbols-outlined">expand_less</span>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </section>

    <!--Phần review-->
    <section class="pro__preview mt-5">
        <div class="container">
            <!--Title-->
            <div class="section-title-all">
                <h3>Đánh giá, nhận xét khách hàng</h3>
            </div>

            <!--Phần hiển thị nội dung-->
            <div class="onireviewapp-container">
                <div id="onireviewapp" data-customer-email="" data-customer-name="" data-pid="1017229646">
                    <div id="onirvapp-detail-body">
                        <div class="onirvapp-detail-tabs row">
                            <div class="onirvapp-tab col-10 mt-2">
                                <span>Danh sách đánh giá</span>
                                <span id="numberOfReview"> (<%=product.getTotalReview()%> đánh giá)</span>
                            </div>
                        </div>
                        <div class="onirvapp-detail-content mt-3">
                            <div id="onirvapp-review-list" class="onirvapp-comments-list active">
                                <%if (product.getTotalReview() == 0) {%>
                                <div class="d-none onirvapp-review-empty text-center">
                                    <span class="onirvapp-comments-empty-text">Chưa có đánh giá nào</span>
                                </div>
                                <%
                                } else {
                                    for (Review review : product.getReviews()) {
                                %>
                                <div class="a-review mb-3">
                                    <div class="user">
                                        <div class="avatar-user left">
                                            <img src="<%=review.getUser().getAvatar()%>">
                                        </div>
                                        <div class="right">
                                            <div class="name-user"><span><%=review.getUser().getFullName()%></span>
                                            </div>
                                            <div class="onirvapp--shape-container d-inline-block">
                                                <ul class="list-group list-group-horizontal">
                                                    <!--Các li có class checked là sao hoàn thiện-->
                                                    <%
                                                        index = 0;
                                                        for (; index < review.getNumberStar(); index++) {
                                                    %>
                                                    <li>
                                                        <i class="fa-solid fa-star" style="color: #fdd836;"></i>
                                                    </li>
                                                    <%
                                                        }
                                                        if (index < 5) {
                                                            for (; index < 5; index++) {
                                                    %>
                                                    <li>
                                                        <i class="fa-regular fa-star" style="color: #fdd836;"></i>
                                                    </li>
                                                    <%
                                                            }
                                                        }
                                                    %>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="date-review">
                                            <span><%=review.getDate()%></span>
                                        </div>
                                    </div>
                                    <div class="content-review">
                                        <div class="text-review">
                                            <span><%=review.getComment()%></span>
                                        </div>
                                        <div class="img-review">
                                            <%for (String url : review.getImages()) {%>
                                            <img src="<%=url%>" alt="img-review.img" class="me-1">
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                                <%
                                        }
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!--Phần sản xem gần đây-->
    <section class="other-product mt-5">
        <div class="container collection-wrap-product-list">
            <div class="other-product-title mb-3 pb-2">
                <h3>Sản phẩm xem gần đây</h3>
            </div>

            <%
                List<Product> recentlyViewedProducts = (List<Product>) request.getSession().getAttribute("recently-viewed-products");
                recentlyViewedProducts = recentlyViewedProducts == null ? new ArrayList<>() : recentlyViewedProducts;
                int pageRecentlyViewedProducts = recentlyViewedProducts.size() % 4 == 0 ? recentlyViewedProducts.size() / 4 - 1 : recentlyViewedProducts.size() / 4;
            %>
            <div id="carouselExampleIndicators" class="carousel slide mb-5 d-block position-relative ">
                <!--Các nút bên dưới hình-->
                <%for (int i = 0; i < pageRecentlyViewedProducts; i++) {%>
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="<%=i%>"
                            <%if(i == 0) {%>class="active"<%}%>
                            aria-current="true" aria-label="Slide <%=i+1%>"></button>
                </div>
                <%}%>

                <!--Danh sách các hình-->
                <div class="carousel-inner">
                    <%
                        for (int i = 0; i < pageRecentlyViewedProducts + 1; i++) {
                    %>
                    <div class="carousel-item <%if(i == 0) {%>active<%}%>">
                        <div class="show-item-sale  row row-cols-xl-4 row-cols-lg-3 row-cols-md-2 row-cols-sm-1 mb-4 d-flex justify-content-center py-3">
                            <%
                                for (index = i * 4; index < recentlyViewedProducts.size(); index++) {
                                    product = recentlyViewedProducts.get(index);
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
                                            <a href="more-info-product?id=<%=product.getId()%>">
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
                                        <span class="pro-loop-vendor d-block"><%=recentlyViewedProducts.get(index).getBrandName()%></span>
                                    </div>

                                    <!--Hiển thị tên sản phẩm-->
                                    <h3 class="pro-loop-name text-center">
                                        <a href="more-info-product?id=<%=recentlyViewedProducts.get(index).getId()%>"
                                           title="Tên sản phẩm"><%=recentlyViewedProducts.get(index).getName()%>
                                        </a>

                                    </h3>

                                    <!--hiển thị giá-->
                                    <div class="pro-loop-price text-center mt-0">
                                        <%
                                            if (recentlyViewedProducts.get(index).getDiscount() > 0) {
                                        %>
                                        <p class="fw-bold d-inline me-3"><%=currentVietnames(recentlyViewedProducts.get(index).getDiscount())%>
                                        </p>
                                        <del><%=currentVietnames(recentlyViewedProducts.get(index).getPrice())%>
                                        </del>
                                        <%
                                        } else {
                                        %>
                                        <p class="fw-bold d-inline me-3"><%=currentVietnames(recentlyViewedProducts.get(index).getPrice())%>
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
                                                            if (recentlyViewedProducts.get(index).getStarNumber() >= j) {
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
                                            <span class="onireviewapp-loopitem-title">(<%=recentlyViewedProducts.get(index).getTotalReview()%> đánh giá)</span>
                                        </div>

                                        <!--Đường cắt ngang-->
                                        <span class="h-line d-inline-block"></span>

                                        <!--Phần hiển thị số lượng đã bán-->
                                        <div class="sold_qty_num  d-inline-block">
                                            <p class="m-0">
                                                Đã bán:
                                                <span><%=recentlyViewedProducts.get(index).getTotalQuantitySold()%></span>
                                            </p>
                                        </div>
                                    </div>

                                    <!--2 nút thao tác-->
                                    <div class="pro-loop-bottom">
                                        <button type="button"
                                                product-id="<%=recentlyViewedProducts.get(index).getId()%>"
                                                class="text-white f-button setAddCartLoop <%=recentlyViewedProducts.get(index).available() ? "show-models" : ""%>"
                                                data-type="show-models">
                                            Xem nhanh
                                        </button>
                                        <button type="button"
                                                product-id="<%=recentlyViewedProducts.get(index).getId()%>"
                                                class="text-white f-button setBuyNow <%=recentlyViewedProducts.get(index).available() ? "show-models" : ""%>"
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

                <%if (pageRecentlyViewedProducts != 0) {%>
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
    </section>

    <section>
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
                                    <input type="number" disabled
                                           class="input-group-text form-control quantity-selector"
                                           id="quantity" value="1">
                                    <button class="input-group-text plus-quantity" data-type="plus">+</button>
                                </div>
                                <div class="product-action d-flex">
                                    <button type="button" class="hover-opacity" id="add-cart">Thêm vào giỏ hàng
                                    </button>
                                    <button type="button" class="hover-opacity " id="buy-now">Mua ngay</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
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
                        <li><a class="hover" href="policy_pages/chinh_sach_doi_tra_va_hoan_tien.jsp">Chính sách
                            thanh toán, giao nhận</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_bao_mat.jsp">Chính sách bảo mật</a>
                        </li>
                        <li><a class="hover" href="policy_pages/chinh_sach_bao_hanh.jsp">Chính sách bảo hành</a>
                        </li>
                        <li><a class="hover" href="policy_pages/chinh_sach_doi_tra_va_hoan_tien.jsp">Chính sách
                            đổi trả và hoàn tiền</a></li>
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
<script src="javascript/thong_tin_san_pham.js"></script>
<script src="javascript/gian_hang.js"></script>
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
</script>
</body>
</html>