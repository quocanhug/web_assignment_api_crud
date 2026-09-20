package vn.iotstar.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxDemoController {

	@GetMapping(value = { "/", "/ajax" }, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String ajaxDemo() {
		return """
				<!DOCTYPE html>
				<html lang="vi">
				<head>
				    <meta charset="UTF-8">
				    <meta name="viewport" content="width=device-width, initial-scale=1.0">
				    <title>Spring Boot REST API with AJAX - ThS. Nguyễn Hữu Trung</title>
				    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
				    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
				    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
				    <script>var contextPath = "";</script>
				    <style>
				        body { background-color: #f1f5f9; font-family: 'Segoe UI', system-ui, sans-serif; }
				        .hero-banner { background: linear-gradient(135deg, #0f172a 0%, #1e3a8a 50%, #2563eb 100%); color: white; padding: 2.2rem 0; margin-bottom: 2rem; border-radius: 0 0 1.25rem 1.25rem; box-shadow: 0 10px 25px -5px rgba(30, 58, 138, 0.3); }
				        .card { border: none; border-radius: 1rem; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
				        .img-preview { width: 56px; height: 56px; object-fit: cover; border-radius: 8px; border: 1px solid #cbd5e1; }
				        .nav-pills .nav-link.active { background-color: #2563eb; }
				        .nav-pills .nav-link { color: #334155; font-weight: 600; border-radius: 0.5rem; }
				    </style>
				</head>
				<body>
				    <div class="hero-banner text-center">
				        <div class="container">
				            <span class="badge bg-light text-primary px-3 py-2 rounded-pill fw-semibold mb-2">LẬP TRÌNH WEB (WEBPR330479)</span>
				            <h1 class="fw-bold mb-1">Spring Boot RESTful API với AJAX</h1>
				            <p class="mb-3 opacity-75">Giảng viên: ThS. Nguyễn Hữu Trung | CRUD Category & Products (Bài tập thêm)</p>
				            <div>
				                <a href="/swagger-ui.html" target="_blank" class="btn btn-success fw-semibold me-2">
				                    <i class="fas fa-book-open me-2"></i>Mở Swagger UI Docs
				                </a>
				                <a href="/v3/api-docs" target="_blank" class="btn btn-outline-light fw-semibold">
				                    <i class="fas fa-code me-2"></i>OpenAPI JSON
				                </a>
				            </div>
				        </div>
				    </div>

				    <div class="container pb-5">
				        <ul class="nav nav-pills nav-fill mb-4 p-2 bg-white rounded-3 shadow-sm" id="pills-tab" role="tablist">
				            <li class="nav-item" role="presentation">
				                <button class="nav-link active py-2" id="tab-category-btn" data-bs-toggle="pill" data-bs-target="#tab-category" type="button">
				                    <i class="fas fa-folder-open me-2"></i>1. Quản lý Category (API & AJAX)
				                </button>
				            </li>
				            <li class="nav-item" role="presentation">
				                <button class="nav-link py-2" id="tab-product-btn" data-bs-toggle="pill" data-bs-target="#tab-product" type="button">
				                    <i class="fas fa-boxes-stacked me-2"></i>2. Quản lý Products (Bài tập thêm)
				                </button>
				            </li>
				        </ul>

				        <div class="tab-content" id="pills-tabContent">
				            <!-- TAB CATEGORY -->
				            <div class="tab-pane fade show active" id="tab-category" role="tabpanel">
				                <div class="card p-4">
				                    <div class="d-flex justify-content-between align-items-center mb-3">
				                        <h4 class="fw-bold mb-0 text-dark"><i class="fas fa-tags me-2 text-primary"></i>Danh sách Category</h4>
				                        <button class="btn btn-success" onclick="showCreateNewCategoryModal()">
				                            <i class="fas fa-plus me-2"></i>Thêm Category Ajax
				                        </button>
				                    </div>
				                    <div class="table-responsive">
				                        <table class="table table-striped table-hover align-middle" id="categoryTable">
				                            <thead class="table-dark">
				                                <tr>
				                                    <th style="width: 80px;">Id</th>
				                                    <th style="width: 100px;">Icon</th>
				                                    <th>Name</th>
				                                    <th style="width: 150px;" class="text-center">Actions</th>
				                                </tr>
				                            </thead>
				                            <tbody>
				                                <tr><td colspan="4" class="text-center py-4 text-muted">Đang tải danh sách...</td></tr>
				                            </tbody>
				                        </table>
				                    </div>
				                </div>
				            </div>

				            <!-- TAB PRODUCT -->
				            <div class="tab-pane fade" id="tab-product" role="tabpanel">
				                <div class="card p-4">
				                    <div class="d-flex justify-content-between align-items-center mb-3">
				                        <h4 class="fw-bold mb-0 text-dark"><i class="fas fa-box me-2 text-primary"></i>Danh sách Products</h4>
				                        <button class="btn btn-primary" onclick="showCreateNewProductModal()">
				                            <i class="fas fa-plus me-2"></i>Thêm Product Ajax
				                        </button>
				                    </div>
				                    <div class="table-responsive">
				                        <table class="table table-striped table-hover align-middle" id="productTable">
				                            <thead class="table-primary">
				                                <tr>
				                                    <th style="width: 70px;">Id</th>
				                                    <th style="width: 90px;">Image</th>
				                                    <th>Product Name</th>
				                                    <th>Price</th>
				                                    <th>Quantity</th>
				                                    <th>Discount</th>
				                                    <th>Category</th>
				                                    <th style="width: 150px;" class="text-center">Actions</th>
				                                </tr>
				                            </thead>
				                            <tbody>
				                                <tr><td colspan="8" class="text-center py-4 text-muted">Đang tải danh sách...</td></tr>
				                            </tbody>
				                        </table>
				                    </div>
				                </div>
				            </div>
				        </div>
				    </div>

				    <!-- MODAL THÊM CATEGORY -->
				    <div class="modal fade" tabindex="-1" role="dialog" id="createCategoryModal">
				        <div class="modal-dialog modal-dialog-centered" role="document">
				            <div class="modal-content">
				                <form id="addCategory" method="post" onsubmit="return false;" enctype="multipart/form-data">
				                    <div class="modal-header">
				                        <h5 class="modal-title fw-bold"><i class="fas fa-plus-circle me-2 text-success"></i>Add Category</h5>
				                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				                    </div>
				                    <div class="modal-body">
				                        <div class="form-group mb-3">
				                            <label for="new_categoryname" class="form-label fw-semibold">Category Name</label>
				                            <input type="text" class="form-control" id="new_categoryname" name="categoryName" placeholder="Nhập tên category..." required>
				                        </div>
				                        <div class="form-group mb-3">
				                            <label for="new_icon" class="form-label fw-semibold">Icon</label>
				                            <input type="file" class="form-control" id="new_icon" name="icon" accept="image/*">
				                        </div>
				                        <button type="submit" class="btn btn-primary w-100 fw-semibold py-2">Add</button>
				                    </div>
				                    <div class="modal-footer">
				                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
				                    </div>
				                </form>
				            </div>
				        </div>
				    </div>

				    <!-- MODAL CẬP NHẬT CATEGORY -->
				    <div class="modal fade" tabindex="-1" role="dialog" id="updateCategoryInfoModal">
				        <div class="modal-dialog modal-dialog-centered" role="document">
				            <div class="modal-content">
				                <div class="modal-header">
				                    <h5 class="modal-title fw-bold"><i class="fas fa-edit me-2 text-warning"></i>Update Category</h5>
				                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				                </div>
				                <div class="modal-body">
				                    <div class="card mb-3 bg-light">
				                        <div class="card-header bg-white">
				                            <h6 class="mb-0 fw-semibold"><i class="far fa-address-card me-2 text-primary"></i>Category</h6>
				                        </div>
				                        <div class="card-body py-2">
				                            <p id="updateCategoryInfoModalId" class="mb-1 fw-bold"></p>
				                            <p id="updateCategoryInfoModalName" class="mb-1"></p>
				                            <p id="updateCategoryInfoModalIcon" class="mb-1 text-muted small"></p>
				                        </div>
				                    </div>
				                    <form id="updateCategory" method="post" onsubmit="return false;" enctype="multipart/form-data">
				                        <input type="hidden" id="categoryId_up" name="categoryId">
				                        <div class="form-group mb-3">
				                            <label for="categoryName_up" class="form-label fw-semibold">Category Name</label>
				                            <input type="text" class="form-control" id="categoryName_up" name="categoryName" required>
				                        </div>
				                        <div class="form-group mb-3">
				                            <label for="icon_up" class="form-label fw-semibold">Icon mới (tùy chọn)</label>
				                            <input type="file" class="form-control" id="icon_up" name="icon" accept="image/*">
				                        </div>
				                        <button type="submit" class="btn btn-primary w-100 fw-semibold py-2">Cập nhật</button>
				                    </form>
				                </div>
				                <div class="modal-footer">
				                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
				                </div>
				            </div>
				        </div>
				    </div>

				    <!-- MODAL THÊM PRODUCT (BÀI TẬP THÊM) -->
				    <div class="modal fade" tabindex="-1" role="dialog" id="createProductModal">
				        <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
				            <div class="modal-content">
				                <form id="addProduct" method="post" onsubmit="return false;" enctype="multipart/form-data">
				                    <div class="modal-header">
				                        <h5 class="modal-title fw-bold"><i class="fas fa-plus-circle me-2 text-primary"></i>Thêm Product Ajax</h5>
				                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				                    </div>
				                    <div class="modal-body">
				                        <div class="row g-3">
				                            <div class="col-md-8">
				                                <label class="form-label fw-semibold">Product Name</label>
				                                <input type="text" class="form-control" name="productName" id="pro_name" required>
				                            </div>
				                            <div class="col-md-4">
				                                <label class="form-label fw-semibold">Category</label>
				                                <select class="form-select" name="categoryId" id="pro_category" required></select>
				                            </div>
				                            <div class="col-md-4">
				                                <label class="form-label fw-semibold">Price (VNĐ)</label>
				                                <input type="number" step="1000" class="form-control" name="unitPrice" id="pro_price" required>
				                            </div>
				                            <div class="col-md-4">
				                                <label class="form-label fw-semibold">Quantity</label>
				                                <input type="number" class="form-control" name="quantity" id="pro_quantity" value="10" required>
				                            </div>
				                            <div class="col-md-4">
				                                <label class="form-label fw-semibold">Discount (%)</label>
				                                <input type="number" step="0.1" class="form-control" name="discount" id="pro_discount" value="0">
				                            </div>
				                            <div class="col-md-12">
				                                <label class="form-label fw-semibold">Ảnh sản phẩm</label>
				                                <input type="file" class="form-control" name="imageFile" id="pro_image" accept="image/*">
				                            </div>
				                            <div class="col-md-12">
				                                <label class="form-label fw-semibold">Description</label>
				                                <textarea class="form-control" name="description" id="pro_description" rows="2"></textarea>
				                            </div>
				                        </div>
				                        <button type="submit" class="btn btn-primary w-100 fw-semibold mt-4 py-2">Lưu Product</button>
				                    </div>
				                    <div class="modal-footer">
				                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
				                    </div>
				                </form>
				            </div>
				        </div>
				    </div>

				    <!-- MODAL SỬA PRODUCT -->
				    <div class="modal fade" tabindex="-1" role="dialog" id="updateProductModal">
				        <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
				            <div class="modal-content">
				                <form id="updateProduct" method="post" onsubmit="return false;" enctype="multipart/form-data">
				                    <input type="hidden" name="productId" id="pro_up_id">
				                    <div class="modal-header">
				                        <h5 class="modal-title fw-bold"><i class="fas fa-edit me-2 text-warning"></i>Cập nhật Product</h5>
				                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				                    </div>
				                    <div class="modal-body">
				                        <div class="row g-3">
				                            <div class="col-md-8">
				                                <label class="form-label fw-semibold">Product Name</label>
				                                <input type="text" class="form-control" name="productName" id="pro_up_name" required>
				                            </div>
				                            <div class="col-md-4">
				                                <label class="form-label fw-semibold">Category</label>
				                                <select class="form-select" name="categoryId" id="pro_up_category" required></select>
				                            </div>
				                            <div class="col-md-4">
				                                <label class="form-label fw-semibold">Price (VNĐ)</label>
				                                <input type="number" step="1000" class="form-control" name="unitPrice" id="pro_up_price" required>
				                            </div>
				                            <div class="col-md-4">
				                                <label class="form-label fw-semibold">Quantity</label>
				                                <input type="number" class="form-control" name="quantity" id="pro_up_quantity" required>
				                            </div>
				                            <div class="col-md-4">
				                                <label class="form-label fw-semibold">Discount (%)</label>
				                                <input type="number" step="0.1" class="form-control" name="discount" id="pro_up_discount">
				                            </div>
				                            <div class="col-md-12">
				                                <label class="form-label fw-semibold">Ảnh mới (tùy chọn)</label>
				                                <input type="file" class="form-control" name="imageFile" id="pro_up_image" accept="image/*">
				                            </div>
				                            <div class="col-md-12">
				                                <label class="form-label fw-semibold">Description</label>
				                                <textarea class="form-control" name="description" id="pro_up_description" rows="2"></textarea>
				                            </div>
				                        </div>
				                        <button type="submit" class="btn btn-warning w-100 fw-semibold mt-4 py-2">Cập nhật Product</button>
				                    </div>
				                    <div class="modal-footer">
				                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
				                    </div>
				                </form>
				            </div>
				        </div>
				    </div>

				    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
				    <script type="text/javascript">
				        $(document).ready(function() {
				            loadCategories();
				            loadProducts();
				        });

				        /* 1. LẤY DANH SÁCH CATEGORY */
				        function loadCategories() {
				            $.getJSON(contextPath + '/api/category', function(json) {
				                var list = Array.isArray(json) ? json : (json.body || []);
				                var tr = [];
				                var options = '<option value="">-- Chọn Category --</option>';

				                for (var i = 0; i < list.length; i++) {
				                    tr.push('<tr>');
				                    tr.push('<td class="fw-bold">' + list[i].categoryId + '</td>');
				                    var iconHtml = list[i].icon ? '<img src="' + contextPath + '/api/category/icon/' + list[i].icon + '" class="img-preview" alt="icon">' : '<span class="badge bg-secondary">No icon</span>';
				                    tr.push('<td>' + iconHtml + '</td>');
				                    tr.push('<td class="fw-semibold">' + list[i].categoryName + '</td>');
				                    tr.push('<td class="text-center">'
				                        + '<a href="#" data-id="' + list[i].categoryId + '" data-name="' + escape(list[i].categoryName) + '" data-icon="' + (list[i].icon || '') + '" id="editcate" class="btn btn-outline-warning btn-sm me-2"><i class="fa fa-edit"></i></a>'
				                        + '<a href="#" data-id="' + list[i].categoryId + '" id="categoryId" class="btn btn-outline-danger btn-sm"><i class="fa fa-trash"></i></a>'
				                        + '</td>');
				                    tr.push('</tr>');

				                    options += '<option value="' + list[i].categoryId + '">' + list[i].categoryName + '</option>';
				                }

				                $('#categoryTable tbody').html(tr.length ? tr.join('') : '<tr><td colspan="4" class="text-center text-muted py-4">Chưa có Category nào.</td></tr>');
				                $('#pro_category').html(options);
				                $('#pro_up_category').html(options);
				            });
				        }

				        /* 2. THÊM CATEGORY */
				        function showCreateNewCategoryModal() {
				            $('#new_categoryname').val('');
				            $('#new_icon').val('');
				            var modal = new bootstrap.Modal(document.getElementById('createCategoryModal'));
				            modal.show();
				        }

				        $("form#addCategory").submit(function(e) {
				            e.preventDefault();
				            var formData = new FormData(this);
				            $.ajax({
				                url: contextPath + '/api/category/addCategory',
				                type: 'POST',
				                dataType: "json",
				                data: formData,
				                success: function (data) {
				                    bootstrap.Modal.getInstance(document.getElementById('createCategoryModal')).hide();
				                    loadCategories();
				                },
				                error: function(err) {
				                    alert(err.responseText || 'Lỗi thêm Category!');
				                },
				                cache: false,
				                contentType: false,
				                processData: false
				            });
				        });

				        /* 3. HIỂN THỊ MODAL CẬP NHẬT CATEGORY */
				        $(document).on('click', '#editcate', function(e) {
				            e.preventDefault();
				            var categoryId = $(this).data('id');
				            var categoryName = unescape($(this).data('name'));
				            var icon = $(this).data('icon');

				            $('#updateCategoryInfoModalId').text("Category ID: " + categoryId);
				            $('#updateCategoryInfoModalName').text("Category Name: " + categoryName);
				            $('#updateCategoryInfoModalIcon').text('Icon: ' + (icon || 'Không có'));

				            $('#categoryName_up').val(categoryName);
				            $('#categoryId_up').val(categoryId);
				            $('#icon_up').val('');

				            var modal = new bootstrap.Modal(document.getElementById('updateCategoryInfoModal'));
				            modal.show();
				        });

				        $("form#updateCategory").submit(function(e) {
				            e.preventDefault();
				            var formData = new FormData(this);
				            $.ajax({
				                url: contextPath + '/api/category/updateCategory',
				                type: 'PUT',
				                dataType: "json",
				                data: formData,
				                success: function (data) {
				                    bootstrap.Modal.getInstance(document.getElementById('updateCategoryInfoModal')).hide();
				                    loadCategories();
				                },
				                error: function(err) {
				                    alert(err.responseText || 'Lỗi cập nhật Category!');
				                },
				                cache: false,
				                contentType: false,
				                processData: false
				            });
				        });

				        /* 4. XÓA CATEGORY */
				        $(document).delegate('#categoryId', 'click', function(e) {
				            e.preventDefault();
				            var id = $(this).data('id');
				            if (confirm('Do you really want to delete record?')) {
				                var parent = $(this).closest('tr');
				                $.ajax({
				                    type: "DELETE",
				                    url: contextPath + '/api/category/deleteCategory?categoryId=' + id,
				                    dataType: "json",
				                    success: function() {
				                        parent.fadeOut('slow', function() {
				                            $(this).remove();
				                        });
				                        loadCategories();
				                    },
				                    error: function(err) {
				                        alert('Lỗi khi xóa Category: ' + (err.responseText || 'Không thể xóa'));
				                    }
				                });
				            }
				        });

				        /* 5. LẤY DANH SÁCH PRODUCT (BÀI TẬP THÊM) */
				        function loadProducts() {
				            $.getJSON(contextPath + '/api/product', function(res) {
				                var list = res.body || [];
				                var tr = [];
				                for (var i = 0; i < list.length; i++) {
				                    tr.push('<tr>');
				                    tr.push('<td class="fw-bold">' + list[i].productId + '</td>');
				                    var imgHtml = list[i].images ? '<img src="' + contextPath + '/api/product/image/' + list[i].images + '" class="img-preview" alt="product">' : '<span class="badge bg-secondary">No img</span>';
				                    tr.push('<td>' + imgHtml + '</td>');
				                    tr.push('<td class="fw-semibold">' + list[i].productName + '</td>');
				                    tr.push('<td class="text-primary fw-bold">' + (list[i].unitPrice ? list[i].unitPrice.toLocaleString('vi-VN') + ' đ' : '0') + '</td>');
				                    tr.push('<td><span class="badge bg-info text-dark">' + (list[i].quantity || 0) + '</span></td>');
				                    tr.push('<td>' + (list[i].discount || 0) + '%</td>');
				                    tr.push('<td>' + (list[i].category ? '<span class="badge bg-secondary">' + list[i].category.categoryName + '</span>' : 'N/A') + '</td>');
				                    tr.push('<td class="text-center">'
				                        + '<button class="btn btn-outline-warning btn-sm me-2" onclick="editProduct(' + list[i].productId + ')"><i class="fa fa-edit"></i></button>'
				                        + '<button class="btn btn-outline-danger btn-sm" onclick="deleteProduct(' + list[i].productId + ')"><i class="fa fa-trash"></i></button>'
				                        + '</td>');
				                    tr.push('</tr>');
				                }
				                $('#productTable tbody').html(tr.length ? tr.join('') : '<tr><td colspan="8" class="text-center text-muted py-4">Chưa có Product nào.</td></tr>');
				            });
				        }

				        /* 6. THÊM PRODUCT */
				        function showCreateNewProductModal() {
				            $('#addProduct')[0].reset();
				            var modal = new bootstrap.Modal(document.getElementById('createProductModal'));
				            modal.show();
				        }

				        $("form#addProduct").submit(function(e) {
				            e.preventDefault();
				            var formData = new FormData(this);
				            $.ajax({
				                url: contextPath + '/api/product/addProduct',
				                type: 'POST',
				                dataType: "json",
				                data: formData,
				                success: function (data) {
				                    bootstrap.Modal.getInstance(document.getElementById('createProductModal')).hide();
				                    loadProducts();
				                },
				                error: function(err) {
				                    alert(err.responseJSON ? err.responseJSON.message : 'Lỗi thêm Product!');
				                },
				                cache: false,
				                contentType: false,
				                processData: false
				            });
				        });

				        /* 7. SỬA PRODUCT */
				        function editProduct(id) {
				            $.getJSON(contextPath + '/api/product/' + id, function(res) {
				                if (res.status && res.body) {
				                    var p = res.body;
				                    $('#pro_up_id').val(p.productId);
				                    $('#pro_up_name').val(p.productName);
				                    $('#pro_up_price').val(p.unitPrice);
				                    $('#pro_up_quantity').val(p.quantity);
				                    $('#pro_up_discount').val(p.discount);
				                    $('#pro_up_description').val(p.description);
				                    if (p.category) {
				                        $('#pro_up_category').val(p.category.categoryId);
				                    }
				                    var modal = new bootstrap.Modal(document.getElementById('updateProductModal'));
				                    modal.show();
				                }
				            });
				        }

				        $("form#updateProduct").submit(function(e) {
				            e.preventDefault();
				            var formData = new FormData(this);
				            $.ajax({
				                url: contextPath + '/api/product/updateProduct',
				                type: 'PUT',
				                dataType: "json",
				                data: formData,
				                success: function (data) {
				                    bootstrap.Modal.getInstance(document.getElementById('updateProductModal')).hide();
				                    loadProducts();
				                },
				                error: function(err) {
				                    alert(err.responseJSON ? err.responseJSON.message : 'Lỗi cập nhật Product!');
				                },
				                cache: false,
				                contentType: false,
				                processData: false
				            });
				        });

				        /* 8. XÓA PRODUCT */
				        function deleteProduct(id) {
				            if (confirm('Bạn có chắc chắn muốn xóa Product #' + id + ' không?')) {
				                $.ajax({
				                    url: contextPath + '/api/product/deleteProduct?productId=' + id,
				                    type: 'DELETE',
				                    success: function() {
				                        loadProducts();
				                    },
				                    error: function(err) {
				                        alert('Lỗi xóa Product!');
				                    }
				                });
				            }
				        }
				    </script>
				</body>
				</html>
				""";
	}
}
