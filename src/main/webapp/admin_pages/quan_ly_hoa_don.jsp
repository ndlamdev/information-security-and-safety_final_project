<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum" %>
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
    <link rel="stylesheet" href="../css/danh_sach_hoa_don.css">
    <link rel="icon" href="../<%=logo.getUrlImage()%>">

    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>

    <title>Quản lý hóa đơn</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div id="main" class="mt-5 pb-5">
    <div class="container ">
        <!--        thanh tìm kiếm-->
        <div class="search pb-4">
            <div class="search_header ms-4">
                <span>Tìm kiếm</span>
            </div>

            <div class="row text-center option-search">
                <div class="search-bill col">
                    <div class="search-item rounded">
                        <input class="ps-2" type="text" name="bill-id" id="bill-id"
                               placeholder="Nhập mã hóa đơn">
                        <button class="d-flex align-items-center p-1 input-search d-inline-block border-0"
                                style="background: none"><span
                                class="material-symbols-outlined ps-1 fs-3">search</span></button>
                    </div>
                </div>
                <div class="search-customer col">
                    <div class="search-item rounded">
                        <input class="ps-2" type="text" name="customer-name" id="customer-name"
                               placeholder="Nhập tên khách hàng">
                        <button for="customer-name"
                                class="d-flex align-items-center p-1 input-search  d-inline-block border-0"
                                style="background: none"><span
                                class="material-symbols-outlined ps-1 fs-3">search</span></button>
                    </div>
                </div>
                <div class="col float-start d-flex align-items-center">
                    <label for="status"></label>
                    <select name="status" id="status" class="ps-1 py-2 rounded w-50 select-search">
                        <option value="">Tất cả</option>
                        <%for (BillStatusEnum.BillStatusJson status : (List<BillStatusEnum.BillStatusJson>) request.getAttribute("statuses")) {%>
                        <option value="<%=status.getStatus()%>">
                            <%=status.getStatus()%>
                        </option>
                        <%}%>
                    </select>
                </div>
            </div>
        </div>
        <div class="show-list-bill p-0">
            <!-- tên cột -->
            <div class="name-col row  align-items-center ps-4">
                <div class="col-2">Mã hóa đơn</div>
                <div class="col-2">Thời gian đặt</div>
                <div class="col-3">Thông tin khách hàng</div>
                <div class="col-2">Trạng thái</div>
                <div class="col-2">Hình thức thanh toán</div>
                <div class="col-1 emty"></div>
            </div>
            <!-- hiện thị danh sách sản phẩm-->
            <div class="list-bill" id="display-bills">
               <%-- <!-- bắt đầu một hàng hiện thị sản phẩm-->
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
                    <div class="option-edit col-1"><a href="chi_tiet_hoa_don.jsp"><i class="fa-solid fa-eye"></i></a>
                    </div>
                </div>
                <!-- kết thúc một hàng hiện thị sản phẩm-->--%>
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

<jsp:include page="footer.jsp"/>

<script src="../javascript/menu_footer.js"></script>
<script src="../javascript/admin_pages/admin_page.js"></script>
<script src="../javascript/admin_pages/danh_sach_hoa_don.js"></script>
<script type="text/javascript">
    $(".admin-menu-item a").eq(3).addClass("active")
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