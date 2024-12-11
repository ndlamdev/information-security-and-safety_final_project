<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.ProductCart" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) session.getAttribute("logo");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="css/gio_hang.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <title>Mua ngay</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<main id="main" class="mt-5 pb-5">
    <div class="container rounded cart">
        <div class="row no-gutters">
            <div class="col-md-8">
                <div class="product-details mr-2">
                    <!--Quay về-->
                    <div class="d-flex flex-row align-items-center">
                        <a href="index.jsp">
                            <i class="fa fa-long-arrow-left"></i>
                            <span class="ms-2">Quay lại trang chủ</span>
                        </a>
                    </div>
                    <hr>
                    <!--Phần header-->
                    <div class="cart-header">
                        <h6 class="mb-0">Giỏ hàng</h6>
                        <div class="d-flex justify-content-between">
                            <span>Bạn đang có <span
                                    class="amount-product">0</span> sản phẩm</span>
                        </div>
                    </div>
                    <!--Danh sách sản phẩm-->
                    <div class="list-product cart-body" id="list-product">
                        <%
                            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
                            ProductCart productCart = (ProductCart) request.getAttribute("product-cart");
                            if (productCart != null) {
                        %>
                        <!--Sản phẩm-->
                        <div class="product">
                            <input class="product-checkbox" type="checkbox"
                                   product-id="<%=productCart.getProductId()%>"
                                   model-id="<%=productCart.getModel().getId()%>" hidden="hidden" checked>
                            <div class="info-product">
                                <img class="rounded" src="<%=productCart.getModel().getUrlImage()%>">
                                <div class="ms-2">
                                    <span class="fw-bold d-block name-product"><%=productCart.getName()%></span>
                                    <span class="option"><%=productCart.getModel().getName()%></span>
                                </div>
                            </div>
                            <div class="change-amount">
                                <button type="button"
                                        product-id="<%=productCart.getProductId()%>"
                                        model-id="<%=productCart.getModel().getId()%>"
                                        class="down">
                                    <span class="material-symbols-outlined">arrow_left</span>
                                </button>
                                <input type="number" name="amount-product-item"
                                       product-id="<%=productCart.getProductId()%>"
                                       model-id="<%=productCart.getModel().getId()%>"
                                       class="amount-product-item" min="1"
                                       max="<%=productCart.getQuantity()%>"
                                       value="<%=productCart.getQuantity()%>" disabled>
                                <button type="button"
                                        product-id="<%=productCart.getProductId()%>"
                                        model-id="<%=productCart.getModel().getId()%>"
                                        class="up">
                                    <span class="material-symbols-outlined">arrow_right</span>
                                </button>
                            </div>
                            <div class="price">
                                    <span class="price">
                                      <%if (productCart.hasDiscount()) {%>
                                      <%=nf.format(productCart.getDiscount())%>
                                      <%} else {%>
                                      <%=nf.format(productCart.getPrice())%>
                                      <%}%>
                                    </span>
                            </div>
                            <div class="total-price">
                                    <span class="total-money">
                                      <%=nf.format(productCart.totalPrice())%>
                                    </span>
                            </div>
                        </div>
                        <!--Kết thúc sản phẩm-->
                        <%}%>
                    </div>
                </div>
            </div>
            <!--Thanh toán-->
            <div class="col-md-4" id="pay">
                <div class="payment-info">
                    <form accept-charset="UTF-8" action="buy_now" method="POST" id="form-info-customer">
                        <input type="text" name="action" hidden="" value="pay">
                        <div class="d-flex justify-content-between align-items-center">
                            <span>Thanh toán</span>
                        </div>
                        <!--Hình thức thanh toán-->
                        <div class="pay-option">
                            <span class="type d-block mt-3 mb-1">Hình thức thanh toán</span>
                            <div class="list-option-pay pb-2">
                                <label class="radio" for="cash">
                                    <input type="radio" name="pay-option" value="cash" id="cash" checked>
                                    <span>
                                    <img src="images/icon/cash.png" alt="cash.png">
                                </span>
                                </label>
                                <label for="transfer" class="radio" data-bs-toggle="modal" data-bs-target="#qr-pay">
                                    <input type="radio" name="pay-option" value="transfer" id="transfer">
                                    <span>
                                    <img src="images/icon/transfer.png" alt="cash.png">
                                </span>
                                </label>
                            </div>
                        </div>
                        <div class="form-info">
                            <div>
                                <label for="full-name">Họ và tên <span class="text-danger">*</span></label>
                                <input required type="text" class="info-payer" placeholder="Họ và tên" name="full-name"
                                       id="full-name">
                            </div>
                            <div>
                                <label for="phone-number">Email <span class="text-danger">*</span></label>
                                <input type="email" class="info-payer" required placeholder="Nhập email" name="email"
                                       id="email">
                            </div>
                            <div>
                                <label for="phone-number">Số điện thoại <span class="text-danger">*</span></label>
                                <input type="tel" class="info-payer" pattern="[0-9]{4}[0-9]{3}[0-9]{3}" required
                                       placeholder="Nhập số điện thoại" name="phone-number" id="phone-number">
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="provinces">Thành phố/Tỉnh <span class="text-danger">*</span></label>
                                    <select class="info-payer" name="provinces" id="provinces" required>
                                        <option selected value="" disabled style="color: #fff">Chọn thành phố/tỉnh
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label for="districts">Quận/Huyện <span class="text-danger">*</span></label>
                                    <select id="districts" name="districts" class="info-payer" required>
                                        <option selected value="" disabled style="color: #fff">Chọn quận/huyện</option>
                                    </select>
                                </div>

                                <div class="col-md-12">
                                    <label for="wards">Phường/Xã <span class="text-danger">*</span></label>
                                    <select class="info-payer" id="wards" name="wards" required>
                                        <option selected value="" disabled style="color: #fff">Chọn phường/xã</option>
                                    </select>
                                </div>
                            </div>
                            <div>
                                <label for="full-address">Địa chỉ cụ thể <span class="text-danger">*</span></label>
                                <input type="text" name="full-address" class="info-payer" required
                                       placeholder="số xx, thôn xx, xã xx, huyện xx, tỉnh xx" id="full-address">
                            </div>
                        </div>
                        <hr class="line">
                        <div class="money">
                            <div class="d-flex justify-content-between information">
                                <span>Tổng hóa đơn</span>
                                <p class="m-0 p-0" id="totalBill">
                                    <%=request.getAttribute("totalBill")%>
                                </p>
                            </div>
                            <div class="d-flex justify-content-between information">
                                <span>Giảm</span>
                                <p class="m-0 p-0" id="totalPriceReduced">
                                    <%=request.getAttribute("totalPriceReduced")%>
                                </p>
                            </div>
                            <div class="d-flex justify-content-between information">
                                <span>Phí ship</span>
                                <p class="m-0 p-0" id="shippingFee">
                                    <%=request.getAttribute("shippingFee")%>
                                </p>
                            </div>
                            <div class="d-flex justify-content-between information">
                                <span>Tổng trả: </span>
                                <p class="m-0 p-0" id="totalPay">
                                    <%=request.getAttribute("totalPay")%>
                                </p>
                            </div>
                        </div>
                        <button id="button-pay"
                                class="btn btn-primary btn-block d-flex justify-content-center mt-2 w-100"
                                type="submit">
                            <span>Thanh toán<i class="fa fa-long-arrow-right ms-1"></i></span>
                        </button>
                    </form>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="qr-pay" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                 aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">Mã thanh toán</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <img src="images/qr.png" alt="qr.png">
                        </div>
                    </div>
                </div>
            </div>

            <button hidden="" id="button-error-select-product" data-bs-toggle="modal"
                    data-bs-target="#error-select-product"></button>
            <div class="modal fade" id="error-select-product" data-bs-backdrop="static" data-bs-keyboard="false"
                 tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5">Lỗi</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <h3><%=session.getAttribute("title")%>
                            </h3>
                            <p><%=session.getAttribute("message")%>
                            </p>
                        </div>
                        <%if (session.getAttribute("title") != null && ((String) session.getAttribute("title")).contains("đăng nhập")) {%>
                        <div class="modal-footer">
                            <a href="dang_nhap.jsp" style="color:#fff;">
                                <button type="button" class="btn btn-primary">
                                    OK
                                </button>
                            </a>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/mua_ngay.js"></script>
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

    <%if(session.getAttribute("title") != null){%>
    $("#button-error-select-product").click();
    <%session.removeAttribute("title");
    session.removeAttribute("message");%>
    <%}%>
</script>
</body>
</html>