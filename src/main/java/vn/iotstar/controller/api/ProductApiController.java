package vn.iotstar.controller.api;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IStorageService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductApiController {

	@Autowired
	private IProductService productService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IStorageService storageService;

	@GetMapping
	public ResponseEntity<?> getAllProduct() {
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", productService.findAll()),
				HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
		Optional<Product> optProduct = productService.findById(id);
		if (optProduct.isPresent()) {
			return new ResponseEntity<Response>(
					new Response(true, "Thành công", optProduct.get()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(
					new Response(false, "Không tìm thấy Product", null),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/getProduct")
	public ResponseEntity<?> getProduct(@Validated @RequestParam("id") Long id) {
		Optional<Product> optProduct = productService.findById(id);
		if (optProduct.isPresent()) {
			return new ResponseEntity<Response>(
					new Response(true, "Thành công", optProduct.get()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(
					new Response(false, "Thất bại", null),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> saveOrUpdate(
			@Validated @RequestParam("productName") String productName,
			@RequestParam(value = "imageFile", required = false) MultipartFile productImages,
			@Validated @RequestParam("unitPrice") Double productPrice,
			@Validated @RequestParam(value = "discount", defaultValue = "0") Double promotionalPrice,
			@RequestParam(value = "description", defaultValue = "") String productDescription,
			@Validated @RequestParam("categoryId") Long categoryId,
			@Validated @RequestParam(value = "quantity", defaultValue = "0") Integer quantity,
			@Validated @RequestParam(value = "status", defaultValue = "1") Short status) {

		Optional<Product> optProduct = productService.findByProductName(productName);

		if (optProduct.isPresent()) {
			return new ResponseEntity<Response>(
					new Response(false, "Sản phẩm này đã tồn tại trong hệ thống", optProduct.get()),
					HttpStatus.BAD_REQUEST);
		} else {
			Product product = new Product();
			Timestamp timestamp = new Timestamp(new Date(System.currentTimeMillis()).getTime());

			try {
				product.setProductName(productName);
				product.setUnitPrice(productPrice);
				product.setDiscount(promotionalPrice);
				product.setDescription(productDescription);
				product.setQuantity(quantity);
				product.setStatus(status);
				product.setCreateDate(timestamp);

				// Xử lý Category liên quan
				Optional<Category> optCategory = categoryService.findById(categoryId);
				if (optCategory.isPresent()) {
					product.setCategory(optCategory.get());
				} else {
					Category cateEntity = new Category();
					cateEntity.setCategoryId(categoryId);
					product.setCategory(cateEntity);
				}

				// Kiểm tra tồn tại file, lưu file
				if (productImages != null && !productImages.isEmpty()) {
					UUID uuid = UUID.randomUUID();
					String uuString = uuid.toString();
					// Lưu file vào trường images
					product.setImages(storageService.getSorageFilename(productImages, uuString));
					storageService.store(productImages, product.getImages());
				}

				productService.save(product);

				optProduct = productService.findByCreateDate(timestamp);
				if (optProduct.isEmpty()) {
					optProduct = Optional.of(product);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Response>(
						new Response(false, "Lỗi khi lưu sản phẩm: " + e.getMessage(), null),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<Response>(
					new Response(true, "Thành công", optProduct.get()),
					HttpStatus.OK);
		}
	}

	@PutMapping(path = "/updateProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateProduct(
			@Validated @RequestParam("productId") Long productId,
			@Validated @RequestParam("productName") String productName,
			@RequestParam(value = "imageFile", required = false) MultipartFile productImages,
			@Validated @RequestParam("unitPrice") Double productPrice,
			@Validated @RequestParam(value = "discount", defaultValue = "0") Double promotionalPrice,
			@RequestParam(value = "description", defaultValue = "") String productDescription,
			@Validated @RequestParam("categoryId") Long categoryId,
			@Validated @RequestParam(value = "quantity", defaultValue = "0") Integer quantity,
			@Validated @RequestParam(value = "status", defaultValue = "1") Short status) {

		Optional<Product> optProduct = productService.findById(productId);

		if (optProduct.isEmpty()) {
			return new ResponseEntity<Response>(
					new Response(false, "Không tìm thấy Product với ID: " + productId, null),
					HttpStatus.BAD_REQUEST);
		} else {
			Product product = optProduct.get();
			product.setProductName(productName);
			product.setUnitPrice(productPrice);
			product.setDiscount(promotionalPrice);
			product.setDescription(productDescription);
			product.setQuantity(quantity);
			product.setStatus(status);

			// Xử lý Category liên quan
			Optional<Category> optCategory = categoryService.findById(categoryId);
			if (optCategory.isPresent()) {
				product.setCategory(optCategory.get());
			}

			// Kiểm tra file ảnh mới
			if (productImages != null && !productImages.isEmpty()) {
				UUID uuid = UUID.randomUUID();
				String uuString = uuid.toString();
				product.setImages(storageService.getSorageFilename(productImages, uuString));
				storageService.store(productImages, product.getImages());
			}

			productService.save(product);

			return new ResponseEntity<Response>(
					new Response(true, "Cập nhật Thành công", product),
					HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "/deleteProduct")
	public ResponseEntity<?> deleteProduct(@Validated @RequestParam("productId") Long productId) {
		Optional<Product> optProduct = productService.findById(productId);
		if (optProduct.isEmpty()) {
			return new ResponseEntity<Response>(
					new Response(false, "Không tìm thấy Product", null),
					HttpStatus.BAD_REQUEST);
		} else {
			productService.delete(optProduct.get());
			return new ResponseEntity<Response>(
					new Response(true, "Xóa Thành công", optProduct.get()),
					HttpStatus.OK);
		}
	}

	@GetMapping(path = "/image/{filename:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
