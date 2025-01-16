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
import ecommerce.business.products.service.EcommerceProductService;
import ecommerce.utils.ApiReturn;
import ecommerce.utils.BaseController;

@RestController
@RequestMapping("ecommerce/products")
public class EcommerceProductController extends BaseController {
    private final EcommerceProductService ecommerceProductService;

    public EcommerceProductController(Executor executor, EcommerceProductService ecommerceProductService) {
        super(executor);
        this.ecommerceProductService = ecommerceProductService;
    }

    @PostMapping("/register")
    public CompletableFuture<ApiReturn<EcommerceProduct>> registerProduct(
            @RequestBody EcommerceProduct ecommerceProduct) {
        return asyncResultOf(() -> this.ecommerceProductService.registerProduct(ecommerceProduct));
    }

    @GetMapping
    public CompletableFuture<ApiReturn<List<EcommerceProduct>>> findProducts(
            @RequestParam(required = false) boolean active, @RequestParam(required = false) String category,
            @RequestParam(required = false) UUID productId, @RequestParam(required = false) String name) {
        return asyncResultOf(() -> this.ecommerceProductService.findProducts(active, category, productId, name));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ApiReturn<EcommerceProduct>> findProductById(
            @PathVariable UUID id) {
        return asyncResultOf(() -> this.ecommerceProductService.findProductById(id));
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ApiReturn<Boolean>> updateProduct(@PathVariable UUID id,
            @RequestBody EcommerceProduct ecommerceProduct) {
        return asyncResultOf(() -> this.ecommerceProductService.updateProduct(ecommerceProduct, id));
    }
}