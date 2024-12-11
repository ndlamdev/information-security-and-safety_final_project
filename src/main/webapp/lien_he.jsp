<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    BannerImage logo = (BannerImage) session.getAttribute("logo");
    BannerImage contact = (BannerImage) session.getAttribute("contact");
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
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Liên hệ</title>
</head>

<body>

<jsp:include page="header.jsp"/>

<main id="main" class="contact-form py-1 pt-5">
    <div class="container shadow p-3 mb-5 bg-body-tertiary rounded">
        <div class="row">
            <div class="contact-info col ">
                <h4>Liên hệ với chúng tôi</h4>
                <form>
                    <div class="form-group">
                        <label for="name">Tên của bạn <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Số điện thoại <span class="text-danger">*</span></label>
                        <input type="tel" class="form-control" id="phone" name="phone" required pattern="[0-9]{10}">
                    </div>
                    <div class="form-group">
                        <label for="address">Địa chỉ <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="address" name="address" required>
                    </div>
                    <div class="form-group">
                        <label for="comment">Bình luận <span class="text-danger">*</span></label>
                        <textarea class="form-control" id="comment" name="comment" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary mt-2 ">Gửi thông tin</button>
                </form>
            </div>
            <div class="contact-image col">
                <img src="<%=contact.getUrlImage()%>" class="object-fit-cover border rounded overflow-hidden"
                     style="width: 100%; height: 424px" alt="Hình ảnh liên hệ">
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
    setTimeout(() => {
        $(".menu-item").eq(5).addClass("active")
    }, 250)
    <%}%>
</script>
</body>

</html>