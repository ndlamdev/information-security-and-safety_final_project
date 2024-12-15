<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) application.getAttribute("logo");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="../css/menu_footer.css">
    <link rel="stylesheet" href="../css/danh_sach_tai_khoan.css">
    <link rel="icon" href="../<%=logo.getUrlImage()%>">

    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>

    <title>Quản lý tài khoản</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<main id="main" class=" mt-5 pb-5">
    <div class="container">
        <div class="search pb-4">
            <div class="search_header ms-4">
                <span>Tìm kiếm</span>
            </div>

            <div class="option-search row">
                <div class="col-3">
                    <div class="search-item rounded">
                        <input type="text" name="search-account-id search" id="search-account-id"
                               placeholder="Nhập mã tài khoản">
                        <label for="search-account-id" class="d-flex align-items-center p-1"><span
                                class="material-symbols-outlined ps-1 fs-3">search</span></label>
                    </div>
                </div>
                <div class="col-3">
                    <div class="search-item rounded">
                        <input type="text" name="search-account-name search" id="search-account-name"
                               placeholder="Nhập tên tài khoản">
                        <label for="search-account-name" class="d-flex align-items-center p-1"><span
                                class="material-symbols-outlined ps-1 fs-3">search</span></label>
                    </div>
                </div>
                <div class="col-3">
                    <select class="py-2 rounded w-75 search" name="input-account-role" id="input-account-role">
                        <option value="-1" selected>Tất cả</option>
                        <option value="1">Tài khoản</option>
                        <option value="0">Admin</option>
                        <option value="2">Shipper</option>
                    </select>
                </div>
                <div class="col-3">
                    <select class="py-2 rounded w-75 search" name="input-lock-account" id="input-lock-account">
                        <option value="-1" selected>Tất cả</option>
                        <option value="1">Đã khóa</option>
                        <option value="0">Không khóa</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="table">
            <div class="header-table row align-items-center ps-4">
                <div class="col-2">Mã tài khoản</div>
                <div class="col-4">Tài khoản</div>
                <div class="col-1 text-center">Giới tính</div>
                <div class="col-1 text-center">Tổng đơn hàng đặt</div>
                <div class="col-2 text-center">Tổng chi</div>
                <div class="col-1 text-center">Vai trò</div>
                <div class="col-1 text-center">Khóa tài khoản</div>
            </div>
            <div id="table-body">
                <div class="body-table">
                </div>
            </div>


            <!--Phần footer, coppy-->
            <div class="footer-table row p-4 d-flex align-items-center">
                <div class="text-amount-account col-8 ">
                    <span class="ps-0 pe-0">Tổng số tài khoản: </span>
                    <span class="amount ps-0 pe-0" id="count_user">0</span>
                    <span class="ps-0 pe-0"> tài khoản</span>
                </div>
                <div class="change-page-display-list col-4 d-flex ps-5 pagination_account">
                    <%--<button id="prev" class="d-flex align-items-center justify-content-center"><span
                            class="material-symbols-outlined">chevron_left</span></button>
                    &lt;%&ndash;<button class="d-flex align-items-center justify-content-center button-number active"
                            data-target="1">1
                    </button>&ndash;%&gt;

                    <button id="next" class="d-flex align-items-center justify-content-center"><span
                            class="material-symbols-outlined">chevron_right</span></button>--%>
                </div>
            </div>
            <!--kết thúc footer-->
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="../javascript/menu_footer.js"></script>
<script src="../javascript/admin_pages/admin_page.js"></script>
<script src="../javascript/admin_pages/danh_sach_tai_khoan.js"></script>
<script type="text/javascript">
    $(".admin-menu-item a").eq(1).addClass("active")
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