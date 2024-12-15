<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) application.getAttribute("logo");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="css/dang_nhap_va_dang_ky.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Đăng nhập</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<main class="my-4 main-login">
    <div class="modal-body form-login">
        <div class="row">
            <div class="col-6">
                <div class="tab-pane fade active show" id="modalLogin" role="tabpanel" aria-labelledby="modalLoginTab">
                    <h5 class="text-center mb-2">
                        Đặt lại mật khẩu
                    </h5>
                    <div class="login-form-body">
                        <%String email = (String) session.getAttribute("email");%>
                        <form accept-charset="UTF-8" action="forget_password" id="customer_login" method="post">
                            <input name="action" type="hidden" value="change_password">
                            <input name="email" type="hidden" value="<%=email%>">
                            <div class="form-group mb-2">
                                <label for="password">Mật khẩu<span class="text-danger">*</span></label>
                                <input type="password" id="password" placeholder="Mật khẩu" class="form-control"
                                       name="password" required="">
                            </div>
                            <div class="form-group mb-2">
                                <label for="re-password">Nhập lại mật khẩu<span class="text-danger">*</span></label>
                                <input type="password" id="re-password" placeholder="Nhập lại mật khẩu"
                                       class="form-control" name="re-password" required="">
                            </div>
                            <%
                                String error = (String) request.getAttribute("re-password-error");
                                if (error != null) {%>
                            <span class="text-danger"><%=error%></span>
                            <%}%>
                            <div class="form-group mt-2 d-flex-center">
                                <button type="submit" style="font-size: 13px; background:  #ff3300; color: #fff"
                                        class="btn btn-primary hoverOpacity px-4 border-0">
                                    Hoàn tất
                                </button>
                            </div>

                            <div class="form-group mt-3 d-flex-center">
                                <a href="dang_nhap.jsp">
                                    <button type="button" style="font-size: 13px;" class="btn btn-primary hoverOpacity"
                                            id="signin">
                                        Đăng nhập
                                    </button>
                                </a>
                            </div>
                            <p style="text-align: center; font-size: 13px; margin-bottom: 2px;" class="mt-2">Hoặc</p>
                            <p style="text-align: center; font-size: 13px;">Bạn chưa có tài khoản? <a
                                    style="font-size: 13px; color: blue;" href="dang_ky.jsp">Đăng ký</a></p>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-6 banner-account">
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
</body>
</html>