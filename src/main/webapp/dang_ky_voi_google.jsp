<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    BannerImage logo = (BannerImage) application.getAttribute("logo");
    BannerImage signupBanner = (BannerImage) application.getAttribute("signupBanner");
%>
<%User user = (User) request.getAttribute("user");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="css/dang_nhap_va_dang_ky.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Đăng ký</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<main class="my-4 main-login">
    <div class="modal-body form-login">
        <div class="row">
            <div class="col-6">
                <div class="tab-pane fade active show" id="modalLogin" role="tabpanel" aria-labelledby="modalLoginTab">
                    <h5 class="text-center mb-2">
                        ĐĂNG KÝ
                    </h5>
                    <div class="login-form-body">
                        <form accept-charset="UTF-8" action="signup_with_google" id="customer_login" method="post">
                            <input name="utf8" type="hidden" value="✓">
                            <div class="form-group mb-3">
                                <label for="signup-fullName">Họ và tên*</label>
                                <input type="text" id="signup-fullName" placeholder="Họ và tên" class="form-control"
                                       name="customer[fullName]" required="">
                            </div>
                            <div class="form-group mb-3">
                                <div class="row pb-1">
                                    <div class="col-5"><label>Giới tính</label></div>
                                    <div class="col p-0"><label>Ngày sinh</label></div>
                                </div>

                                <div class="row">
                                    <div class="col-2 mt-1">
                                        <input type="radio" id="register-gender-0" value="Nữ" name="customer[gender]"
                                               checked="">
                                        <label for="register-gender-0">Nữ</label>
                                    </div>
                                    <div class="col-3 mt-1">
                                        <input type="radio" id="register-gender-1" value="Nam" name="customer[gender]"
                                               data-gtm-form-interact-field-id="0">
                                        <label for="register-gender-1">Nam</label>
                                    </div>

                                    <div class="ngay_sinh col-7 p-0 d-flex">
                                        <input class="col-2  p-1" type="number" name="day" id="day" placeholder="DD">
                                        <select class="col-5 p-1" name="month" id="month">
                                            <option value="1">Tháng 1</option>
                                            <option value="2">Tháng 2</option>
                                            <option value="3">Tháng 3</option>
                                            <option value="4">Tháng 4</option>
                                            <option value="5">Tháng 5</option>
                                            <option value="6">Tháng 6</option>
                                            <option value="7">Tháng 7</option>
                                            <option value="8">Tháng 8</option>
                                            <option value="9">Tháng 9</option>
                                            <option value="10">Tháng 10</option>
                                            <option value="11">Tháng 11</option>
                                            <option value="12">Tháng 12</option>
                                        </select>
                                        <input class="col-4  p-1" type="number" name="year" id="year"
                                               placeholder="YYYY">
                                    </div>
                                    <%String error_birthday = (String) request.getAttribute("signup_error_birthday");%>
                                    <%
                                        if (error_birthday != null) {
                                    %>
                                    <div class="error_birthday text-end">
                                        <small class="text-danger "><%=error_birthday%>
                                        </small>
                                    </div>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                            <div class="form-group mb-3 d-none">
                                <label for="signup-email">Email*</label>
                                <input type="email" id="signup-email" placeholder="Email" class="form-control"
                                       name="customer[email]" value="<%=user.getEmail()%>" required="">
                                <%String error_email = (String) request.getAttribute("signup_error_email");%>
                                <%
                                    if (error_email != null) {
                                %>
                                <small style="color: red"><%=error_email%>
                                </small>
                                <%
                                    }
                                %>
                            </div>
                            <div class="form-group  mb-3">
                                <label for="signup-password">Mật khẩu*</label>
                                <input type="password" id="signup-password" placeholder="Mật khẩu" class="form-control"
                                       name="customer[password]" required="">
                                <%String error_pass = (String) request.getAttribute("signup_error_pass");%>
                                <%
                                    if (error_pass != null) {
                                %>
                                <small style="color: red"><%=error_pass%>
                                </small>
                                <%
                                    }
                                %>
                            </div>
                            <div class="form-group ">
                                <label for="signup-repassword">Nhập mật khẩu*</label>
                                <input type="password" id="signup-repassword" placeholder="Nhập lại mật khẩu"
                                       class="form-control" name="customer[repassword]" required="">
                                <%
                                    if (error_pass != null) {
                                %>
                                <small style="color: red"><%=error_pass%>
                                </small>
                                <%
                                    }
                                %>
                            </div>

                            <div class="form-group mt-2 d-flex-center">
                                <button type="submit" style="font-size: 13px;" class="btn btn-primary hoverOpacity ">
                                    Đăng ký
                                </button>
                            </div>
                            <p style="text-align: center; font-size: 13px; margin-bottom: 2px;" class="mt-2">Hoặc</p>
                            <p style="text-align: center; font-size: 13px;">Bạn đã có tài khoản? <a
                                    style="font-size: 13px; color: blue;" href="dang_nhap.jsp">Đăng nhập</a></p>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-6 banner-account" style="background-image: url('<%=signupBanner.getUrlImage()%>');">
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/dang_ky.js"></script>
</body>
</html>