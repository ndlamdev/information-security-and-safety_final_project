<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    BannerImage logo = (BannerImage) session.getAttribute("logo");
    BannerImage auth = (BannerImage) session.getAttribute("authBanner");
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
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/xac_thuc.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Xác thực</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<main id="main" class="mt-5 pb-5">
    <div class="container row px-5">
        <div class="verify col-6 ps-5">
            <div class="verify-header mb-3">
                <%
                    String email = (String) session.getAttribute("email"),
                            message = (String) session.getAttribute("message"),
                            fullName = (String) session.getAttribute("fullName"),
                            action = (String) session.getAttribute("action");
                %>
                <h2 class="mb-1">Vui lòng xác thực tài khoản email: <br>
                    <a class="text-primary text-decoration-underline" href="https://mail.google.com/"
                       target="_blank"><span><%=email%></span></a>của bạn!</h2>
                <%if (message != null) {%>
                <small class="text-danger mt-2"><%=message%>
                </small>
                <%}%>
                <form action="re_send_code_verify" method="post">
                    <%if (fullName != null) {%>
                    <input type="hidden" name="fullName" value="<%=fullName%>">
                    <%}%>
                    <input type="hidden" name="email" value="<%=email%>">
                    <input type="hidden" name="action" value="<%=action%>">
                    <input hidden="" id="re-send" class="bg-primary text-white px-3 py-1 border-0 rounded-2"
                           type="submit" value="Gửi lại">
                </form>
            </div>
        </div>
        <div class="col-6 pe-5">
            <img src="<%=auth.getUrlImage()%>" alt="background" class="rounded-2">
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/index.js"></script>
<script>
    setTimeout(function () {
        $("#re-send").removeAttr("hidden");
    }, 600000);
</script>
</body>
</html>