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
				    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
				    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
				    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
				    <style>
				        body { background-color: #f8fafc; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
				        .header-box { background: linear-gradient(135deg, #1e3a8a, #3b82f6); color: white; padding: 2rem 0; border-radius: 0 0 1rem 1rem; margin-bottom: 2rem; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); }
				        .card { border-radius: 0.75rem; border: none; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); }
				        .category-img { width: 50px; height: 50px; object-fit: cover; border-radius: 8px; border: 1px solid #e2e8f0; }
				        .btn-swagger { background: #10b981; color: white; }
				        .btn-swagger:hover { background: #059669; color: white; }
				    </style>
				</head>
				<body>
				    <div class="header-box text-center">
				        <div class="container">
				            <h4 class="text-uppercase tracking-wide opacity-75">Khoa Công Nghệ Thông Tin - HCMUTE</h4>
				            <h1 class="fw-bold">Spring Boot REST API with AJAX Example</h1>
				            <p class="mb-0">Giảng viên: ThS. Nguyễn Hữu Trung | CRUD Category & Upload File</p>
				            <div class="mt-3">
				                <a href="/swagger-ui.html" class="btn btn-swagger fw-semibold"><i class="bi bi-code-slash"></i> Mở Swagger UI Documentation</a>
				            </div>
				        </div>
				    </div>

				    <div class="container pb-5">
				        <div class="row g-4">
				            <!-- Form thêm / sửa Category -->
				            <div class="col-lg-4">
				                <div class="card p-4">
				                    <h4 class="card-title fw-bold mb-3 text-primary" id="form-title">
				                        <i class="bi bi-plus-circle-fill"></i> Thêm Category mới
				                    </h4>
				                    <form id="categoryForm" enctype="multipart/form-data">
				                        <input type="hidden" id="categoryId" name="categoryId">
				                        <div class="mb-3">
				                            <label class="form-label fw-semibold">Tên Category</label>
				                            <input type="text" class="form-control" id="categoryName" name="categoryName" placeholder="Nhập tên category..." required>
				                        </div>
				                        <div class="mb-3">
				                            <label class="form-label fw-semibold">Icon / Ảnh đại diện</label>
				                            <input type="file" class="form-control" id="icon" name="icon" accept="image/*">
				                            <div id="iconPreview" class="mt-2 text-center d-none">
				                                <img id="previewImg" class="category-img" style="width: 80px; height: 80px;" src="" alt="preview">
				                            </div>
				                        </div>
				                        <div class="d-grid gap-2">
				                            <button type="button" class="btn btn-primary fw-semibold" id="btnSave" onclick="saveCategory()">
				                                <i class="bi bi-check-circle"></i> Thêm mới
				                            </button>
				                            <button type="button" class="btn btn-secondary d-none" id="btnCancel" onclick="resetForm()">
				                                Hủy cập nhật
				                            </button>
				                        </div>
				                    </form>
				                    <div id="formAlert" class="mt-3"></div>
				                </div>
				            </div>

				            <!-- Danh sách Category -->
				            <div class="col-lg-8">
				                <div class="card p-4">
				                    <div class="d-flex justify-content-between align-items-center mb-3">
				                        <h4 class="card-title fw-bold mb-0 text-dark">
				                            <i class="bi bi-list-task"></i> Danh sách Category
				                        </h4>
				                        <button class="btn btn-outline-primary btn-sm" onclick="loadCategories()">
				                            <i class="bi bi-arrow-clockwise"></i> Tải lại
				                        </button>
				                    </div>
				                    <div class="table-responsive">
				                        <table class="table table-hover align-middle">
				                            <thead class="table-light">
				                                <tr>
				                                    <th style="width: 80px;">ID</th>
				                                    <th style="width: 100px;">Icon</th>
				                                    <th>Tên Category</th>
				                                    <th style="width: 160px;" class="text-center">Thao tác</th>
				                                </tr>
				                            </thead>
				                            <tbody id="categoryTableBody">
				                                <tr><td colspan="4" class="text-center text-muted py-4">Đang tải dữ liệu...</td></tr>
				                            </tbody>
				                        </table>
				                    </div>
				                </div>
				            </div>
				        </div>
				    </div>

				    <script>
				        $(document).ready(function() {
				            loadCategories();
				        });

				        function loadCategories() {
				            $.ajax({
				                url: '/api/category',
				                type: 'GET',
				                success: function(res) {
				                    if (res && res.status && res.body) {
				                        renderCategories(res.body);
				                    }
				                },
				                error: function(err) {
				                    $('#categoryTableBody').html('<tr><td colspan="4" class="text-center text-danger">Lỗi tải dữ liệu từ API!</td></tr>');
				                }
				            });
				        }

				        function renderCategories(list) {
				            let html = '';
				            if (!list || list.length === 0) {
				                html = '<tr><td colspan="4" class="text-center text-muted py-4">Chưa có category nào.</td></tr>';
				            } else {
				                list.forEach(item => {
				                    let iconHtml = item.icon ? `<img src="/api/category/icon/${item.icon}" class="category-img" alt="icon">` : '<span class="badge bg-secondary">No icon</span>';
				                    html += `
				                        <tr>
				                            <td class="fw-bold">#${item.categoryId}</td>
				                            <td>${iconHtml}</td>
				                            <td class="fw-semibold">${item.categoryName}</td>
				                            <td class="text-center">
				                                <button class="btn btn-outline-warning btn-sm me-1" onclick="editCategory(${item.categoryId}, '${escape(item.categoryName)}', '${item.icon || ''}')" title="Sửa">
				                                    <i class="bi bi-pencil-square"></i>
				                                </button>
				                                <button class="btn btn-outline-danger btn-sm" onclick="deleteCategory(${item.categoryId})" title="Xóa">
				                                    <i class="bi bi-trash"></i>
				                                </button>
				                            </td>
				                        </tr>
				                    `;
				                });
				            }
				            $('#categoryTableBody').html(html);
				        }

				        function saveCategory() {
				            const id = $('#categoryId').val();
				            const name = $('#categoryName').val().trim();
				            const fileInput = document.getElementById('icon');

				            if (!name) {
				                showAlert('Vui lòng nhập tên category!', 'warning');
				                return;
				            }

				            const formData = new FormData();
				            formData.append('categoryName', name);
				            if (fileInput.files.length > 0) {
				                formData.append('icon', fileInput.files[0]);
				            }

				            if (!id) {
				                // Thêm mới
				                $.ajax({
				                    url: '/api/category/addCategory',
				                    type: 'POST',
				                    data: formData,
				                    processData: false,
				                    contentType: false,
				                    success: function(res) {
				                        showAlert(res.message || 'Thêm thành công!', 'success');
				                        resetForm();
				                        loadCategories();
				                    },
				                    error: function(err) {
				                        showAlert(err.responseText || 'Lỗi thêm category!', 'danger');
				                    }
				                });
				            } else {
				                // Cập nhật
				                formData.append('categoryId', id);
				                $.ajax({
				                    url: '/api/category/updateCategory',
				                    type: 'PUT',
				                    data: formData,
				                    processData: false,
				                    contentType: false,
				                    success: function(res) {
				                        showAlert(res.message || 'Cập nhật thành công!', 'success');
				                        resetForm();
				                        loadCategories();
				                    },
				                    error: function(err) {
				                        showAlert(err.responseText || 'Lỗi cập nhật category!', 'danger');
				                    }
				                });
				            }
				        }

				        function editCategory(id, encodedName, icon) {
				            const name = unescape(encodedName);
				            $('#categoryId').val(id);
				            $('#categoryName').val(name);
				            $('#form-title').html('<i class="bi bi-pencil-fill"></i> Cập nhật Category #' + id);
				            $('#btnSave').html('<i class="bi bi-check2-circle"></i> Cập nhật');
				            $('#btnCancel').removeClass('d-none');
				            if (icon) {
				                $('#previewImg').attr('src', '/api/category/icon/' + icon);
				                $('#iconPreview').removeClass('d-none');
				            } else {
				                $('#iconPreview').addClass('d-none');
				            }
				            window.scrollTo({ top: 0, behavior: 'smooth' });
				        }

				        function resetForm() {
				            $('#categoryId').val('');
				            $('#categoryName').val('');
				            $('#icon').val('');
				            $('#iconPreview').addClass('d-none');
				            $('#form-title').html('<i class="bi bi-plus-circle-fill"></i> Thêm Category mới');
				            $('#btnSave').html('<i class="bi bi-check-circle"></i> Thêm mới');
				            $('#btnCancel').addClass('d-none');
				        }

				        function deleteCategory(id) {
				            if (!confirm('Bạn có chắc chắn muốn xóa Category #' + id + ' không?')) {
				                return;
				            }
				            $.ajax({
				                url: '/api/category/deleteCategory?categoryId=' + id,
				                type: 'DELETE',
				                success: function(res) {
				                    showAlert(res.message || 'Xóa thành công!', 'success');
				                    loadCategories();
				                },
				                error: function(err) {
				                    showAlert(err.responseText || 'Lỗi xóa category!', 'danger');
				                }
				            });
				        }

				        function showAlert(msg, type) {
				            $('#formAlert').html(`
				                <div class="alert alert-${type} alert-dismissible fade show" role="alert">
				                    ${msg}
				                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				                </div>
				            `);
				            setTimeout(() => { $('#formAlert .alert').alert('close'); }, 4000);
				        }
				    </script>
				</body>
				</html>
				""";
	}
}
