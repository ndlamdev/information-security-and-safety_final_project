<%@ page import="com.lamnguyen.mat_kinh_kimi.model.Bill" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.ProductCart" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.User" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.BannerImage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% BannerImage logo = (BannerImage) session.getAttribute("logo");%>
<%
    Bill bill = (Bill) session.getAttribute("billPayed");
    CartService cart = (CartService) session.getAttribute("cart");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="css/menu_footer.css">
    <link rel="stylesheet" href="css/thanh_toan_thanh_cong.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">

    <script src="jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="jquery/jquery-3.7.1.min.js"></script>
    <title>Xác nhận đơn hàng</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<main id="main" class="mt-5 pb-5 container w-75">
    <h2 class="text-center mb-4">Xác nhận đơn hàng</h2>

    <div class="card mb-4">
        <div class="card-header text-white" style="background-color: #2f189a;">Thông tin hóa đơn</div>
        <div class="card-body">
            <p><strong>Họ và tên:</strong> <%=bill.getUserName()%>
            </p>
            <p><strong>Email:</strong> <%=bill.getEmail()%>
            </p>
            <p><strong>Số điện thoại:</strong> <%=bill.getPhoneNumber()%>
            </p>
            <p><strong>Địa chỉ:</strong>
                <%=request.getAttribute("addressDetails")%>
            </p>
        </div>
    </div>

    <!-- Thông tin đơn hàng -->
    <div class="card mb-4">
        <div class="card-header text-white" style="background-color: #2F189A">Thông tin đơn hàng</div>
        <div class="card-body">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                    <th>Thành tiền</th>
                </tr>
                </thead>
                <tbody>
                <%
                    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
                    List<ProductCart> productCartList = (List<ProductCart>) session.getAttribute("products");
                    if (productCartList == null || productCartList.isEmpty()) {
                        System.out.println("Danh sách sản phẩm trong giỏ hàng trống!");
                    }
                    assert productCartList != null;
                    int count = 0;
                    for (ProductCart productCart : productCartList) {
                %>
                <tr>
                    <td><%=count += 1%>
                    </td>
                    <td><%=productCart.getName()%>
                    </td>
                    <td><%=productCart.getQuantity()%>
                    </td>
                    <td>
                        <fmt:formatNumber value="<%=productCart.getPrice()%>" type="currency" currencySymbol="₫"/>
                    </td>
                    <td>
                        <fmt:formatNumber value="<%=productCart.getPrice() * productCart.getQuantity()%>"
                                          type="currency" currencySymbol="₫"/>
                    </td>
                </tr>
                <%}%>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="4" class="text-end">Tổng cộng:</th>
                    <th>
                        <fmt:formatNumber value="<%=bill.totalBill()  %>" type="currency" currencySymbol="₫"/>
                    </th>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>

    <!-- Các nút hành động -->
    <div class="text-center mb-3">
        <a class="btn btn-primary text-decoration-none"
           href="<%=System.getProperty("os.name").contains("Windows") ? "./tool/Kimi Sign Tool-1.0.exe" : "./tool/kimi-sign-tool_1.0_amd64.deb"%>"
           download="<%=System.getProperty("os.name").contains("Windows") ? "Kimi Sign Tool-1.0.exe" : "kimi-sign-tool_1.0_amd64.deb"%>"
        >
            Tải phần mềm
        </a>
        <a class="btn btn-secondary text-decoration-none"
           href="./doc/bills/<%=session.getAttribute("file")%>"
           download="bill_<%=bill.getId()%>_<%=LocalDateTime.now().toString()%>.pdf">
            Tải hóa đơn
        </a>
    </div>

    <!-- Modal nhập chữ ký điện tử -->
    <button class="btn btn-warning mb-3" data-bs-toggle="modal" data-bs-target="#signatureModal">Ký điện tử</button>

    <div class="modal fade" id="signatureModal" tabindex="-1" aria-labelledby="signatureModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <form method="post" action="confirm_pay">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="signatureModalLabel">Nhập chữ ký điện tử</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="signature" class="form-label">Chữ ký điện tử</label>
                            <input type="text" required class="form-control" id="signature"
                                   placeholder="Nhập chữ ký điện tử">
                        </div>
                        <div class="mb-3">
                            <label for="hashAlgorithm" class="form-label">Thuật toán hash</label>
                            <select class="form-select" id="hashAlgorithm">
                                <option value="SHA-256">SHA-256</option>
                                <option value="SHA-512">SHA-512</option>
                                <option value="MD5">MD5</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" typeof="submit">
                            Xác nhận ký
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="text-center">
        <a href="<%=session.getAttribute("back")%>" class="btn btn-danger">Hủy đơn hàng</a>
    </div>
</main>

<jsp:include page="footer.jsp"/>

<script src="javascript/menu_footer.js"></script>
<script src="javascript/xac_nhan_chu_ky.js"></script>
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
</script>
</body>
</html>
