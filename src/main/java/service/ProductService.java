package service;

import dao.ProductRepository;
import model.Language;
import model.PriceCurrency;
import model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductService.class);
    private Optional<Product> optionalProduct;
    private Optional<Language> optionalLanguage;
    private Optional<PriceCurrency> optionalPriceCurrency;
    private List<Product> productList;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Optional<Product> getById (long id) {
        try {
            optionalProduct = productRepository.findById(id);
            return optionalProduct;
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    public List<Product> getByPartOfTitle(String title, String languageId, String currencyId) {
        try {
            optionalLanguage = languageService.getLanguageById(languageId);
            optionalPriceCurrency = currencyService.getCurrencyById(currencyId);
            if(optionalLanguage.isPresent() && optionalPriceCurrency.isPresent()){
                productList = productRepository
                        .findByTitleContainsAndLanguageAndPriceCurrency(title,
                                optionalLanguage.get().getLanguageId(),
                                optionalPriceCurrency.get().getCurrencyId());
                return productList;
            }
            else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Product> getByPartOfDescription(String partOfDescription,
                                                String languageId,
                                                String currencyId) {
        try {
            optionalLanguage = languageService.getLanguageById(languageId);
            optionalPriceCurrency = currencyService.getCurrencyById(currencyId);
            if(optionalLanguage.isPresent() && optionalPriceCurrency.isPresent()){
                productList = productRepository
                        .findByDescriptionContainsAndLanguageAndPriceCurrency(partOfDescription,
                                optionalLanguage.get().getLanguageId(),
                                optionalPriceCurrency.get().getCurrencyId());
                return productList;
            }
            else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Product> getAllProductsByLanguageAndPriceCurrency(String languageId, String currencyId){
        try {
            optionalLanguage = languageService.getLanguageById(languageId);
            optionalPriceCurrency = currencyService.getCurrencyById(currencyId);
            if(optionalLanguage.isPresent() && optionalPriceCurrency.isPresent()){
                List<Product> productList = productRepository.findAllByLanguageAndPriceCurrency(
                        optionalLanguage.get().getLanguageId(),
                        optionalPriceCurrency.get().getCurrencyId());
                return productList;
            }
            else {
                return new ArrayList<>();
            }
        }
        catch (Exception ex) {
            log.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Transactional
    public boolean deleteProduct(Product product){
        try {
            if(productRepository.existsById(product.getProductId())) {
                productRepository.delete(product);
                return true;
            }
            else {
                log.info("product with id " + product.getProductId() + " not found, delete is not possible");
                return false;
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return false;
        }
    }

    @Transactional
    public Optional<Product> updateProduct(Product product) {
        try {
            if(productRepository.existsById(product.getProductId())) {
                Product updatedProduct = productRepository.save(product);
                optionalProduct = Optional.of(updatedProduct);
                return optionalProduct;
            }
            else {
                log.info("product with id " + product.getProductId() + " not found, update is not possible");
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    public long createProduct(Product product) {
        try {
            Product newProduct = productRepository.save(product);
            return newProduct.getProductId();
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return 0;
        }
    }

}
