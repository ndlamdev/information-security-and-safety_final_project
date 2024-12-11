<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) session.getAttribute("logo"); %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="../css/menu_footer.css">
    <link rel="stylesheet" href="../css/admin_pages.css">
    <link rel="stylesheet" href="../css/danh_sach_slider.css">
    <link rel="icon" href="../<%=logo.getUrlImage()%>">

    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>

    <title>Quản lý banner</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div id="main" class="mt-5 pb-5">
    <div class="container">
        <div class="frame-boder row p-3 my-5">
            <div class="title"><span>Danh sách hình ảnh trong thanh trượt</span></div>
            <div class="col-10">
                <div class="edit-img row row-cols-2" id="show-slides">
                    <%
                        BannerImage loginBanner = (BannerImage) request.getAttribute("bannerLoginImages");
                        BannerImage signupBanner = (BannerImage) request.getAttribute("bannerSignupImages");
                        BannerImage prBanner = (BannerImage) request.getAttribute("bannerPRImages");
                        BannerImage contactBanner = (BannerImage) request.getAttribute("bannerContactImages");
                        BannerImage authBanner = (BannerImage) request.getAttribute("bannerAuthImages");
                        List<BannerImage> urls = (List<BannerImage>) request.getAttribute("bannerImages");
                        for (BannerImage ri : urls) {
                    %>
                    <div class="slide-management p-3" slide-id="<%=ri.getDescription()%>">
                        <div class="item-img col">
                            <img class="img-fluid" data-banner="<%=ri.getDescription()%>" src="../<%=ri.getUrlImage()%>"
                                 alt="">
                            <div class="check-box-img">
                                <input class="form-check-input" type="checkbox"
                                       id="check-img-<%=ri.getDescription().substring((6))%>">
                            </div>
                        </div>
                    </div>
                    <% }%>
                </div>
            </div>
            <div class="col-2 p-4">
                <div class="btn-edit-img row row-cols-1 ">
                    <button class="remove-img col" type="button" disabled>
                        <span><i class="fa-solid fa-trash"></i></span>
                        <span class="px-2">Xóa</span>
                    </button>
                    <button id="select-all-img" class="select-all-img col" type="button"><span><i
                            class="fa-solid fa-check-double"></i></span><span class="px-2">Chọn tất cả</span></button>
                    <label class="add-img col" for="slide-added"><i class="fa-solid fa-arrow-up-from-bracket px-2"></i>Thêm
                        ảnh</label>
                    <form class="add-img " action="upload-file-on-banner-management" method="post"
                          enctype="multipart/form-data">
                        <input id="slide-added" class="form-check-input imageInput " type="file" value=""
                               name="slide-added"
                               accept="image/*" data-preview="slide-added" hidden>
                    </form>
                </div>
            </div>
        </div>
        <%-----------------------------Modal of the remove buttom-----------------------------------%>
        <!-- Button trigger modal -->
        <button type="button" class="btn-show-model btn btn-primary" data-bs-toggle="modal"
                data-bs-target="#removeNoticeModal" hidden></button>

        <!-- Modal -->
        <div class="modal fade" id="removeNoticeModal" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><i
                                class="fa-solid fa-triangle-exclamation px-2"></i>Thông báo</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <span>Bạn có chắc chắn xóa không?</span>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button id="remove-img" type="button" class="btn" data-bs-dismiss="modal"
                                style="color: #fff; background-color: #ff3300">Ok
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <%--end--%>
        <%--        quan ly banner--%>
        <div class="frame-boder frame-banner row p-3">
            <div class="title"><span>Danh sách hình ảnh hiện thị</span></div>
            <div class="row">
                <div class="edit-img row row-cols-2">
                    <div class="p-3">
                        <div class="item-img col">
                            <img class=" img-fluid" data-banner="banner-login" id="login-img"
                                 src="../<%=loginBanner.getUrlImage()%>" alt="">
                            <div class="text-banner"><span>Đăng nhập</span></div>
                            <form class="upload-img" action="upload-file-on-banner-management" method="post"
                                  enctype="multipart/form-data">
                                <input class="form-check-input imageInput" type="file" value="" name="banner-login"
                                       id="banner-login" accept="image/*" data-preview="login-img"
                                       style="height: 100px; width: 100%" hidden>
                                <label for="banner-login"><i class="fa-solid fa-arrow-up-from-bracket"></i></label>
                            </form>
                        </div>
                    </div>

                    <div class="p-3">
                        <div class="item-img col">
                            <img class=" img-fluid z-0 " data-banner="banner-signup" id="signup-img"
                                 src="../<%=signupBanner.getUrlImage()%>" alt="">
                            <div class="text-banner"><span>Đăng ký</span></div>
                            <form class="upload-img" action="upload-file-on-banner-management" method="post"
                                  enctype="multipart/form-data">
                                <input class="form-check-input imageInput " type="file" value="" name="banner-signup"
                                       id="banner-signup" accept="image/*" data-preview="signup-img" hidden>
                                <label for="banner-signup"><i class="fa-solid fa-arrow-up-from-bracket"></i></label>
                            </form>
                        </div>
                    </div>
                    <div class="p-3">
                        <div class="item-img col">
                            <img class=" img-fluid z-0 " data-banner="banner-pr" id="pr-img"
                                 src="../<%=prBanner.getUrlImage()%>" alt="">
                            <div class="text-banner"><span>Quảng cáo</span></div>
                            <form class="upload-img" action="upload-file-on-banner-management" method="post"
                                  enctype="multipart/form-data">
                                <input class="form-check-input imageInput " type="file" value="" name="banner-pr"
                                       id="banner-pr"
                                       accept="image/*" data-preview="pr-img" hidden>
                                <label for="banner-pr"><i class="fa-solid fa-arrow-up-from-bracket"></i></label>
                            </form>
                        </div>
                    </div>
                    <div class="p-3">
                        <div class="item-img col">
                            <img class=" img-fluid z-0" data-banner="banner-logo" id="logo-img"
                                 src="../<%=logo.getUrlImage()%>" alt="">
                            <div class="text-banner"><span>Logo</span></div>
                            <form class="upload-img" action="upload-file-on-banner-management" method="post"
                                  enctype="multipart/form-data">
                                <input class="form-check-input imageInput " type="file" value="" name="banner-logo"
                                       id="banner-logo" accept="image/*" data-preview="logo-img" hidden>
                                <label for="banner-logo"><i class="fa-solid fa-arrow-up-from-bracket"></i></label>
                            </form>
                        </div>
                    </div>
                    <div class="p-3">
                        <div class="item-img col">
                            <img class=" img-fluid z-0" data-banner="banner-contact" id="contact-img"
                                 src="../<%=contactBanner.getUrlImage()%>" alt="">
                            <div class="text-banner"><span>Liên hệ</span></div>
                            <form class="upload-img" action="upload-file-on-banner-management" method="post"
                                  enctype="multipart/form-data">
                                <input class="form-check-input imageInput " type="file" value="" name="banner-contact"
                                       id="banner-contact" accept="image/*" data-preview="contact-img" hidden>
                                <label for="banner-contact"><i class="fa-solid fa-arrow-up-from-bracket"></i></label>
                            </form>
                        </div>
                    </div>
                    <div class="p-3">
                        <div class="item-img col">
                            <img class=" img-fluid z-0" data-banner="banner-auth" id="auth-img"
                                 src="../<%=authBanner.getUrlImage()%>" alt="">
                            <div class="text-banner"><span>Xác thực</span></div>
                            <form class="upload-img" action="upload-file-on-banner-management" method="post"
                                  enctype="multipart/form-data">
                                <input class="form-check-input imageInput " type="file" value="" name="banner-auth"
                                       id="banner-auth" accept="image/*" data-preview="auth-img" hidden>
                                <label for="banner-auth"><i class="fa-solid fa-arrow-up-from-bracket"></i></label>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script src="../javascript/menu_footer.js"></script>
<script src="../javascript/admin_pages/admin_page.js"></script>
<script src="../javascript/admin_pages/danh_sach_slider.js"></script>
<script type="text/javascript">
    $(".admin-menu-item a").eq(4).addClass("active")
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