<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    BannerImage logo = (BannerImage) application.getAttribute("logo");
    BannerImage loginBanner = (BannerImage) application.getAttribute("loginBanner");
%>
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
                        ĐĂNG NHẬP
                    </h5>
                    <div class="login-form-body">
                        <form accept-charset="UTF-8" action="login" id="customer_login" method="post">
                            <input name="form_type" type="hidden" value="customer_login">
                            <input name="utf8" type="hidden" value="✓">

                            <div class="form-group mb-2">
                                <label for="login-email">Email*</label>
                                <input type="text" id="login-email" placeholder="Email" class="form-control"
                                       name="email" required="">
                            </div>
                            <div class="form-group mb-1">
                                <label for="login-password">Mật khẩu*</label>
                                <input type="password" id="login-password" placeholder="Mật khẩu" class="form-control"
                                       name="password" required="">
                            </div>
                            <div class="login-error mt-1">
                                <%
                                    String error = (String) request.getAttribute("login_error");
                                    if (error != null) {
                                %><span class="text-danger"><%=error%></span> <%}%>
                            </div>
                            <div class="mt-1" style="display: flex; justify-content: flex-end;">
                                <button type="button"
                                        style="font-size: 13px; color: blue; background: none; border: none;"
                                        id="forget-password" href="#" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal">Quên mật khẩu
                                </button>
                            </div>
                            <div class="form-group mt-2 d-flex-center">
                                <button type="submit" style="font-size: 13px;" class="btn btn-primary hoverOpacity"
                                        id="signin">
                                    Đăng nhập
                                </button>
                            </div>
                            <p style="text-align: center; font-size: 13px; margin-bottom: 2px;" class="mt-2">Hoặc đăng
                                nhập bằng</p>
                            <p class="text-center">
                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/mat_kinh_kimi/login_google&response_type=code&client_id=908243265697-739bs2s8pbobo6p2mo951ij9t86lsnuf.apps.googleusercontent.com&approval_prompt=force">
                                    <img style="height: 25px; width: 25px" src="images/logo/google_logo.png"
                                         alt="google_logo.png">
                                </a>
                            </p>
                            <p class="mt-2" style="text-align: center; font-size: 13px;">Bạn chưa có tài khoản? <a
                                    style="font-size: 13px; color: blue;" href="dang_ky.jsp">Đăng ký</a></p>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-6 banner-account" style=" background-image: url('<%=loginBanner.getUrlImage()%>');">
            </div>
        </div>
    </div>

    <%
        String error_not_found_email_forget_password = (String) request.getAttribute("error-not-found-email-forget-password");
        String message = (String) session.getAttribute("message");
        String verifyError = (String) request.getAttribute("verify_error");
    %>
    <!-- Modal -->
    <%-- Form quên mật khầu --%>
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Quên mật khẩu</h5>
                    <button type="button" id="close-modal-forget-password" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <div class="modal-body form-forgot">
                    <form accept-charset="UTF-8" action="forget_password" id="customer_forget_password" method="post">
                        <div class="form-group mb-3">
                            <input name="action" type="hidden" value="check_mail">
                            <label for="email">Email<span class="text-danger">*</span></label>
                            <input type="email" id="email" placeholder="Email" class="form-control"
                                   name="email" required="">
                            <%if (error_not_found_email_forget_password != null) {%>
                            <small class="text-danger"><%=error_not_found_email_forget_password%>
                            </small>
                            <%}%>
                        </div>
                        <div class="form-group mt-4 d-flex-center">
                            <button type="submit" style="font-size: 13px;" class="btn btn-primary hoverOpacity ">
                                Quên mật khẩu
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%if (message != null) {%>
    <%--Model thông báo đăng ký hoặc đổi quên mật khẩu thành công--%>
    <button hidden="" type="button" id="show-complete-modal" data-bs-toggle="modal"
            data-bs-target="#complete-modal"></button>
    <div class="modal fade" id="complete-modal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5">Thành công</h1>
                    <button id="close-complete-modal" type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>

                <div class="modal-body position-relative">
                    <div class="d-flex align-items-center justify-content-center">
                        <img style="width: 50px" src="images/icon/complete.png" alt="complete.png">
                        <p class="fs-3 ms-2"><%=message%>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%
        }
        if (verifyError != null) {
    %>
    <button hidden="" type="button" id="show-verify-error-modal" data-bs-toggle="modal"
            data-bs-target="#verify-error-modal"></button>
    <div class="modal fade" id="verify-error-modal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5">Lỗi xác thực</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>

                <div class="modal-body position-relative">
                    <div class="d-flex align-items-centerr">
                        <img style="width: 50px" src="images/icon/verify_error.png" alt="verify_error.png">
                        <p class="ms-2 fs-6"><%=verifyError%>
                        </p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <form accept-charset="UTF-8" action="re_send_code_verify" method="post">
                        <input type="hidden" name="fullName" value="<%=request.getAttribute("fullName")%>">
                        <input type="hidden" name="email" value="<%=request.getAttribute("email")%>">
                        <input type="hidden" name="action" value="<%=request.getAttribute("action")%>">
                        <button type="submit" class="btn btn-primary">Gửi mã xác nhận</button>
                    </form>

                </div>
            </div>
        </div>
    </div>
    <%}%>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
<script>
    <%if(error_not_found_email_forget_password != null) {%>
    $("#forget-password").click();
    <%
     session.removeAttribute("error-not-found-email-forget-password");
    }
    if(message != null){%>
    $("#show-complete-modal").click();
    <%
      session.removeAttribute("message");
    }
    if (verifyError != null) {%>
    $("#show-verify-error-modal").click();
    <%
    session.removeAttribute("verify_error");
    }%>
</script>
</body>
</html>