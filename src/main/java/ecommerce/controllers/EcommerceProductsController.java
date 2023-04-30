package ecommerce.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.business.products.entity.EcommerceProduct;
import ecommerce.business.products.entity.ResumedEcommerceProduct;
import ecommerce.business.products.entity.ResumedEcommerceProducts;
import ecommerce.business.products.service.EcommerceProductService;
import ecommerce.utils.ApiReturn;
import ecommerce.utils.BaseController;

@RestController
@RequestMapping("ecommerce/products")
public class EcommerceProductsController extends BaseController {
    private final EcommerceProductService productService;

    public EcommerceProductsController(Executor executor, EcommerceProductService productService) {
        super(executor);
        this.productService = productService;
    }

    @PostMapping("/register")
    public CompletableFuture<ApiReturn<EcommerceProduct>> registerProduct(
            @RequestBody EcommerceProduct productDetails) {
        return asyncResultOf(() -> this.productService.registerProduct(productDetails));
    }

    @GetMapping
    public CompletableFuture<ApiReturn<List<ResumedEcommerceProducts>>> findProducts(
            @RequestParam(required = false) boolean active, @RequestParam(required = false) String category,
            @RequestParam(required = false) UUID productId, @RequestParam(required = false) String name) {
        return asyncResultOf(() -> this.productService.findProducts(active, category, productId, name));
    }

    @GetMapping("/{productId}")
    public CompletableFuture<ApiReturn<ResumedEcommerceProduct>> findProductById(
            @PathVariable UUID productId) {
        return asyncResultOf(() -> this.productService.findProductById(productId));
    }

    @PutMapping("/update/{productId}")
    public CompletableFuture<ApiReturn<Boolean>> updateProduct(@PathVariable UUID productId,
            @RequestBody ResumedEcommerceProduct productDetails) {
        return asyncResultOf(() -> this.productService.updateProduct(productDetails, productId));
    }
}