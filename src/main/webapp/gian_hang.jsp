<%@ page import="com.lamnguyen.mat_kinh_kimi.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.Cart" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.Model" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  Created by IntelliJ IDEA.
  User: Tu
  Date: 11/27/2023
  Time: 4:28 PM
  To change this template use File | Settings | File Templates.
--%>
<% BannerImage logo = (BannerImage) session.getAttribute("logo"); %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="notify/notify-metro.css"/>
    <link rel="stylesheet" href="css/gian_hang.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="icon" type="image/x-icon" href="<%=logo.getUrlImage()%>">

    <%--jquery--%>
    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <%--notify--%>
    <script src="notify/notify.js"></script>

    <title><%=request.getAttribute("title")%>
    </title>
</head>
<body>
<jsp:include page="header.jsp"/>

<main id="main" class="mt-2 pb-5">
    <div class="container">
        <!--Phần slide-->
        <div id="carouselIndicators" class="carousel slide mb-3" data-bs-ride="carousel">
            <div class="carousel-indicators" id="banner-indicators">
            </div>
            <div class="carousel-inner" id="banner-inner">
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselIndicators"
                    data-bs-slide="prev">
                <span class="material-symbols-outlined">arrow_back </span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselIndicators"
                    data-bs-slide="next">
                <span class="material-symbols-outlined">arrow_forward </span>
            </button>
        </div>
        <!--End phần slide-->

        <!--Phần nội dung-->
        <div class="content collection-wrap">
            <!--phần fillter-->
            <section class="collection-wrap-filter">
                <!--Phần title-->
                <div class="filter-header">
                    <div class="section-title-all row d-flex align-items-center">
                        <div class="col-11">
                            <h2 class="d-inline-block col-11 fs-4">Bộ lọc (<%=request.getAttribute("totalFilter")%>lựa
                                chọn) <i class="lnil lni-plus"></i>
                            </h2>
                        </div>
                        <!--Nút hủy bộ lọc-->
                        <div class="col mt-2">
                            <button id="removeCheckedFilterAll" class="button border-0" type="button"
                                    data-action="close-com.lamnguyen.mat_kinh_kimi.filter">
                                <a href="product-booth?<%=request.getAttribute("request")%>&none=none">
                                    <svg class="Icon Icon--close" role="presentation" viewBox="0 0 16 14" width="15"
                                         height="15">
                                        <path d="M15 0L1 14m14 0L1 0" stroke="currentColor" fill="none"
                                              fill-rule="evenodd">

                                        </path>
                                    </svg>
                                </a>
                            </button>
                        </div>
                    </div>
                </div>

                <!--Phần hiển thị danh sách bộ lọc-->
                <!--Các com.lamnguyen.mat_kinh_kimi.filter>ul>li có thể bị ẩn phải có class là hidden-->
                <!--Các com.lamnguyen.mat_kinh_kimi.filter-list nào không có phần hiddent thì xóa luôn cái thể xem com.lamnguyen.mat_kinh_kimi.filter-more luôn nha-->
                <div class="filter-body">
                    <!--Các trường lọc-->
                    <div class="filter">
                        <h4>Thương hiệu</h4>
                        <ul class="filter-list">
                            <%
                                List<String> brands = (List<String>) request.getAttribute("brands");
                                for (int indexBrands = 0; indexBrands < brands.size(); indexBrands++) {
                            %>
                            <li class="<%=indexBrands > 4 ?"d-none hidden" : ""%>">
                                <button class="border-0"
                                        value="<%=brands.get(indexBrands)%>">
                                    <a href="product-booth?<%=request.getAttribute("request")%>&filter-brand=<%=brands.get(indexBrands)%>&page=1">
                                        <%=brands.get(indexBrands)%>
                                    </a>
                                </button>
                            </li>
                            <%}%>
                            <li class="filter-more">
                                <span class="border-0">Xem thêm <i class="fa-solid fa-arrow-right"></i></span>
                            </li>
                        </ul>
                    </div>

                    <!--Các trường lọc chất liệu-->
                    <div class="filter">
                        <h4>Chất liệu</h4>
                        <ul class="filter-list">
                            <%
                                List<String> materials = (List<String>) request.getAttribute("materials");
                                for (int indexMaterial = 0; indexMaterial < materials.size(); indexMaterial++) {
                            %>
                            <li class="<%=indexMaterial > 4 ?"d-none hidden" : ""%>">
                                <button class="border-0"
                                        value="<%=materials.get(indexMaterial)%>">
                                    <a href="product-booth?<%=request.getAttribute("request")%>&filter-material=<%=materials.get(indexMaterial)%>&page=1">
                                        <%=materials.get(indexMaterial)%>
                                    </a>
                                </button>
                            </li>
                            <%}%>
                            <li class="filter-more">
                                <span class="border-0">Xem thêm <i class="fa-solid fa-arrow-right"></i></span>
                            </li>
                        </ul>
                    </div>

                    <!--Các trường lọc Giá-->
                    <div class="filter">
                        <h4>Giá sản phẩm</h4>
                        <ul class="filter-list">
                            <li>
                                <button class="border-0" value="0-220000">
                                    <a href="<%=response.encodeURL("product-booth?" + request.getAttribute("request") + "&com.lamnguyen.mat_kinh_kimi.filter-price=0-220000&page=1")%>">
                                        0 - 220,000
                                    </a>
                                </button>
                            </li>
                            <li>
                                <button class="border-0" value="220000-500000">
                                    <a href="<%=response.encodeURL("product-booth?" + request.getAttribute("request") + "&com.lamnguyen.mat_kinh_kimi.filter-price=220000-500000&page=1")%>">
                                        220,000 - 500,000
                                    </a>
                                </button>
                            </li>
                            <li>
                                <button class="border-0" value="500000-1000000">
                                    <a href="<%=response.encodeURL("product-booth?" + request.getAttribute("request") + "&com.lamnguyen.mat_kinh_kimi.filter-price=500000-1000000&page=1")%>">
                                        500,000 - 1,000,000
                                    </a>
                                </button>
                            </li>
                            <li>
                                <button class="border-0" value="1000000-2000000">
                                    <a href="<%=response.encodeURL("product-booth?" + request.getAttribute("request") + "&com.lamnguyen.mat_kinh_kimi.filter-price=1000000-2000000&page=1")%>">
                                        1,000,000 - 2,000,000
                                    </a>
                                </button>
                            </li>
                            <li class="d-none hidden">
                                <button class="border-0" value="2000000-3000000">
                                    <a href="<%=response.encodeURL("product-booth?" + request.getAttribute("request") + "&com.lamnguyen.mat_kinh_kimi.filter-price=2000000-3000000&page=1")%>">
                                        2,000,000 - 3,000,000
                                    </a>
                                </button>
                            </li>
                            <li class="d-none hidden">
                                <button class="border-0" value="3000000-5000000">
                                    <a href="<%=response.encodeURL("product-booth?" + request.getAttribute("request") + "&com.lamnguyen.mat_kinh_kimi.filter-price=3000000-5000000&page=1")%>">
                                        3,000,000 - 5,000,000
                                    </a>
                                </button>
                            </li>
                            <li class="d-none hidden">
                                <button class="border-0" value="5000000-10000000">
                                    <a href="<%=response.encodeURL("product-booth?" + request.getAttribute("request") + "&com.lamnguyen.mat_kinh_kimi.filter-price=5000000-10000000&page=1")%>">
                                        5,000,000 - 10,000,000
                                    </a>
                                </button>
                            </li>
                            <li class="filter-more">
                                <span class="border-0">Xem thêm <i class="fa-solid fa-arrow-right"></i></span>
                            </li>
                        </ul>
                    </div>

                    <!--Các trường lọc Kiểu-->
                    <div class="filter">
                        <h4>Kiểu dáng</h4>
                        <ul class="filter-list">
                            <%
                                List<String> types = (List<String>) request.getAttribute("types");
                                for (int indexType = 0; indexType < types.size(); indexType++) {
                            %>
                            <li class="<%=indexType > 4 ? "d-none hidden" : ""%>">
                                <button class="border-0"
                                        value="<%=types.get(indexType)%>">
                                    <a href="product-booth?<%=request.getAttribute("request")%>&filter-type=<%=types.get(indexType)%>&page=1">
                                        <%=types.get(indexType)%>
                                    </a>
                                </button>
                            </li>
                            <%}%>

                            <li class="filter-more">
                                <span class="border-0">Xem thêm <i class="fa-solid fa-arrow-right"></i></span>
                            </li>
                        </ul>
                    </div>
                </div>
            </section>
            <!--end com.lamnguyen.mat_kinh_kimi.filter-->

            <!--phần sort-->
            <section class="collection-wrap-sort">
                <!--Phần title-->
                <div class="sort-header">
                    <div class="section-title-all">
                        <h2 class="fs-4">Sắp xếp <i class="lnil lni-plus"></i></h2>
                    </div>
                </div>

                <!--Phần thân-->
                <div class="sort-body">
                    <div class="sort">
                        <h4>Sắp xếp theo</h4>
                        <!--Phần danh sách các tiêu chí sort-->
                        <ul class="sort-list">
                            <li>
                                <button class="border-0" id="sort-price-decs">
                                    <a href="product-booth?<%=request.getAttribute("request")%>&sort-price=decs">
                                        Giá:Tăng dần
                                    </a>
                                </button>
                            </li>
                            <li>
                                <button class="border-0" id="sort-price-asc">
                                    <a href="product-booth?<%=request.getAttribute("request")%>&sort-price=asc">
                                        Giá: Giảm dần
                                    </a>
                                </button>
                            </li>
                            <li>
                                <button class="border-0" id="sort-name-decs">
                                    <a href="product-booth?" <%=request.getAttribute("request")%>&sort-name=decs">
                                        Tên: A-Z
                                    </a>
                                </button>
                            </li>
                            <li>
                                <button class="border-0" id="sort-name-asc">
                                    <a href="product-booth?<%=request.getAttribute("request")%>&sort-name=asc">
                                        Tên: Z-A
                                    </a>
                                </button>
                            </li>
                        </ul>
                    </div>
                </div>
            </section>
            <!--end sort-->

            <!--Phần danh sách sản phẩm-->
            <!--Kích thước tối đa là 16 sản phẩm-->
            <!--Nếu thiếu thì tách trang-->
            <!--cấu trúc tên khi tách trang: ví dụ tách trang kinh_mat.jsp (index bắt đầu là 02, 03, 04, ....)-->
            <!--kinh_mat_page_02.jsp-->
            <section class="collection-wrap-product-list mt-5">
                <div class="title mb-3">
                    <h3><%=request.getAttribute("title")%>
                    </h3>
                </div>

                <!--hiển thị danh sách sản phẩm-->
                <div class="row row-cols-xl-4 row-cols-lg-3 row-cols-md-2 row-cols-sm-1 mb-4 d-flex justify-content-center">
                    <%
                        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        products = products == null ? new ArrayList<Product>() : products;
                        for (Product product : products) {
                    %>
                    <!--Ô hiển thị-->
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
                                <span class="pro-loop-vendor d-block"><%=product.getBrandName()%></span>
                            </div>

                            <!--Hiển thị tên sản phẩm-->
                            <h3 class="pro-loop-name text-center">
                                <a href="more_info_product?id=<%=product.getId()%>"
                                   title="<%=product.getName()%>"><%=product.getName()%>
                                </a>
                            </h3>

                            <!--hiển thị giá-->
                            <div class="pro-loop-price text-center mt-0">
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

                            <!--Hiển thị đánh giá và số lượng bán-->
                            <div class="sold_qty text-center">
                                <!--Phần đánh giá sao-->
                                <div class="prod-review-loop   d-inline-block">
                                    <!--Danh sách ngôi sao-->
                                    <div class="onirvapp--shape-container d-inline-block">
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
                                                for (; index < 5; index++) {
                                            %>
                                            <li>
                                                <i class="fa-regular fa-star" style="color: #fdd836;"></i>
                                            </li>
                                            <%
                                                }
                                            %>
                                        </ul>
                                    </div>

                                    <!--số lượng đánh giá-->
                                    <span class="onireviewapp-loopitem-title">(<%=product.getTotalReview()%> đánh giá)</span>
                                </div>

                                <!--Đường cắt ngang-->
                                <span class="h-line d-inline-block"></span>

                                <!--Phần hiển thị số lượng đã bán-->
                                <div class="sold_qty_num  d-inline-block">
                                    <p class="m-0">
                                        Đã bán: <span><%=product.getTotalQuantitySold()%></span>
                                    </p>
                                </div>
                            </div>

                            <!--2 nút thao tác-->
                            <div class="pro-loop-bottom">
                                <button type="button" product-id="<%=product.getId()%>"
                                        class="f-button setAddCartLoop <%=product.available() ? "show-models" : ""%>"
                                        data-type="show-models">
                                    Xem nhanh
                                </button>
                                <button type="button" product-id="<%=product.getId()%>"
                                        class="f-button setBuyNow <%=product.available() ? "show-models" : ""%>"
                                        data-type="buy-now" data-id="">
                                    Mua ngay
                                </button>
                            </div>
                        </div>
                    </div>
                    <!--End 1 ô sản phẩm-->
                    <%}%>
                </div>
            </section>
            <!--end hiển thị danh sách sản phẩm-->

            <!--Phần chuyển trang (<- 1 2 3 ... ->)-->
            <section id="navigation_change_page">
                <nav class="content-footer">
                    <ul class="pagination d-flex justify-content-center">
                        <%
                            int totalPage = Integer.parseInt(String.valueOf(request.getAttribute("total-page"))),
                                    currentPage = Integer.parseInt(String.valueOf(request.getAttribute("page"))),
                                    indexPage = 1;
                            if (totalPage != 1) {
                        %>
                        <%if (currentPage != 1) {%>
                        <li>
                            <a href="product-booth?<%=request.getAttribute("request")%>&page=1">
                                <button class="d-flex align-items-center justify-content-center">
                                    <span class="material-symbols-outlined">keyboard_double_arrow_left</span>
                                </button>
                            </a>
                        </li>
                        <li>
                            <a href="product-booth?<%=request.getAttribute("request")%>&page=<%=currentPage-1%>">
                                <button id="prev" class="d-flex align-items-center justify-content-center">
                                    <span class="material-symbols-outlined">chevron_left</span>
                                </button>
                            </a>
                        </li>
                        <%}%>
                        <%
                            for (indexPage = currentPage - 2; indexPage < currentPage; indexPage++) {
                                if (indexPage > 0) {
                        %>
                        <li>
                            <a href="product-booth?<%=request.getAttribute("request")%>&page=<%=indexPage%>">
                                <button class="d-flex align-items-center justify-content-center button-number"
                                        data-target="<%=indexPage%>">
                                    <%=indexPage%>
                                </button>
                            </a>
                        </li>
                        <%
                                }
                            }
                        %>
                        <%for (indexPage = currentPage; indexPage <= totalPage && (indexPage - currentPage) < (4 - ((currentPage - 3) > 0 ? 0 : (currentPage - 3))); indexPage++) {%>
                        <li class="<%=currentPage == indexPage ? "active" : ""%>">
                            <a href="product-booth?<%=request.getAttribute("request")%>&page=<%=indexPage%>">
                                <button class="d-flex align-items-center justify-content-center button-number"
                                        data-target="<%=indexPage%>">
                                    <%=indexPage%>
                                </button>
                            </a>
                        </li>
                        <%}%>
                        <%if (totalPage != 0 && currentPage != totalPage) {%>
                        <li>
                            <a href="product-booth?<%=request.getAttribute("request")%>&page=<%=currentPage+1%>">
                                <button id="next" class="d-flex align-items-center justify-content-center">
                                    <span class="material-symbols-outlined">chevron_right</span>
                                </button>
                            </a>
                        </li>
                        <li>
                            <a href="product-booth?<%=request.getAttribute("request")%>&page=<%=totalPage%>">
                                <button class="d-flex align-items-center justify-content-center">
                                    <span class="material-symbols-outlined">keyboard_double_arrow_right</span>
                                </button>
                            </a>
                        </li>
                        <%}%>
                        <%}%>
                    </ul>
                </nav>
            </section>
            <!--End phần chuyển trang  (<- 1 2 3 ... ->)-->
        </div>
    </div>

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
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/gian_hang.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
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

        $(".com.lamnguyen.mat_kinh_kimi.filter-list").find("button").removeClass("checked");
        $(".sort-list").find("button").removeClass("checked");
        <%Map<String, List<String>> mapInfRoot = (Map<String, List<String>>)request.getAttribute("mapInfRoot");
        Map<String, List<String>> mapFilter = (Map<String, List<String>>)request.getAttribute("mapFilter");
        Map<String, String> mapSort = (Map<String, String>)request.getAttribute("mapSort");
        for(List<String> values : mapFilter.values()){
            for(String id : values){%>
        $(`button[value="<%=id%>"]`).addClass("checked");
        <%}};
        for(Map.Entry<String, String> id : mapSort.entrySet()){%>
        $("#<%=id.getKey()%>-<%=id.getValue()%>").addClass("checked");
        <%}%>

        $("a[id-category-group=<%=mapInfRoot.get("id-category-group")%>]").addClass("active")
    });
</script>
</body>
</html>