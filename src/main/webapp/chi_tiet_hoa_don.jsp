<%@ page import="model.service.CartService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="model.bean.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<%
    User user = (User) session.getAttribute("user");
    BannerImage logo = (BannerImage) session.getAttribute("logo");
%>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="notify/notify-metro.css" />
    <link rel="stylesheet" href="css/chi_tiet_hoa_don.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <%--notify--%>
    <script src="notify/notify.js"></script>

    <title>Chi tiết hóa đơn</title>
</head>
<body>
<header id="menu">
    <nav class="navbar navbar-expand-lg pb-0">
        <div class="container-xxl m-md-auto mt-2">
            <div class="row">
                <div class="logo col-lg-2 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                    <a href="index.jsp" class="navbar-brand me-5">
                        <img src="<%=logo.getUrlImage()%>" alt="logo.png">
                        KIMI
                    </a>
                </div>
                <div class="search col-lg-5 col-md-6 col-sm-6 border-0 px-lg-0 px-md-5">
                    <form class="d-flex input-group">
                        <input class="form-control border-0 ps-3" type="text" name="search" id="search"
                               placeholder="Nhập tên sản phẩm!">
                        <span class="input-group-text  border-0"><a class="nav-link" href="#"><i
                                class="fa-solid fa-magnifying-glass"></i></a></span>
                    </form>
                </div>
                <div class="login col-lg-2 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="dang_nhap.jsp">
                        <button type="button" class="btn d-flex float-lg-end  me-xl-4 me-lg-2">
                            <span class="d-lg-inline d-md-none d-sm-none">Đăng nhập</span>
                            <span class="d-lg-none d-md-line material-symbols-outlined ms-1">login</span>
                        </button>
                    </a>
                </div>
                <div class="sign-up col-lg-1 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="dang_ky.jsp">
                        <button type="button" class="btn d-flex float-lg-none">
                            <span class="d-lg-inline d-md-none  d-sm-none">Đăng ký</span>
                            <span class="d-lg-none d-md-line material-symbols-outlined ms-1">logout</span>
                        </button>
                    </a>
                </div>
                <div class="cart col-lg-2 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="gio_hang.jsp">
                        <button type="button" class="btn d-flex float-lg-none">
                            <span class="d-lg-inline d-md-none  d-sm-none">Giỏ hàng</span>
                            <span class="icon d-flex">
                                <span class="material-symbols-outlined">
                                    shopping_cart
                                </span>
                                <span id="amount-product" class="amount-product">
                                    <%
                                        CartService cart = (CartService) session.getAttribute("cart");
                                        if (cart == null) cart = new CartService();
                                    %>
                                    <%=cart.getTotalProduct()%>
                                </span>
                            </span>
                        </button>
                    </a>
                </div>
                <div class="menu-product col-lg-12 col-md-1 col-sm-1">
                    <!--Icon 3 dấu gạch mang hiển thị menu-->
                    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas"
                            data-bs-target="#offcanvasNavbar"
                            aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <!--Các mục trong menu-->
                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar"
                         aria-labelledby="offcanvasNavbarLabel">
                        <div class="offcanvas-body">
                            <ul class="navbar-nav m-auto">
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="product/kinh_mat/kinh_mat.jsp"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        <span>Kính mát</span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="product/kinh_mat/kinh_mat_nam.jsp">Kính mát
                                            nam</a></li>
                                        <li><a class="dropdown-item" href="product/kinh_mat/kinh_mat_nu.jsp">Kính mát
                                            nữ</a></li>
                                        <li><a class="dropdown-item" href="product/kinh_mat/kinh_mat_ngay_va_dem.jsp">Kính
                                            đi ngày và đêm</a></li>
                                        <li><a class="dropdown-item" href="product/kinh_mat/kinh_doi_mau.jsp">Kính đổi
                                            màu</a></li>
                                        <li><a class="dropdown-item"
                                               href="product/kinh_mat/kinh_loc_anh_sang_xanh.jsp">Kính lọc ánh sáng
                                            xanh</a></li>
                                        <li><a class="dropdown-item"
                                               href="product/kinh_mat/kinh_mat_clip_on_hai_lop.jsp">Kính Mắt Clip on 2
                                            Lớp</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="product/mat_kinh_tre_em/mat_kinh_tre_em.jsp"
                                       class="nav-link dropdown-toggle px-4 rounded" type="button">
                                        Mắt kính trẻ em
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item"
                                               href="product/mat_kinh_tre_em/gong_kinh_tre_em.jsp">Gọng Kính Trẻ Em</a>
                                        </li>
                                        <li><a class="dropdown-item"
                                               href="product/mat_kinh_tre_em/kinh_mat_tre_em.jsp">Kính Mát Trẻ Em</a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="product/gong_kinh/gong_kinh.jsp"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        Gọng kính
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="product/gong_kinh/gong_kinh_nua_khung.jsp">Gọng
                                            kính nữa khung</a></li>
                                        <li><a class="dropdown-item" href="product/gong_kinh/gong_kinh_khoan.jsp">Gọng
                                            kính khoan</a></li>
                                        <li><a class="dropdown-item" href="product/gong_kinh/gong_kinh_tron.jsp">Gọng
                                            kính tròn</a></li>
                                        <li><a class="dropdown-item" href="product/gong_kinh/gong_kinh_titan.jsp">Gọng
                                            kính titan</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="product/trong_kinh/trong_kinh.jsp"
                                       class="nav-link dropdown-toggle px-4 rounded"
                                       type="button">
                                        Tròng kính
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item"
                                               href="product/trong_kinh/trong_kinh_chong_anh_sang_xanh.jsp">Tròng kính
                                            chống ánh sáng xanh</a></li>
                                        <li><a class="dropdown-item" href="product/trong_kinh/trong_kinh_doi_mau.jsp">Tròng
                                            kính đổi màu</a></li>
                                        <li><a class="dropdown-item" href="product/trong_kinh/trong_kinh_mau.jsp">Tròng
                                            kính màu</a></li>
                                        <li><a class="dropdown-item"
                                               href="product/trong_kinh/trong_kinh_cho_gong_khoan.jsp">Tròng kính cho
                                            gọng khoan</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="khuyen_mai.jsp" class="nav-link px-4 rounded">Khuyến mãi</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="lien_he.jsp" class="nav-link px-4 rounded">Liên hệ</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>

<%
    Bill bill = (Bill) request.getAttribute("bill");
    BillStatus lastStatus = bill.getLastStatus();
    List<BillStatus> statusList = bill.getStatuses();
    List<BillDetail> billDetails = bill.getDetails();
    NumberFormat nfCurrency = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
    NumberFormat nfNumber = NumberFormat.getNumberInstance(Locale.of("vi", ""));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    List<ProductCart> products = (List<ProductCart>) request.getAttribute("products");
    double totalPriceProducts = CartService.getTotalPriceProducts(products);
%>
<main id="main" class="mt-5 pb-5">
    <div class="container p-2">
        <!--        top-->
        <div class="in4-bill-top d-flex justify-content-between">
            <div class="in4-left">
                <div class="id-bill-and-in4-ship d-flex align-items-center justify-content-around">
                    <div class="id-bill"><p>
                        <h3 class="fw-bold">Hóa đơn: <span class="bill-id">#<%=bill.getId()%></span></h3></div>
                    <div class="in4-ship px-3 mx-5"><span><%=lastStatus.getStatus()%></span></div>
                </div>
                <div class="time-order py-2"><p><span><%=dateFormat.format(lastStatus.getDate())%></span></p></div>
            </div>
            <%if (bill.canEdit()) {%>
            <div class="in4-right ">
                <button type="button" id="button-show-dialog-cancel-bill"  data-bs-toggle="modal"
                        data-bs-target="#accept-cancel"><i class="fa-solid fa-trash"></i> Hủy đơn hàng</button>
            </div>
            <%}%>
        </div>

        <!--sản phẩm và thông tin khác hàng-->
        <div class="item-and-in4-customer row">

            <div class="p-3 col-8">
                <!--thông tin sản phẩm-->
                <div class="in4-item-frame">
                    <div class="col-name-item py-3 row">
                        <div class="col-6 ps-4">Sản phẩm</div>
                        <div class="col-2 text-center">Đơn giá</div>
                        <div class="col-2 text-center">Số lượng</div>
                        <div class="col-2 text-center">Thành tiền</div>
                    </div>

                    <div class="list-item">
                        <%
                            Model model = null;
                            ProductCart product = null;
                            BillDetail billDetail = null;
                            for (int i = 0; i < products.size(); i++) {
                                product = products.get(i);
                                billDetail = billDetails.get(i);
                                model = product.getModel();
                        %>
                        <div class="item row mx-0 py-2">
                            <div class="col-6 ps-4 d-flex">
                                <div class="img-item pe-4">
                                    <img class="d-none" src="" alt="">
                                    <div class="img-60x60 text-center rounded-circle">
                                        <img src="<%=model.getUrlImage()%>" alt="<%=model.getName()%>.png"
                                             class="rounded-circle w-100">
                                    </div>
                                </div>
                                <div class="in4-item">
                                    <p>
                                        <span><%=product.getName()%></span><br>
                                        <span style="font-size: 12px; color: rgba(0, 0, 0, 0.5)"><%=model.getName()%></span>
                                    </p>
                                </div>
                                <div></div>
                            </div>
                            <div class="col-2 text-center"><%=nfCurrency.format(billDetail.getPrice())%>
                            </div>
                            <div class="col-2 text-center"><%=nfNumber.format(billDetail.getQuantity())%>
                            </div>
                            <div class="col-2 text-center"><%=nfCurrency.format(billDetail.getQuantity() * billDetail.getPrice())%>
                            </div>
                        </div>
                        <%}%>
                    </div>

                    <!--hiện thị tổng số tiền cần trả-->
                    <div class="show-total-money py-3 mx-5 d-flex align-items-center justify-content-end">
                        <div class="show-total px-3">
                            <ul class="ps-0 mb-0">
                                <li>
                                    <span>Tổng hóa đơn:</span><span><%=nfCurrency.format(totalPriceProducts)%></span>
                                </li>
                                <li>
                                    <span>Phí vận chuyển:</span><span><%=nfCurrency.format(bill.getTransportFee())%></span>
                                </li>
                                <li>
                                    <span>Tổng tiền phải trả:</span><span><%=nfCurrency.format(totalPriceProducts + bill.getTransportFee())%></span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!--kết thúc thông tin sản phẩm-->

                <!--lộ trình vận chuyển-->
                <div class="trip-ship mt-4">
                    <div class="row mx-0 header-trip-ship">
                        <div class="col-12 ps-4 py-3">
                            <h4>Lộ trình vận chuyển hàng</h4>
                        </div>
                    </div>
                    <div class="show-list-trip body-trip-ship p-3">
                        <%for (BillStatus status : bill.getStatuses()) {%>
                        <div class="trip d-flex justify-content-between">
                            <div class="in4-trip">
                                <div class="icon-trip d-flex justify-content-between">
                                    <span><i class="fa-solid fa-circle"></i></span>
                                    <div class="has-one-line px-2">
                                        <p><%=status.getStatus()%>
                                        </p>
                                        <p><%=status.getDescribe()%>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="time-trip">
                                <span><%=dateFormat.format(status.getDate())%></span>
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
                <!--Kết thúc phần lộ trình-->
            </div>


            <div class="p-3 col-4">
                <!--thông tin khách hàng-->
                <div class="in4-customer ">
                    <div class="col-name-in4-customer"><h4 class="px-3 py-3 mb-2">Thông tin khách hàng</h4></div>
                    <div class=" d-flex px-3 py-1">
                        <div class="avatar-customer">
                            <img class="rounded-circle" src="<%=user.getAvatar()%>" alt="avatar.png">
                        </div>
                        <div class="info-customer ms-2 w-100">
                            <p class="name-customer"><%=user.getFullName()%>
                            </p>
                            <p class="email-customer"><%=user.getEmail()%>
                            </p>
                        </div>
                    </div>
                    <div class="tong-san-pham d-flex align-items-start justify-content-start  px-3 pt-1 pb-3">
                        <div class="icon-cart d-flex align-items-center justify-content-center"><i
                                class="fa-solid fa-cart-shopping"></i></div>
                        <p>Tổng loại sản phẩm: <span><%=nfNumber.format(products.size())%></span></p>
                    </div>
                    <div class="address  px-3 pb-2 pt-2">
                        <div class="edit-address d-flex align-items-center justify-content-between">
                            <p>Địa chỉ</p>
                            <%if (bill.canEdit()) {%>
                            <button id="edit" type="button" class="btn" data-bs-toggle="modal"
                                    data-bs-target="#editAddressModal">
                                <i class="fa-solid fa-pen"></i>
                            </button>
                            <%}%>
                        </div>

                        <p class="mb-1">
                            <span>Họ và tên: </span>
                            <span id="name" class="name-customer"><%=bill.getUserName()%></span>
                        </p>
                        <p  class="mb-1">
                            <span>Số điện thoại: </span>
                            <span id="phone-number" class="number-phone-customer"><%=bill.getPhoneNumber()%></span>
                        </p>
                        <p  class="mb-1">
                            <span>Email: </span>
                            <span id="email" class="number-phone-customer"><%=bill.getEmail()%></span>
                        </p>
                        <p>
                            <span>Địa chỉ: </span>
                            <span id="address"
                                  province-code="<%=bill.getCodeProvince()%>"
                                  district-code="<%=bill.getCodeDistrict()%>"
                                  ward-code="<%=bill.getCodeWard()%>"
                                  address="<%=bill.getAddress()%>"
                                  class="address-customer text-end w-75"><%=bill.getAddressDetail()%></span>
                        </p>
                    </div>
                </div>
                <!--Kết thúc thông tin khách hàng-->
                <button type="button" class="back user-back w-100 py-2 rounded-3 mt-3">Quay lại</button>
            </div>
        </div>
    </div>

    <section>
        <!-- Modal edit contact-->
        <div class="modal fade" id="editAddressModal" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Chỉnh sửa thông tin</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="form">
                        <div class="mb-3">
                            <label for="input-name" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="input-name"
                                   placeholder="Nhập họ và tên">
                        </div>
                        <div class="mb-3">
                            <label for="input-phone-number" class="form-label">Số điện thoại <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="input-phone-number"
                                   placeholder="Nhập số điện thoại">
                        </div>
                        <div class="mb-3">
                            <label for="input-email" class="form-label">Email <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="input-email"
                                   placeholder="Nhập email">
                        </div>
                        <hr>
                        <div class="row mb-2">
                            <div class="col-md-6">
                                <label for="provinces" class="form-label">Thành phố/Tỉnh <span class="text-danger">*</span></label>
                                <select class="info-payer form-control" name="provinces" id="provinces" required>
                                    <option selected value="" disabled style="color: #fff">Chọn thành phố/tỉnh
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="districts" class="form-label">Quận/Huyện <span class="text-danger">*</span></label>
                                <select id="districts" name="districts" class="info-payer form-control" required>
                                    <option selected value="" disabled style="color: #fff">Chọn quận/huyện</option>
                                </select>
                            </div>

                            <div class="col-md-12 mt-2">
                                <label for="wards" class="form-label">Phường/Xã <span class="text-danger">*</span></label>
                                <select class="info-payer form-control" id="wards" name="wards" required>
                                    <option selected value="" disabled style="color: #fff">Chọn phường/xã</option>
                                </select>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="input-address" class="form-label">Địa chỉ cụ thể <span class="text-danger">*</span></label>
                            <input type="text" name="full-address" class="info-payer form-control" required
                                   placeholder="số xx, thôn xx, xã xx, huyện xx, tỉnh xx" id="input-address">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">Đóng
                        </button>
                        <button id="save" bill-id="<%=bill.getId()%>"  type="button" class="btn" data-bs-dismiss="modal"
                                style="background-color: var(--color-blue-origin); color: #FFFFFF">Lưu
                        </button>
                    </div>
                </div>
            </div>
        </div>


        <!-- Modal accept-->
        <div class="modal fade" id="accept-cancel" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <img src="images/icon/warning.png" class="me-1" style="width: 25px">
                        <h1 class="modal-title fs-5" >Thông báo</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body" >
                        <p>Bạn có chắc chắn hủy đơn hàng này không!</p>
                    </div>
                    <div class="modal-footer">
                        <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">Hủy
                        </button>
                        <button id="cancel-bill" bill-id="<%=bill.getId()%>" type="button" class="btn" data-bs-dismiss="modal"
                                style="background-color: var(--color-blue-origin); color: #FFFFFF">OK
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<footer id="footer" class="footer">
    <div class="container ">
        <div class="footer-top row">
            <div class="footer-top-item col">
                <h5>Mắt Kính KIMI
                </h5>
                <div class="footer-content footer-contact">
                    <div class="ft_map">

                    </div>
                    <ul>
                        <li class="contact-1"><i class="fa-solid fa-map-location-dot px-1"></i><span class="px-1">Khu phố 6, Phường Linh Trung, Quận Thủ Đức, TP. Hồ Chí Minh</span>
                        </li>
                        <li class="contact-2"><i class="fa-solid fa-phone px-1"></i><b><span
                                class="px-1">0855354919</span></b> ( 9:00 - 21:00 )
                        </li>
                        <li class="contact-3"><i class="fa-solid fa-business-time px-1"></i><span class="px-1">9:00 - 20:00 ( Kể cả T7 và CN )</span>
                        </li>
                        <li class="contact-4"><i class="fa-solid fa-envelope px-1"></i><span class="px-1">matkinhkimi@gmail.com</span>
                        </li>
                        <li class="contact-5"><a href="https://www.facebook.com/profile.php?id=100045667640701"><i
                                class="fa-brands fa-facebook-f px-1"></i><span
                                class="px-1 hover"><b>KIMI</b> </span></a></li>
                        <li class="contact-6"><p>Kiểm tra thị lực miễn phí &amp; cắt kính lấy liền.</p></li>
                        <li class="contact-7"><p>Hỗ trợ trả góp lãi suất 0% thẻ tín dụng.</p></li>
                    </ul>
                </div>
            </div>

            <div class="footer-top-item col">
                <h5>HỖ TRỢ KHÁCH HÀNG
                </h5>
                <div class="footer-content footer-contact">
                    <div class="ft_map">

                    </div>
                    <ul>
                        <li><a class="hover" href="policy_pages/huong_dan_mua_hang_online.jsp">Hướng dẫn mua hàng
                            online</a>
                        </li>
                        <li><a class="hover" href="policy_pages/chinh_sach_thanh_toan_va_giao_nhan.jsp">Chính sách thanh
                            toán,
                            giao nhận</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_bao_mat.jsp">Chính sách bảo mật</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_bao_hanh.jsp">Chính sách bảo hành</a></li>
                        <li><a class="hover" href="policy_pages/chinh_sach_doi_tra_va_hoan_tien.jsp">Chính sách đổi trả
                            và
                            hoàn tiền</a></li>
                        <li><a class="hover" href="policy_pages/kiem_tra_don_hang.jsp">Kiểm tra đơn hàng</a></li>
                    </ul>
                </div>

            </div>
        </div>
        <div class="row footer-bot text-center border-3">
            <div class="logo col-lg-3 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                <a href="index.jsp">
                    <img src="images/logo/logo.png" alt="logo.png">
                    <span>KIMI</span>
                </a>
            </div>
            <div class="col"><span>© 2023 - Tất cả các quyền thuộc về KIMI</span></div>
        </div>
    </div>
</footer>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/chi_tiet_hoa_don.js"></script>
<script src="javascript/gio_hang.js"></script>
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