<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AJAX Category & Product Management</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" />
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script>var contextPath = "${pageContext.request.contextPath}";</script>
</head>
<body class="p-4">
    <div class="container">
        <h2 class="mb-4 text-center">Quản lý Category & Product với RESTful API và AJAX</h2>
        
        <!-- ================= CATEGORY SECTION ================= -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3>Danh sách Category</h3>
            <button class="btn btn-success" onclick="showCreateNewCategoryModal()">
                <i class="fas fa-plus me-2"></i>Thêm Category Ajax
            </button>
        </div>

        <table class="table table-striped table-bordered" id="categoryTable">
            <thead class="table-dark">
                <tr>
                    <th>Id</th>
                    <th>icon</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Load by Ajax -->
            </tbody>
        </table>

        <!-- ================= PRODUCT SECTION ================= -->
        <div class="d-flex justify-content-between align-items-center mt-5 mb-3">
            <h3>Danh sách Products (Bài tập thêm)</h3>
            <button class="btn btn-primary" onclick="showCreateNewProductModal()">
                <i class="fas fa-plus me-2"></i>Thêm Product Ajax
            </button>
        </div>

        <table class="table table-striped table-bordered" id="productTable">
            <thead class="table-primary">
                <tr>
                    <th>Id</th>
                    <th>Image</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Discount</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Load by Ajax -->
            </tbody>
        </table>
    </div>

    <!-- Modal Thêm Category -->
    <div class="modal fade" tabindex="-1" role="dialog" id="createCategoryModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form id="addCategory" method="post" onsubmit="return false;" enctype="multipart/form-data">
                    <div class="modal-header">
                        <h5 class="modal-title">Add Category</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group mb-3">
                            <label for="new_categoryname" class="form-label">Category Name</label>
                            <input type="text" class="form-control" id="new_categoryname" name="categoryName" required>
                        </div>
                        <div class="form-group mb-3">
                            <label for="new_icon" class="form-label">Icon</label>
                            <input type="file" class="form-control" id="new_icon" name="icon">
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary w-100">Add</button>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal Cập nhật Category -->
    <div class="modal fade" tabindex="-1" role="dialog" id="updateCategoryInfoModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Update Category</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="card mb-3">
                        <div class="card-header">
                            <h6 class="mb-0"><i class="far fa-address-card me-1"></i>Category Info</h6>
                        </div>
                        <div class="card-body pb-1">
                            <p id="updateCategoryInfoModalId" class="mb-1"></p>
                            <p id="updateCategoryInfoModalName" class="mb-1"></p>
                            <p id="updateCategoryInfoModalIcon" class="mb-1"></p>
                        </div>
                    </div>
                    <form id="updateCategory" method="post" onsubmit="return false;" enctype="multipart/form-data">
                        <input type="hidden" id="categoryId_up" name="categoryId">
                        <div class="form-group mb-3">
                            <label for="categoryName_up" class="form-label">Category Name</label>
                            <input type="text" class="form-control" id="categoryName_up" name="categoryName" required>
                        </div>
                        <div class="form-group mb-3">
                            <label for="icon_up" class="form-label">Icon</label>
                            <input type="file" class="form-control" id="icon_up" name="icon">
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary w-100">Cập nhật</button>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            loadCategories();
            loadProducts();
        });

        function loadCategories() {
            $.getJSON((contextPath || '') + '/api/category', function(json) {
                var list = Array.isArray(json) ? json : (json.body || []);
                var tr = [];
                for (var i = 0; i < list.length; i++) {
                    tr.push('<tr>');
                    tr.push('<td>' + list[i].categoryId + '</td>');
                    var iconSrc = list[i].icon ? ((contextPath || '') + '/api/category/icon/' + list[i].icon) : '';
                    tr.push('<td>' + (iconSrc ? '<img src="' + iconSrc + '" style="width:50px; height:50px; object-fit:cover;" class="img-thumbnail" alt="">' : 'N/A') + '</td>');
                    tr.push('<td>' + list[i].categoryName + '</td>');
                    tr.push('<td>' 
                        + '<a href="#" data-id="' + list[i].categoryId + '" data-name="' + escape(list[i].categoryName) + '" data-icon="' + (list[i].icon || '') + '" id="editcate" class="btn btn-outline-warning btn-sm me-2"><i class="fa fa-edit"></i></a>' 
                        + '<a href="#" data-id="' + list[i].categoryId + '" id="deletecate" class="btn btn-outline-danger btn-sm"><i class="fa fa-trash"></i></a>'
                        + '</td>');
                    tr.push('</tr>');
                }
                $('#categoryTable tbody').html(tr.join(''));
            });
        }

        function showCreateNewCategoryModal() {
            $('#new_categoryname')[0].value = '';
            $('#new_icon').val('');
            var modal = new bootstrap.Modal(document.getElementById('createCategoryModal'));
            modal.show();
        }

        $("form#addCategory").submit(function(e) {
            e.preventDefault();
            var formData = new FormData(this);
            $.ajax({
                url: (contextPath || '') + '/api/category/addCategory',
                type: 'POST',
                dataType: "json",
                data: formData,
                success: function (data) {
                    bootstrap.Modal.getInstance(document.getElementById('createCategoryModal')).hide();
                    loadCategories();
                },
                cache: false,
                contentType: false,
                processData: false
            });
        });

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

            var modal = new bootstrap.Modal(document.getElementById('updateCategoryInfoModal'));
            modal.show();
        });

        $("form#updateCategory").submit(function(e) {
            e.preventDefault();
            var formData = new FormData(this);
            $.ajax({
                url: (contextPath || '') + '/api/category/updateCategory',
                type: 'PUT',
                dataType: "json",
                data: formData,
                success: function (data) {
                    bootstrap.Modal.getInstance(document.getElementById('updateCategoryInfoModal')).hide();
                    loadCategories();
                },
                cache: false,
                contentType: false,
                processData: false
            });
        });

        $(document).on('click', '#deletecate', function(e) {
            e.preventDefault();
            var id = $(this).data('id');
            if (confirm('Do you really want to delete record?')) {
                var parent = $(this).closest('tr');
                $.ajax({
                    type: "DELETE",
                    url: (contextPath || '') + '/api/category/deleteCategory?categoryId=' + id,
                    dataType: "json",
                    success: function() {
                        parent.fadeOut('slow', function() {
                            $(this).remove();
                        });
                    }
                });
            }
        });

        function loadProducts() {
            $.getJSON((contextPath || '') + '/api/product', function(res) {
                var list = res.body || [];
                var tr = [];
                for (var i = 0; i < list.length; i++) {
                    tr.push('<tr>');
                    tr.push('<td>' + list[i].productId + '</td>');
                    var imgSrc = list[i].images ? ((contextPath || '') + '/api/product/image/' + list[i].images) : '';
                    tr.push('<td>' + (imgSrc ? '<img src="' + imgSrc + '" style="width:50px; height:50px; object-fit:cover;" class="img-thumbnail" alt="">' : 'N/A') + '</td>');
                    tr.push('<td>' + list[i].productName + '</td>');
                    tr.push('<td>' + (list[i].unitPrice ? list[i].unitPrice.toLocaleString() + ' đ' : '0') + '</td>');
                    tr.push('<td>' + (list[i].quantity || 0) + '</td>');
                    tr.push('<td>' + (list[i].discount || 0) + '%</td>');
                    tr.push('<td>' + (list[i].category ? list[i].category.categoryName : 'N/A') + '</td>');
                    tr.push('<td><button class="btn btn-outline-danger btn-sm" onclick="deleteProduct(' + list[i].productId + ')"><i class="fa fa-trash"></i></button></td>');
                    tr.push('</tr>');
                }
                $('#productTable tbody').html(tr.join(''));
            });
        }

        function deleteProduct(id) {
            if (confirm('Bạn có chắc muốn xóa Product #' + id + '?')) {
                $.ajax({
                    url: (contextPath || '') + '/api/product/deleteProduct?productId=' + id,
                    type: 'DELETE',
                    success: function() {
                        loadProducts();
                    }
                });
            }
        }
    </script>
</body>
</html>
