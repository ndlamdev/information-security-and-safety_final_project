<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) application.getAttribute("logo"); %>
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
    <link rel="stylesheet" href="../css/thong_the.css">
    <link href="../select2/select2.min.css" rel="stylesheet"/>
    <link rel="icon" href="../<%=logo.getUrlImage()%>">

    <%--jquery--%>
    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>

    <%--Select 2--%>
    <script src="../select2/select2.min.js"></script>

    <%--notify--%>
    <script src="../notify/notify.js"></script>
    <title>Thống kê</title>

    <%--chart--%>
    <script src="../chartjs/dist/chart.js"></script>
    <script src="../chartjs/dist/helpers.js"></script>
    <script src="../chartjs/dist/chart.umd.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

<main id="main" class=" mt-5 pb-5">
    <div class="container">
        <section>
            <div class="search pb-4">
                <div class="search_header ms-4">
                    <span>Bộ lọc doanh số theo doanh mục và nhóm danh mục</span>
                </div>

                <div class="option-search row">
                    <div class="col-3">
                        <div class="search-item rounded w-75">
                            <select class="number-list-by-category py-2 rounded w-100 border-0" name="input-category"
                                    id="input-category">
                                <option value="0" selected>Nhóm danh mục</option>
                                <option value="1">Danh mục</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-3">
                        <select class="number-list-by-category py-2 rounded w-75" name="input-quarter"
                                id="input-category-quarter">
                            <option value="1" selected>Theo quý</option>
                            <option value="0">Theo tháng</option>
                        </select>
                    </div>
                    <div class="col-3">
                        <select class="number-list-by-category py-2 rounded w-75" name="input-year"
                                id="input-category-year">
                        </select>
                    </div>
                </div>
            </div>
            <canvas id="chart-category"></canvas>
        </section>
        <section class="mt-5">
            <div class="search pb-4">
                <div class="search_header ms-4">
                    <span>Bộ lọc doanh số theo sản phẩm</span>
                </div>

                <div class="option-search row">
                    <div class="col-3">
                        <div class="number-list-by-product search-item rounded w-75">
                            <select class="py-2 rounded w-100 border-0" name="input-product" id="input-product">
                            </select>
                        </div>
                    </div>
                    <div class="col-3">
                        <select class="number-list-by-product py-2 rounded w-75" name="input-month"
                                id="input-product-month">
                        </select>
                    </div>
                    <div class="col-3">
                        <select class="number-list-by-product py-2 rounded w-75" name="input-year"
                                id="input-product-year">
                        </select>
                    </div>
                </div>
            </div>
            <canvas id="chart-product"></canvas>
        </section>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="../javascript/menu_footer.js"></script>
<script src="../javascript/admin_pages/admin_page.js"></script>
<script src="../javascript/admin_pages/dashboard.js"></script>

<script type="text/javascript">
    $(".admin-menu-item a").eq(0).addClass("active")
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