<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.Bill" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) session.getAttribute("logo");%>
<%
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
    Bill bill = (Bill) session.getAttribute("billPayed");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String formattedDateTime = bill.getStatuses().get(0).getDate().format(formatter);
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
    <link rel="stylesheet" href="css/thanh_toan_thanh_cong.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Thanh toán thành công</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<main id="thanh-toan-thanh-cong" class="p-md-5">
    <div class="tttv-frame">
        <div class="container">
            <div class="tttc-top text-center">
                <h3 class="py-2">Thanh toán thành công</h3>
                <div class="icon-tttc py-2">
                    <i class="fa-regular fa-circle-check"></i>
                </div>
                <div class="time-tttc">
                    <p>
                        <span><%=formattedDateTime%></span>
                    </p>
                </div>
            </div>
            <div class="tttc-bot">
                <div class="ma-giao-dich">
                    <span class="left">Mã đơn hàng</span>
                    <span class="right">#<%=bill.getId()%></span>
                </div>
                <div class="username">
                    <span class="left">Tên</span>
                    <span class="right"><%=bill.getUserName()%></span>
                </div>
                <div class="phone-number">
                    <span class="left">Số điện thoại</span>
                    <span class="right"><%=bill.getPhoneNumber()%></span>
                </div>
                <div class="email">
                    <span class="left">Email</span>
                    <span class="right"><%=bill.getEmail()%></span>
                </div>
                <div class="address">
                    <span class="left">Địa chỉ</span>
                    <span class="right text-end"><%=request.getAttribute("addressDetails")%></span>
                </div>
                <div class="phuong-thuc-thanh-toan">
                    <span class="left">Phương thức thanh toán</span>
                    <% if (!bill.isTransfer()) { %>
                    <span class="right">Thanh toán trực tiếp</span>
                    <%} else {%>
                    <span class="right">Chuyển khoản</span>
                    <%}%>
                </div>
                <div class="so-tien">
                    <span class="left">Tổng số tiền</span>
                    <span class="right"><%=nf.format(bill.totalBill())%></span>
                </div>
            </div>
            <div class="btn-tttc py-md-5">
                <a href="index.jsp" class="come-home" type="button"><i class="fa-solid fa-house px-2"></i>Quay về trang
                    chủ</a>
                <a href="chi_tiet_hoa_don.jsp" class="check-bill" type="button"><i
                        class="fa-solid fa-file-circle-check px-2"></i>Kiểm tra
                    đơn hàng</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
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