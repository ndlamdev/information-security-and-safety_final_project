<%@ page import="java.time.LocalDate" %>
<%@ page import="model.bean.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% BannerImage logo = (BannerImage) session.getAttribute("logo");%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap-grid.css">
    <link rel="stylesheet" href="../bootstrap-5.3.2-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../fontawesome-free-6.4.2-web/css/all.css">
    <link rel="stylesheet" href="../css/menu_footer.css">
    <link href="../select2/select2.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="../css/them_san_pham.css">
    <link rel="icon" href="../<%=logo.getUrlImage()%>">


    <script src="../bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
    <script src="../jquery/jquery-3.7.1.slim.min.js"></script>
    <script src="../jquery/jquery-3.7.1.min.js"></script>
    <script src="../select2/select2.min.js"></script>
    <script src="../ckeditor/ckeditor.js"></script>
    <script src="../ckfinder/ckfinder.js"></script>

    <title>Chỉnh sửa sản phẩm</title>
</head>
<body>
<header id="menu">
    <nav class="navbar navbar-expand-lg pb-0">
        <div class="container-xxl m-md-auto mt-2">
            <div class="row">
                <div class="logo col-lg-2 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                    <a href="quan_ly_tai_khoan.jsp" class="navbar-brand me-5">
                        <img src="../<%=logo.getUrlImage()%>" alt="logo.png">
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
                    <a href="../dang_nhap.jsp">
                        <button type="button" class="btn d-flex float-lg-end  me-xl-4 me-lg-2">
                            <span class="d-lg-inline d-md-none d-sm-none">Đăng nhập</span>
                            <span class="d-lg-none d-md-line material-symbols-outlined ms-1">login</span>
                        </button>
                    </a>
                </div>
                <div class="sign-up col-lg-1 col-md-1 col-sm-1 border-0 px-lg-0">
                    <a href="../dang_ky.jsp">
                        <button type="button" class="btn d-flex float-lg-none">
                            <span class="d-lg-inline d-md-none  d-sm-none">Đăng ký</span>
                            <span class="d-lg-none d-md-line material-symbols-outlined ms-1">logout</span>
                        </button>
                    </a>
                </div>
                <div class="menu-product col-lg-12 col-md-1 col-sm-1">
                    <!--Icon 3 dấu gạch mang hiển thị menu-->
                    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas"
                            data-bs-target="#offcanvasNavbarAdmin"
                            aria-controls="offcanvasNavbarAdmin" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <!--Các mục trong menu-->
                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbarAdmin"
                         aria-labelledby="offcanvasNavbarAdminLabel">
                        <div class="offcanvas-body">
                            <ul class="navbar-nav m-auto">
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="dashboard.jsp" class="nav-link px-4 rounded">DashBoard</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_tai_khoan.jsp" class="nav-link px-4 rounded">Quản lý tài khoản</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_san_pham.jsp" class="nav-link px-4 rounded">Quản lý sản
                                        phẩm</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_hoa_don.jsp" class="nav-link px-4 rounded">Quản lý hóa đơn</a>
                                </li>
                                <li class="nav-item dropdown pe-lg-5 pe-md-0">
                                    <a href="quan_ly_banner.jsp" class="nav-link px-4 rounded">Quản lý banner</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>

<main id="main" class=" mt-5 pb-5">
    <%
        Product productEdit = (Product) session.getAttribute("product-edit");
        productEdit = productEdit == null ? new Product() : productEdit;
    %>
    <div class="container position-relative" id="product-id" product-id="<%=session.getAttribute("product-id")%>">
        <section class="left">
            <div class="input-info-product input">
                <h4 class="input-info-product-title title">
                    Thông tin sản phẩm
                </h4>

                <div class="input-info-product-body mb-4">
                    <div class="row mb-4">
                        <div class="col">
                            <input autocomplete="false" type="text" class="w-100" name="name-product" id="product-name"
                                   placeholder="Tên sản phẩm" required value="<%=productEdit.getName()%>">
                            <small class="text-danger" hidden="">Tên sản phẩm không được bỏ trống!</small>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <select class="w-100" type="text" name="product-category-id" id="product-category-id"
                                    required>
                                <optgroup label="Kính mát">
                                    <option value="1">Kính mát nam</option>
                                    <option value="2">Kính mát nữ</option>
                                    <option value="3">Kính đi ngày và đêm</option>
                                    <option value="4">Kinh đổi màu</option>
                                    <option value="5">Kính lọc ánh sánh xanh</option>
                                    <option value="6">Kính mắt clip on 2 lớp</option>
                                </optgroup>
                                <optgroup label="Mắt kính trẻ em">
                                    <option value="7">Gọng kính trẻ em</option>
                                    <option value="8">Kính mát trẻ em</option>
                                </optgroup>
                                <optgroup label="Gọng kính">
                                    <option value="9">Gọng kính nữa khung</option>
                                    <option value="10">Gọng kính khoan</option>
                                    <option value="11">Gọng kính tròn</option>
                                    <option value="12">Gọng kính titan</option>
                                </optgroup>
                                <optgroup label="Tròng kính">
                                    <option value="13">Tròng kính chống ánh sáng xanh</option>
                                    <option value="14">Tròng kính đổi màu</option>
                                    <option value="15">Tròng kính màu</option>
                                    <option value="16">Tròng kính cho gọng khoan</option>
                                </optgroup>
                            </select>
                            <small class="text-danger" hidden="">Vui lòng chọn danh mục cho sản phẩm!</small>
                        </div>
                    </div>
                </div>

                <div class="input-expanded-info-product">
                    <textarea id="editor" name="editor"></textarea>
                </div>
            </div>

            <div class="input-product-image input">
                <h4 class="input-product-image-title title">
                    Ảnh sản phẩm
                </h4>

                <div class="input-product-image-body" id="input-product-image-body">
                    <%
                        for (ProductImage productImage : productEdit.getProductImages()) {
                            String url = productImage.getUrlImage();
                    %>
                    <div class="product-image" product-image-id="<%=productImage.getId()%>">
                        <img src="../<%=url%>" alt="image-product.png">
                        <button type="button" path-file="<%=url%>" class="text-danger cancel">x</button>
                    </div>
                    <%}%>
                    <div class="input" id="input-img">
                        <label for="input-product-image" class="d-"><span
                                class="material-symbols-outlined">cloud_upload</span></label>
                        <input type="file" name="input-product-image" id="input-product-image"
                               accept="image/jpeg,image/png" hidden="">
                    </div>
                </div>
                <small class="text-danger ps-4 error-product-image" hidden="">Vui lòng thêm tối thiểu 2 hình ảnh cho sản phẩm của
                    bạn!</small>
            </div>

            <div class="input-option-product input">
                <h4 class="input-option-product-title title">
                    Mẫu
                </h4>

                <div class="input-option-product-body" id="frame-input-option-product">
                    <%
                        for (int indexModel = 0; indexModel < productEdit.getModels().size(); indexModel++) {
                            Model model = productEdit.getModels().get(indexModel);
                    %>
                    <div class="row a-input-option-product align-items-center mb-2 model" model-id="<%=model.getId()%>">
                        <div class="col-2 text-center">
                            <img src="../<%=model.getUrlImage()%>" alt="hinh_anh.png">
                        </div>
                        <div class="col-3">
                            <label>Tên mẫu</label>
                            <input class="model-name" type="Text" required name="model-name" placeholder="Tên mẫu"
                                   value="<%=model.getName()%>">
                            <small hidden="" class="text-danger">Tên mẫu không được bỏ trống!</small>
                        </div>
                        <div class="col-3">
                            <label>Số lượng</label>
                            <input class="model-quantity" type="number" required name="model-quantity"
                                   placeholder="Số lượng mẫu" value="<%=model.getQuantity()%>">
                            <small hidden="" class="text-danger">Số lượng mẫu không được bỏ trống!</small>
                        </div>
                        <div class="col-3">
                            <label>Lựa chọn hình</label>
                            <select class="select-img-option-product model-url-iamge" name="model-url-iamge">
                                <%
                                    for (int indexProductImage = 0; indexProductImage < productEdit.getProductImages().size(); indexProductImage++) {
                                        String url = productEdit.getProductImages().get(indexProductImage).getUrlImage();
                                %>
                                <option value="../<%=url%>" <%if(model.getUrlImage().equals(url)) {%>selected<%}%>>
                                    Hình <%=indexProductImage%>
                                </option>
                                <%}%>
                            </select>
                            <small hidden="" class="text-danger">Vui lòng chọn hình cho mẫu!</small>
                        </div>
                        <%if (indexModel != 0) {%>
                        <button type="button" class="mx-auto cancel bg-danger rounded col-1">x</button>
                        <%}%>
                    </div>
                    <%
                        }
                        if (productEdit.getModels().isEmpty()) {
                    %>
                    <div class="row a-input-option-product align-items-center mb-2 model">
                        <div class="col-2 text-center">
                            <img src="../images/avatar/default_avatar.png" alt="hinh_anh.png">
                        </div>
                        <div class="col-3">
                            <label>Tên mẫu</label>
                            <input class="model-name" type="Text" required name="model-name"
                                   placeholder="Tên mẫu"
                                   value="Mặc định">
                            <small hidden="" class="text-danger">Tên mẫu không được bỏ trống!</small>
                        </div>
                        <div class="col-3">
                            <label>Số lượng</label>
                            <input class="model-quantity" type="number" required name="model-quantity"
                                   placeholder="Số lượng mẫu">
                            <small hidden="" class="text-danger">Số lượng mẫu không được bỏ trống!</small>
                        </div>
                        <div class="col-3">
                            <label>Lựa chọn hình</label>
                            <select class="select-img-option-product model-url-iamge"
                                    name="model-url-iamge">
                                <option value="../images/avatar/default_avatar.png">Mặc định</option>
                            </select>
                            <small hidden="" class="text-danger">Vui lòng chọn hình cho mẫu!</small>
                        </div>
                    </div>
                    <%}%>
                    <div id="input-product-model"></div>
                </div>
                <small hidden="" class="text-danger ps-4 error-input-model">Vui lòng thêm mẫu cho sản phẩm của
                    bạn!</small>

                <div class="input-option-product-footer">
                    <button type="button" class="btn button-add" id="add-model">
                        Thêm sự lựa chọn sản phẩm
                    </button>
                </div>
            </div>
        </section>
        <section class="right">
            <div class="input-price-product input">
                <h4 class="input-price-product-title title">
                    Giá sản phẩm
                </h4>

                <div class="input-price-product-body">
                    <input type="number" class="w-100 mb-0" name="price-product" id="price-product"
                           placeholder="Giá sản phẩm" required value="<%=productEdit.getPrice()%>">
                    <small hidden="" class="text-danger">Giá sản phẩm không được bỏ trống!</small>
                    <div class="list-sale-product">
                        <%for (ProductDiscount productDiscount : productEdit.getProductDiscounts()) {%>
                        <div class="sale-product product-discounts" product-discount-id="<%=productDiscount.getId()%>">
                            <hr>
                            <div class="row d-flex">
                                <div class="col-10">
                                    <input type="number" class="w-100 price-percentage" name="pricePercentage"
                                           placeholder="chiết khấu" required
                                           value="<%=productDiscount.getPricePercentage()%>">
                                    <small hidden="" class="text-danger">Phần trăm giá giảm không được bỏ trống!</small>
                                </div>
                                <div class="col-2">
                                    <button type="button" class="w-100 h-100 cancel bg-danger rounded col-1" product-discount-id="<%=productDiscount.getId()%>">x</button>
                                </div>
                            </div>
                            <div class="input-date-sale-product d-flex row align-items-center align-items-center">
                                <div class="col-5 pe-0">
                                    <input required type="date" class="w-100 mb-0 ps-1 date-start"
                                           name="date-start-discount"
                                           value="<%=productDiscount.getDateStart().toLocalDate()%>">
                                    <small hidden="" class="text-danger">Ngày bắt đầu không được bỏ trống!</small>
                                </div>
                                <div class="col-2 px-0 mx-0 text-center">-</div>
                                <div class="col-5 ps-0">
                                    <input required type="date" class="w-100 mb-0 ps-1 date-end"
                                           name="date-end-discount"
                                           value="<%=productDiscount.getDateEnd().toLocalDate()%>">
                                    <small hidden="" class="text-danger">Ngày kết thúc không được bỏ trống!</small>
                                </div>
                            </div>
                        </div>
                        <%}%>
                        <div id="input-product-discount"></div>
                    </div>
                </div>

                <hr>
                <div class="input-price-product-footer mt-3">
                    <button type="button" class="btn button-add" id="add-product-discount">Thêm giảm giá</button>
                </div>
            </div>

            <div class="input-filter-product input">
                <h4 class="input-filter-product-title title">
                    Bộ lọc sản phẩm
                </h4>

                <div class="input-filter-product-body">
                    <select class="w-100" data-tags="true" data-placeholder="Lựa chọn thương hiệu!"
                            name="select-brand-product" id="select-brand-product">
                        <option value=""></option>
                    </select>
                    <small hidden="" class="text-danger error-select-brand-product">Vui lòng chọn thương hiệu cho sản
                        phẩm!</small>
                    <select class="w-100" data-tags="true" data-placeholder="Lựa chọn chất liệu!"
                            name="select-material-product" id="select-material-product">
                        <option value=""></option>
                    </select>
                    <small hidden="" class="text-danger error-select-material-product">Vui lòng chọn chất liệu cho sản
                        phẩm!</small>
                    <select class="w-100" data-tags="true" data-placeholder="Lựa chọn kiểu!"
                            name="select-type-product" id="select-type-product">
                        <option value=""></option>
                    </select>
                    <small hidden="" class="text-danger error-select-type-product">Vui lòng chọn kiểu cho sản
                        phẩm!</small>
                </div>
            </div>

            <div class="button-action row">
                <div class="col-6">
                    <button class="w-100  py-2 text-light rounded" id="<%=session.getAttribute("id-button-cancel")%>">
                        Hủy
                    </button>
                </div>
                <div class="col-6">
                    <button class="w-100 rounded py-2 text-light" action="<%=session.getAttribute("action-submit")%>" id="submit" type="button">Lưu</button>
                </div>
            </div>
        </section>
    </div>
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
        </div>
        <div class="row footer-bot text-center border-3">
            <div class="logo col-lg-3 col-md-2 col-sm-2 border-0 px-lg-0 px-md-5">
                <a href="quan_ly_tai_khoan.jsp">
                    <img src="../<%=logo.getUrlImage()%>" alt="logo.png">
                    <span>KIMI</span>
                </a>
            </div>
            <div class="col"><span>© 2023 - Tất cả các quyền thuộc về KIMI</span></div>
        </div>
    </div>
</footer>

<script src="../javascript/menu_footer.js"></script>
<script src="../javascript/admin_pages/admin_page.js"></script>
<script src="../javascript/admin_pages/form_des.js"></script>
<%if (productEdit.getDescribe() != null) {%>
<script type="text/javascript">
    localStorage.setItem("describe", `<%=productEdit.getDescribe()%>`);
    const optionProductCategory = $("#product-category-id").find("option");
    optionProductCategory.each(function (option) {
        if ($(this).text() === "<%=productEdit.getBrandName()%>") $(this).attr("selected", "selected");
    });
</script>
<%}%>
<script src="../javascript/admin_pages/chinh_sua_san_pham.js"></script>
<script type="text/javascript">
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

    $(document).ready(function (){
        initSelectFilter("get-brands", $('#select-brand-product'), "<%=productEdit.getBrandName()%>");
        initSelectFilter("get-materials", $('#select-material-product'), "<%=productEdit.getMaterial()%>");
        initSelectFilter("get-types", $('#select-type-product'), "<%=productEdit.getType()%>");
    });
</script>
</body>
</html>