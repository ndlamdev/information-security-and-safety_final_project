<%@ page import="com.lamnguyen.mat_kinh_kimi.model.Bill" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.service.CartService" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.lamnguyen.mat_kinh_kimi.model.ProductCart" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    Bill bill = (Bill) session.getAttribute("billPayed");
    CartService cart = (CartService) session.getAttribute("cart");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác nhận đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
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
                    List<ProductCart> productCartList = cart.getAllProductCart();
                    if (productCartList == null || productCartList.isEmpty()) {
                        System.out.println("Danh sách sản phẩm trong giỏ hàng trống!");
                    }
                    assert productCartList != null;
                    int count = 0;
                    for (ProductCart productCart : productCartList) {
                %>
                <tr>
                    <td><%=count += 1%></td>
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
        <button class="btn btn-primary">Tải phần mềm</button>
        <button class="btn btn-secondary">Tải hóa đơn</button>
    </div>

    <!-- Modal nhập chữ ký điện tử -->
    <button class="btn btn-warning mb-3" data-bs-toggle="modal" data-bs-target="#signatureModal">Ký điện tử</button>

    <div class="modal fade" id="signatureModal" tabindex="-1" aria-labelledby="signatureModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="signatureModalLabel">Nhập chữ ký điện tử</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="mb-3">
                            <label for="signature" class="form-label">Chữ ký điện tử</label>
                            <input type="text" class="form-control" id="signature" placeholder="Nhập chữ ký điện tử">
                        </div>
                        <div class="mb-3">
                            <label for="hashAlgorithm" class="form-label">Thuật toán hash</label>
                            <select class="form-select" id="hashAlgorithm">
                                <option value="SHA-256">SHA-256</option>
                                <option value="SHA-512">SHA-512</option>
                                <option value="MD5">MD5</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal">Xác nhận ký</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Để sau</button>
                </div>
            </div>
        </div>
    </div>

    <div class="text-center">
        <a href="gio_hang.jsp" class="btn btn-danger">Hủy đơn hàng</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
