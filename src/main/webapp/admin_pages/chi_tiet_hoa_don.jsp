<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.*" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.domain.dto.Signature" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<%
    User user = (User) session.getAttribute("user");
    BannerImage logo = (BannerImage) application.getAttribute("logo");
    Signature signature = (Signature) request.getAttribute("signature");
    String publicKey = (String) request.getAttribute("publicKey");
    Object billStatusObject = request.getAttribute("next-status");
    BillStatusEnum.BillStatusJson nextStatus = null;
    if (billStatusObject != null)
        nextStatus = (BillStatusEnum.BillStatusJson) billStatusObject;
%>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/reset.css">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="../css/menu_footer.css">
    <link rel="stylesheet" href="../notify/notify-metro.css"/>
    <link rel="stylesheet" href="../css/chi_tiet_hoa_don.css">
    <link rel="icon" href="../<%=logo.getUrlImage()%>">

    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>

    <%--notify--%>
    <script src="../notify/notify.js"></script>

    <title>Chi tiết hóa đơn</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<%
    Bill bill = (Bill) request.getAttribute("bill");
    BillStatus lastStatus = bill.getLastStatus();
    List<BillDetail> billDetails = bill.getDetails();
    NumberFormat nfCurrency = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
    NumberFormat nfNumber = NumberFormat.getNumberInstance(Locale.of("vi", ""));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    List<ProductCart> products = (List<ProductCart>) request.getAttribute("products");
    double totalPriceProducts = CartService.getTotalPriceProducts(products);
%>
<main id="main" class="mt-5 pb-5">
    <div class="container p-2">
        <!--top-->
        <div class="in4-bill-top d-flex justify-content-between">
            <div class="in4-left">
                <div class="id-bill-and-in4-ship d-flex align-items-center justify-content-around">
                    <div class="id-bill"><p>
                        <h3 class="fw-bold">
                            <span>Hóa đơn: </span>
                            <span class="bill-id">#<%=bill.getId()%></span>
                        </h3></div>
                    <div class="in4-ship px-3 mx-5"><span id="current-status"><%=lastStatus.getStatus()%></span></div>
                </div>
                <div class="time-order py-2"><p><span><%=dateFormat.format(lastStatus.getDate())%></span></p></div>
            </div>
            <div id="bill-action">
                <%if (nextStatus != null && !lastStatus.getStatus().equals("Đã hủy")) {%>
                <div class="in4-right cancel-bill">
                    <button type="button" data-bs-toggle="modal"
                            data-bs-target="#confirm-cancel-bill">
                        <i class="fa-solid fa-trash button-show-dialog-cancel-bill"></i> Hủy đơn hàng
                    </button>
                </div>
                <%} else if (lastStatus.getStatus().equals("Đã hủy")) {%>
                <div class="in4-right revert-bill">
                    <button type="button" data-bs-toggle="modal"
                            class="bg-success"
                            data-bs-target="#confirm-revert-bill">
                        <i class="fa-solid fa-clock-rotate-left button-show-dialog-cancel-bill"></i> Khôi phục đơn hàng
                    </button>
                </div>
                <%}%>
            </div>
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
                            Model model;
                            ProductCart product;
                            BillDetail billDetail;
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
                                        <img src="../<%=model.getUrlImage()%>" alt="<%=model.getName()%>.png"
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
                    <div class="row mx-0 header-trip-ship py-3 px-4">
                        <div class="col-5 d-flex align-items-center">
                            <h4>Lộ trình vận chuyển hàng</h4>
                        </div>
                        <div class="col-7 d-flex justify-content-end gap-2">
                            <%if (nextStatus != null && nextStatus.getStep() == 1) {%>
                            <button class="py-1 px-2 border-0 rounded-2 text-white bg-danger <%=lastStatus.getStatus().equals("Đã hủy") || lastStatus.getStatus().equals("Chưa ký") ? "d-none" : ""%>"
                                    id="resign"
                                    data-id="<%=bill.getId()%>"
                            >
                                <span>Yêu cầu cập nhật lại chữ ký</span>
                            </button>
                            <%}%>
                            <%if (nextStatus != null) {%>
                            <button class="py-1 px-2 border-0 rounded-2 text-white bg-success <%=lastStatus.getStatus().equals("Đã hủy") || lastStatus.getStatus().equals("Chưa ký") ? "d-none" : ""%>"
                                    id="update-status"
                                    data-bs-target="<%=lastStatus.getStatus().equals("Chờ xác nhận") ?"#model-verify-bill" : "#model-update-status" %>"
                                    data-bs-toggle="modal"
                            >
                                <i class="fa-solid fa-pen"></i>
                                <span>Cập nhật trạng thái</span>
                            </button>
                            <%}%>
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


            <%User customer = (User) request.getAttribute("customer");%>
            <div class="p-3 col-4">
                <!--thông tin khách hàng-->
                <div class="in4-customer ">
                    <div class="col-name-in4-customer"><h4 class="px-3 py-3 mb-2">Thông tin khách hàng</h4></div>
                    <div class=" d-flex px-3 py-1">
                        <div class="avatar-customer">
                            <img class="rounded-circle" src="../<%=customer.getAvatar()%>" alt="avatar.png">
                        </div>
                        <div class="info-customer ms-2 w-100">
                            <p class="name-customer"><%=customer.getFullName()%>
                            </p>
                            <p class="email-customer"><%=customer.getEmail()%>
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
                            <button id="edit" type="button" class="btn" data-bs-toggle="modal"
                                    data-bs-target="#editAddressModal">
                                <i class="fa-solid fa-pen"></i>
                            </button>
                        </div>

                        <p class="mb-1">
                            <span>Họ và tên: </span>
                            <span id="name" class="name-customer"><%=bill.getUserName()%></span>
                        </p>
                        <p class="mb-1">
                            <span>Số điện thoại: </span>
                            <span id="phone-number" class="number-phone-customer"><%=bill.getPhoneNumber()%></span>
                        </p>
                        <p class="mb-1">
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
        <div class="modal fade" id="editAddressModal" tabindex="-1"
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
                            <label for="input-name" class="form-label">Họ và tên <span
                                    class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="input-name"
                                   placeholder="Nhập họ và tên">
                        </div>
                        <div class="mb-3">
                            <label for="input-phone-number" class="form-label">Số điện thoại <span
                                    class="text-danger">*</span></label>
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
                                <label for="provinces" class="form-label">Thành phố/Tỉnh <span
                                        class="text-danger">*</span></label>
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
                                <label for="wards" class="form-label">Phường/Xã <span
                                        class="text-danger">*</span></label>
                                <select class="info-payer form-control" id="wards" name="wards" required>
                                    <option selected value="" disabled style="color: #fff">Chọn phường/xã</option>
                                </select>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="input-address" class="form-label">Địa chỉ cụ thể <span
                                    class="text-danger">*</span></label>
                            <input type="text" name="full-address" class="info-payer form-control" required
                                   placeholder="số xx, thôn xx, xã xx, huyện xx, tỉnh xx" id="input-address">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">Đóng
                        </button>
                        <button id="save" bill-id="<%=bill.getId()%>" type="button" class="btn" data-bs-dismiss="modal"
                                style="background-color: var(--color-blue-origin); color: #FFFFFF">Lưu
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal confirm cancel bill-->
        <div class="modal fade" id="confirm-cancel-bill" tabindex="-1"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <img src="../images/icon/warning.png" class="me-1" style="width: 25px">
                        <h1 class="modal-title fs-5">Thông báo</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc chắn muốn hủy đơn hàng này không!</p>
                    </div>
                    <div class="modal-footer">
                        <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">
                            Hủy
                        </button>
                        <button id="cancel-bill" bill-id="<%=bill.getId()%>" type="button" class="btn"
                                data-bs-dismiss="modal"
                                style="background-color: var(--color-blue-origin); color: #FFFFFF">
                            OK
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal confirm revert bill-->
        <div class="modal fade" id="confirm-revert-bill" tabindex="-1"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <img src="../images/icon/warning.png" class="me-1" style="width: 25px">
                        <h1 class="modal-title fs-5">Thông báo</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc chắn muốn khôi phục đơn hàng này không!</p>
                    </div>
                    <div class="modal-footer">
                        <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">
                            Hủy
                        </button>
                        <button id="revert-bill" bill-id="<%=bill.getId()%>" type="button" class="btn"
                                data-bs-dismiss="modal"
                                style="background-color: var(--color-blue-origin); color: #FFFFFF">
                            OK
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal verify bill-->
        <div class="modal fade" id="model-verify-bill" tabindex="-1"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <i class="fa-solid fa-circle-exclamation me-1 text-warning fs-3"></i>
                        <h1 class="modal-title fs-5">Xác thực chữ ký đơn hàng!</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="d-flex align-items-center mb-3">
                            <span class="text-primary fs-6 px-3 py-2 rounded-2 me-3 text-nowrap"
                                  style="background-color: #F5F5F5">
                               Thuật toán
                            </span>
                            <p class="text-nowrap fs-5 overflow-hidden m-0">
                                <%=signature.getAlgorithm()%>
                            </p>
                        </div>
                        <div class="d-flex align-items-center mb-3">
                            <span class="text-primary fs-6 px-3 py-2 rounded-2 me-3 text-nowrap"
                                  style="background-color: #F5F5F5">
                                Chữ Ký
                            </span>
                            <p class="text-nowrap fs-5 overflow-hidden m-0">
                                <%=signature.getSignature()%>
                            </p>
                        </div>
                        <div class="d-flex align-items-center">
                            <span class="text-primary fs-6 px-3 py-2 rounded-2 me-3 text-nowrap"
                                  style="background-color: #F5F5F5">
                                Khóa Công Cộng
                            </span>
                            <p class="text-nowrap fs-5 overflow-hidden m-0">
                                <%=publicKey%>
                            </p>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="reset" class="btn btn-secondary" id="cancel-verify-bill" data-bs-dismiss="modal">
                            Hủy
                        </button>
                        <button id="verify-bill" bill-id="<%=bill.getId()%>" type="button" class="btn"
                                style="background-color: var(--color-blue-origin); color: #FFFFFF">
                            Xác nhận
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal update bill status-->
        <%
            if (nextStatus != null) {
        %>
        <div class="modal fade" id="model-update-status" tabindex="-1"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <i class="fa-solid fa-circle-exclamation me-1 text-warning fs-3"></i>
                        <h1 class="modal-title fs-5">Cập nhật trạng thái đơn hàng!</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="d-flex align-items-center mb-3">
                            <span class="text-primary fs-6 px-3 py-2 rounded-2 me-3 text-nowrap"
                                  style="background-color: #F5F5F5">
                               Trạng thái
                            </span>
                            <p class="text-nowrap fs-5 overflow-hidden m-0" id="status">
                                <%=nextStatus.getStatus()%>
                            </p>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon1">Ghi chú</span>
                            <input id="note-status" type="text" class="form-control" name="note" placeholder="Ghi chú"
                                   aria-label="Note"
                                   aria-describedby="basic-addon1">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="reset" class="btn btn-secondary" id="cancel-update-bill-status"
                                data-bs-dismiss="modal">
                            Hủy
                        </button>
                        <button id="update-bill-status"
                                data-status="<%=nextStatus.getName()%>"
                                data-bill-id="<%=bill.getId()%>"
                                type="button" class="btn"
                                style="background-color: var(--color-blue-origin); color: #FFFFFF">
                            Xác nhận
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <%}%>
    </section>
</main>

<jsp:include page="footer.jsp"/>

<script src="../swal/sweetalert.js"></script>
<script src="../javascript/menu_footer.js"></script>
<script src="../javascript/admin_pages/admin_page.js"></script>
<script src="../javascript/admin_pages/chi_tiet_hoa_don.js"></script>
<script type="text/javascript">
    <%if(user != null){%>
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