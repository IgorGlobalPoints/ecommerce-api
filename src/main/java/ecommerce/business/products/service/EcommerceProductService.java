package ecommerce.business.products.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ecommerce.business.products.entity.EcommerceProduct;
import ecommerce.business.products.repository.EcommerceProductRepository;
import ecommerce.utils.IException;
import ecommerce.utils.Validator;

@Service
public class EcommerceProductService {
  private static final Logger LOG = LoggerFactory.getLogger(EcommerceProductService.class);

  private final EcommerceProductRepository ecommeceProductRepository;

  public EcommerceProductService(EcommerceProductRepository ecommerceProductRepository) {
    this.ecommeceProductRepository = ecommerceProductRepository;
  }

  public EcommerceProduct registerProduct(EcommerceProduct ecommerceProduct) {
    LOG.info("Registrando novo produto");

    if (ecommerceProduct == null) {
      throw IException.ofValidation("PRODUCT_DETAILS_INVALID", "Detalhes do protudo inválido.");
    }

    ecommerceProduct.init();

    try {
      return this.ecommeceProductRepository.saveProduct(ecommerceProduct);
    } catch (RuntimeException ex) {
      LOG.error("Error ao registrar novo produto {} ", ex.getMessage());
      throw IException.ofError("PRODUCT_REGISTER_ERROR", "Ocorreu um erro ao tentar salvar o produto.");
    }
  }

  public List<EcommerceProduct> findProducts(boolean active, String category, UUID productId, String name) {
    LOG.info("Buscando Lista de produtos");

    try {
      return this.ecommeceProductRepository.findProducts(active, category, productId, name);
    } catch (RuntimeException ex) {
      LOG.error("Erro ao buscar os produtos", ex.getMessage());
      throw IException.ofError("PRODUCT_NOT_FOUND", "Não foi possivel encontrar os produtos.");
    }
  }

  public EcommerceProduct findProductById(UUID productId) {
    LOG.info("Buscando detalhe do produto {}", productId);

    if (Validator.isNullOrEmpty(productId)) {
      throw IException.ofValidation("ID_INVALID", "ID do produto inválido.");
    }

    try {
      return this.ecommeceProductRepository.findProducById(productId);
    } catch (RuntimeException ex) {
      LOG.error("Product {} - Erro ao buscar detalhe do produto {}", ex.getMessage());
      throw IException.ofError("PRODUCT_NOT_FOUND", "Não foi possivel encontrar o produto");
    }
  }

  public Boolean updateProduct(EcommerceProduct ecommerceProduct, UUID productId) {
    LOG.info("Alterando produto {}", productId);

    if (ecommerceProduct == null) {
      throw IException.ofValidation("PRODUCT_DETAILS_INVALID", "Detalhes do produto inválido.");
    }

    if (Validator.isNullOrEmpty(productId)) {
      throw IException.ofValidation("ID_INVALID", "ID do produto inválido.");
    }

    try {
      return this.ecommeceProductRepository.updateProduct(ecommerceProduct, productId);
    } catch (RuntimeException ex) {
      LOG.error("Product {} - Erro ao atualizar o produto {}", ex.getMessage());
      throw IException.ofError("PRODUCT_UPDATE_ERROR", "Ocorreu um erro ao tentar alterar o produto.");
    }
  }
}
