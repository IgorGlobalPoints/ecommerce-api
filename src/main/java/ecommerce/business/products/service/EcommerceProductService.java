package ecommerce.business.products.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ecommerce.business.products.entity.EcommerceProduct;
import ecommerce.business.products.entity.ResumedEcommerceProduct;
import ecommerce.business.products.entity.ResumedEcommerceProducts;
import ecommerce.business.products.repository.EcommerceProductRepository;
import ecommerce.utils.IException;
import ecommerce.utils.Validator;

@Service
public class EcommerceProductService {
    private static final Logger LOG = LoggerFactory.getLogger(EcommerceProductService.class);

    private final EcommerceProductRepository productRepository;

    public EcommerceProductService(EcommerceProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public EcommerceProduct registerProduct(EcommerceProduct productDetails) {
        if (productDetails == null) {
            throw IException.ofValidation("PRODUCT_DETAILS_INVALID", "Detalhes do protudo inválido.");
        }

        var product = this.productRepository.findProductByName(productDetails.getName());

        if (product) {
            throw IException.ofError("ERROR_PRODUCT_EXIST", "Produto já existente, cadastre um novo produto.");
        }

        return this.productRepository.saveProduct(productDetails);
    }

    public List<ResumedEcommerceProducts> findProducts(boolean active, String category, UUID productId, String name) {
        try {
            return this.productRepository.findProducts(active, category, productId, name);
        } catch (RuntimeException ex) {
            LOG.error("Erro ao buscar os produtos", ex);
            throw IException.ofError("PRODUCT_NOT_FOUND", "Não foi possivel encontrar os produtos.");
        }
    }

    public ResumedEcommerceProduct findProductById(UUID productId) {
        if (Validator.isNullOrEmpty(productId)) {
            throw IException.ofValidation("ID_INVALID", "ID do produto inválido.");
        }

        try {
            return this.productRepository.findProducById(productId);
        } catch (RuntimeException ex) {
            LOG.error("Erro ao buscar produto", ex);
            throw IException.ofValidation("PRODUCT_NOT_FOUND", "Não foi possivel encontrar o produto");
        }
    }

    public boolean updateProduct(ResumedEcommerceProduct productDetails, UUID productId) {
        if (productDetails == null) {
            throw IException.ofValidation("PRODUCT_DETAILS_INVALID", "Detalhes do produto inválido.");
        }

        return this.productRepository.updateProduct(productDetails, productId);
    }
}
