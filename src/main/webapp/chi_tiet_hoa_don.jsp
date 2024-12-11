<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.bean.*" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <link rel="stylesheet" href="notify/notify-metro.css"/>
    <link rel="stylesheet" href="css/chi_tiet_hoa_don.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>

    <%--notify--%>
    <script src="notify/notify.js"></script>

    <title>Chi tiết hóa đơn</title>
</head>
<body>
<jsp:include page="header.jsp"/>

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
                <button type="button" id="button-show-dialog-cancel-bill" data-bs-toggle="modal"
                        data-bs-target="#accept-cancel"><i class="fa-solid fa-trash"></i> Hủy đơn hàng
                </button>
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


        <!-- Modal accept-->
        <div class="modal fade" id="accept-cancel" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <img src="images/icon/warning.png" class="me-1" style="width: 25px">
                        <h1 class="modal-title fs-5">Thông báo</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc chắn hủy đơn hàng này không!</p>
                    </div>
                    <div class="modal-footer">
                        <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">Hủy
                        </button>
                        <button id="cancel-bill" bill-id="<%=bill.getId()%>" type="button" class="btn"
                                data-bs-dismiss="modal"
                                style="background-color: var(--color-blue-origin); color: #FFFFFF">OK
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<jsp:include page="footer.jsp"/>

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