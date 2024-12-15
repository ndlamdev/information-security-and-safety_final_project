<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.Product" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%User user = (User) session.getAttribute("user");%>

<%BannerImage logo = (BannerImage) application.getAttribute("logo");%>
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
<%! public String currentVietNames(double amount) {
    Locale localeVN = Locale.of("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
    return currencyVN.format(amount);
}
%>
<body>
<jsp:include page="header.jsp"/>

<main>
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
                                            <p class="fw-bold d-inline me-3"><%=currentVietNames(prominentProducts.get(indexProduct).getDiscount())%>
                                            </p>
                                            <del><%=currentVietNames(prominentProducts.get(indexProduct).getPrice())%>
                                            </del>
                                            <%
                                            } else {
                                            %>
                                            <p class="fw-bold d-inline me-3"><%=currentVietNames(prominentProducts.get(indexProduct).getPrice())%>
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
                        <a class="" href="product-booth?id-category-group=0&id-category=0&page=1" data-hover="Xem thêm">Xem
                            thêm <i
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
                                            <p class="fw-bold d-inline me-3"><%=currentVietNames(discountProducts.get(indexProduct).getDiscount())%>
                                            </p>
                                            <del><%=currentVietNames(discountProducts.get(indexProduct).getPrice())%>
                                            </del>
                                            <%
                                            } else {
                                            %>
                                            <p class="fw-bold d-inline me-3"><%=currentVietNames(discountProducts.get(indexProduct).getPrice())%>
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
    <%--Hiển thị modal các com.lamnguyen.mat_kinh_kimi.model sản phẩm--%>
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
                                <label for="quantity"></label>
                                <input type="number" disabled
                                       class="input-group-text form-control quantity-selector"
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
</main>

<jsp:include page="footer.jsp"/>

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