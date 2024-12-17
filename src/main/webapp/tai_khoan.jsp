<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) application.getAttribute("logo");%>
<%User user = (User) session.getAttribute("user");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="css/tai_khoan.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Tài khoản</title>
</head>

<body>
<jsp:include page="header.jsp"/>

<main id="main" class="mt-5 pb-5" user-id="<%=user.getId()%>">
    <div class="container">
        <div class="row justify-content-center">
            <!--Phần menu-->
            <div class="account-page-sidebar col-3">
                <div class="account-sidebar-header list-group align-items-center">
                    <div class="account-sidebar-avatar list-group-item  border-0">
                        <div class="display-avatar  rounded-circle d-flex align-items-center justify-content-center">
                            <img class="rounded-circle" src="<%=user.getAvatar()%>" alt="" id="avatar">
                        </div>
                        <div class="change-avatar position-absolute">
                            <input class="d-none" type="file" accept="image/jpeg,image/png" id="input-avatar">
                            <label for="input-avatar"><i class="fa-solid fa-user-pen fs-5"></i></label>
                        </div>
                    </div>
                    <div class="account-sidebar-name list-group-item  border-0">
                        <h3>Hi, <b><%=user.getFullName()%>
                        </b></h3>
                    </div>
                </div>
                <div class="account-sidebar-menu mt-3">
                    <ul>
                        <li>
                            <button class="active" data-bs-target="0">Thông tin tài khoản</button>
                        </li>
                        <li>
                            <button data-bs-target="1" class="bill-history">Lịch sử mua hàng</button>
                        </li>
                        <li>
                            <button data-bs-target="2" class="product-reviews">Đánh giá sản phẩm</button>
                        </li>
                        <li>
                            <button data-bs-target="3" class="product-reviews">Khóa</button>
                        </li>
                        <li>
                            <a href="logout">
                                <button id="logout">Đăng xuất</button>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <!--Phần trang-->
            <div class="main-content col-9">
                <!--Bắt đầu page-->
                <div class="page-content account-page-content active">
                    <span class="fs-1 title-page-content">Thông tin tài khoản</span>
                    <div class="account-page-detail account-page-info mt-3">
                        <div class="table-responsive">
                            <div class="table overflow-x-hidden">
                                <div class="row">
                                    <div class="col-3"><span>Email</span></div>
                                    <span readonly type="text" class="display-info d-inline-block"
                                          aria-readonly="true"><%=user.getEmail()%></span>
                                </div>
                                <div class="row">
                                    <div class="col-3"><label for="fullname_edit">Họ và tên</label></div>
                                    <input type="text" class="display-info" name="fullName" id="fullname_edit"
                                           value="<%=user.getFullName()%>">
                                </div>
                                <div class="row">
                                    <div class="col-3"><label for="sex_edit">Giới tính</label></div>
                                    <select type="text" class="display-info" name="sex" id="sex_edit"
                                            value="<%=user.getSex()%>">
                                        <option value="Nam" <%=(user.getSex().equals("Nam")) ? "selected='selected'" : "" %> >
                                            Nam
                                        </option>
                                        <option value="Nữ" <%=(user.getSex().equals("Nữ")) ? "selected='selected'" : "" %> >
                                            Nữ
                                        </option>
                                    </select>
                                </div>
                                <div class="row">
                                    <div class="col-3"><label for="birthday_edit">Ngày sinh</label></div>
                                    <input type="date" name="birthday" id="birthday_edit" class="display-info"
                                           value="<%=user.getBirthDay()%>">
                                </div>

                                <div style="display: flex;justify-content: flex-end">
                                    <div style="width:400px">
                                        <button onclick="uploadProfile()"
                                                style="background: #2F189A;padding: 10px;border: 1px solid white;border-radius: 5px;color:white;font-weight: 600">
                                            Lưu
                                        </button>
                                        <button onclick="changePassword({email:'<%= user.getEmail() %>'})"
                                                style="background: #2F189A;padding: 10px;border: 1px solid white;border-radius: 5px;color:white;font-weight: 600">
                                            Đổi mật khẩu
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--Kết thúc page-->

                <!--Bắt đầu page-->
                <div class="page-content bill-history-page-content">
                    <span class="fs-1 title-page-content">Lịch sử mua hàng</span>
                    <div class="body-page-content mt-2">
                        <div class="menu menu-bill">
                            <button type="button" class="menu-item menu-bill-item active" data-action="">
                                Tất cả
                            </button>
                            <button type="button" class="menu-item menu-bill-item" data-action="Chưa ký">
                                Chưa ký
                            </button>
                            <button type="button" class="menu-item menu-bill-item" data-action="Chờ xác nhận">
                                Chờ xác nhận
                            </button>
                            <button type="button" class="menu-item menu-bill-item" data-action="Xác nhận thành công" style="width: 250px">
                                Xác nhận thành công
                            </button>
                            <button type="button" class="menu-item menu-bill-item" data-action="Thành công">
                                Thành công
                            </button>
                            <button type="button" class="menu-item menu-bill-item" data-action="Đã hủy">
                                Đã hủy
                            </button>
                        </div>
                        <div class="display-content display-bills" id="display-bills">
                        </div>
                    </div>
                </div>
                <!--Kết thúc page-->

                <!--Bắt đầu page-->
                <div class="page-content review-page-content">
                    <span class="fs-1 title-page-content">Đánh giá sản phẩm!</span>
                    <div class="body-page-content mt-2">
                        <div class="menu menu-review">
                            <button type="button" class="menu-item menu-review-item active" data-action="Chưa đánh giá"
                                    have-evaluated="false">
                                Chưa đánh giá
                            </button>
                            <button type="button" class="menu-item menu-review-item" data-action="Đã đánh giá"
                                    have-evaluated="true">
                                Đã đánh giá
                            </button>
                        </div>
                        <div class="display-content display-product-reviews" id="display-product-reviews">
                        </div>
                    </div>
                </div>
                <!--Kết thúc page-->

                <!--Bắt đầu page-->
                <div class="page-content update-key-content">
                    <span class="fs-1 title-page-content">Cập nhật khóa</span>
                    <div class="body-page-content mt-2">
                        <form action="update-key" method="post">
                            <div class="input-group mb-3">
                                <span class="input-group-text bg-body" id="basic-addon1"><i class="fa-solid fa-key"
                                                                                            style="color: #FFD43B;"></i></span>
                                <input type="text" id="public-key" class="form-control" placeholder="Khóa công cộng"
                                       aria-label="PublicKey" aria-describedby="basic-addon1">
                            </div>
                            <div class="action-btn row justify-content-end">
                                <button type="button" id="update-key"
                                        disabled
                                        class="btn update-key btn-outline-primary mx-3 col-3" data-bs-toggle="modal"
                                        data-bs-target="#confirmModal">Cập nhật
                                </button>
                                <button type="button" id="delete-key"
                                        class="btn delete-key btn-outline-danger mx-3 col-3" data-bs-toggle="modal"
                                        data-bs-target="#confirmModal">Hủy khóa
                                </button>
                                <button type="button" id="download-app"
                                        class="btn download-app btn-outline-success mx-3 col-3">Tải phần mềm
                                </button>
                            </div>
                            <!-- Modal -->
                            <div class="modal fade " id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                                    <div class="modal-content">
                                        <div class="modal-header fw-bold">
                                            <h5 class="modal-title" id="confirmModalLabel">Modal title</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            ...
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                Hủy
                                            </button>
                                            <button type="submit" class="btn btn-primary">Xác nhận</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <!--Kết thúc page-->
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/tai_khoan.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script type="text/javascript">
    <%if(user != null){%>
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