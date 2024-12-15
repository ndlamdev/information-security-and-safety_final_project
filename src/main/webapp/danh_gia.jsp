<%@ page import="com.lamnguyen.mat_kinh_kimi.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) application.getAttribute("logo");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="css/danh_gia.css">
    <link rel="icon" type="image/x-icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Đánh giá</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<%
    int reviewId = (int) request.getAttribute("review-id");
    ProductReview productReview = (ProductReview) request.getAttribute("product-review");
    Review review = (Review) request.getAttribute("review");
%>
<main id="main" class="mt-5 pb-5" review-id="<%=reviewId%>">
    <div class="container">
        <button type="button" id="<%=request.getAttribute("action-back")%>" class="back-to-home text-white">
            <i class="fa-solid fa-house"></i>
            <span>Quay về</span>
        </button>

        <div class="review-product mt-3" product-id="<%=productReview.getProductId()%>">
            <div class="review-product header mb-2">
            </div>
            <div class="review-product-body">
                <div class="info-product">
                    <div class="name-product">
                        <p><%=productReview.getProductName()%>
                        </p>
                    </div>
                    <div class="option-product">
                        <span>Màu: </span>
                        <span><%=productReview.getModelName()%></span>
                    </div>
                    <div class="img-product">
                        <img src="<%=productReview.getUrlImage()%>" alt="">
                    </div>
                </div>

                <div class="review">
                    <form action="review" method="post">
                        <div class="quality-product" id="input-star">
                            <p class="fs-4 d-block">Chất lượng sản phẩm</p>
                            <ul class="d-inline-flex list-star ps-0">
                                <!--Các li có class checked là sao hoàn thiện-->
                                <li class="checked">
                                    <i class="fa-solid fa-star" style="color: #fdd836;" star="1"></i>
                                </li>
                                <li class="checked">
                                    <i class="fa-solid fa-star" style="color: #fdd836;" star="2"></i>
                                </li>
                                <li class="checked">
                                    <i class="fa-solid fa-star" style="color: #fdd836;" star="3"></i>
                                </li>
                                <li class="checked">
                                    <i class="fa-solid fa-star" style="color: #fdd836;" star="4"></i>
                                </li>
                                <li class="checked">
                                    <i class="fa-solid fa-star" style="color: #fdd836;" star="5"></i>
                                </li>
                            </ul>
                            <span id="text-quality-product">Tuyệt vời</span>
                            <input type="hidden" name="star" id="star" value="5">
                        </div>
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="review-id" value="<%=reviewId%>">
                        <div class="evaluate-product mt-3">
                            <label for="comment">
                                <span class="fs-4">Đánh giá</span>
                                <span class="text-danger">*</span>
                            </label>
                            <textarea class="mt-1 p-2" placeholder="Nhập thông tin đánh giá" rows="5" name="comment"
                                      id="comment" required><%=review != null ? review.getComment() : ""%></textarea>
                        </div>
                        <div class="img-product mt-3">
                            <span class="fs-4">Hình ảnh: </span>
                            <span class="text-secondary">(Tối đa 5 sản phẩm)</span><br>
                            <div class="list-img-review-product d-flex mt-1">
                                <%
                                    if (review != null) {
                                        for (ReviewImage reviewImage : review.getReviewImages()) {
                                %>
                                <div class="a-img-review-product">
                                    <img src="<%=reviewImage.getUrlImage()%>" alt="img-review">
                                    <button review-image-id="<%=reviewImage.getId()%>" type="button"
                                            class="text-danger cancel">x
                                    </button>
                                </div>
                                <%}%>
                                <%if (review.getReviewImages().size() < 5) {%>
                                <label for="input-img-review" id="label-upload-image">
                                    <i class="fa-solid fa-cloud-arrow-up"></i>
                                </label>
                                <%
                                    }
                                } else {
                                %>
                                <label for="input-img-review" id="label-upload-image">
                                    <i class="fa-solid fa-cloud-arrow-up"></i>
                                </label>
                                <%}%>
                            </div>
                        </div>
                        <%if (review != null) {%>
                        <button class="mt-3 bg-danger button-remove" type="button" id="remove-review">
                            Xóa đánh giá
                        </button>
                        <%}%>
                        <button class="mt-3" type="submit" id="submit-review">
                            Đánh giá
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <input type="file" id="input-img-review" name="input-img-review" hidden="">
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/danh_gia.js"></script>
<script type="text/javascript">
    <%if(review != null) {%>
    setStar();
    $("#input-star>ul>li>i").eq(<%=review.getNumberStar()%> -1).click();
    <%}%>
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