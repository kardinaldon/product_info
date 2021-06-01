package service;

import dao.ProductRepository;
import model.Language;
import model.PriceCurrency;
import model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductService.class);
    private Optional<Product> optionalProduct;
    private Product product;
    private Optional<Language> optionalLanguage;
    private Optional<PriceCurrency> optionalPriceCurrency;
    private List<Product> productList;
    private Pageable pagination;

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
    public Optional<Product> getByProductIdLanguageCurrency (long id,
                                                             String languageId,
                                                             String currencyId) {
        try {
            optionalLanguage = languageService.getLanguageById(languageId);
            optionalPriceCurrency = currencyService.getCurrencyById(currencyId);
            if(optionalLanguage.isPresent() && optionalPriceCurrency.isPresent()){
                optionalProduct = productRepository.findByProductIdAndLanguageAndPriceCurrency(id,
                        optionalLanguage.get(),
                        optionalPriceCurrency.get());
                return optionalProduct;
            }
            else{
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    public List<Product> getByPartOfTitle(String title,
                                          String languageId,
                                          String currencyId,
                                          int size,
                                          int page) {
        try {
            optionalLanguage = languageService.getLanguageById(languageId);
            optionalPriceCurrency = currencyService.getCurrencyById(currencyId);
            pagination = PageRequest.of(page, size);
            if(optionalLanguage.isPresent() && optionalPriceCurrency.isPresent()){
                productList = productRepository
                        .findByTitleContainsAndLanguageAndPriceCurrency(title,
                                optionalLanguage.get(),
                                optionalPriceCurrency.get(),
                                pagination);
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
                                                String currencyId,
                                                int size,
                                                int page) {
        try {
            optionalLanguage = languageService.getLanguageById(languageId);
            optionalPriceCurrency = currencyService.getCurrencyById(currencyId);
            pagination = PageRequest.of(page, size);
            if(optionalLanguage.isPresent() && optionalPriceCurrency.isPresent()){
                productList = productRepository
                        .findByDescriptionContainsAndLanguageAndPriceCurrency(partOfDescription,
                                optionalLanguage.get(),
                                optionalPriceCurrency.get(),
                                pagination);
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
    public List<Product> getAllProductsByLanguageAndPriceCurrency(String languageId,
                                                                  String currencyId,
                                                                  int size,
                                                                  int page){
        try {
            optionalLanguage = languageService.getLanguageById(languageId);
            optionalPriceCurrency = currencyService.getCurrencyById(currencyId);
            pagination = PageRequest.of(page, size);
            if(optionalLanguage.isPresent() && optionalPriceCurrency.isPresent()){
                List<Product> productList = productRepository.findAllByLanguageAndPriceCurrency(
                        optionalLanguage.get(),
                        optionalPriceCurrency.get(),
                        pagination);
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
    public boolean deleteProduct(long id){
        try {
            if(productRepository.existsById(id)) {
                product = productRepository.findById(id).get();
                productRepository.delete(product);
                return true;
            }
            else {
                log.info("product with id " +
                        id +
                        " not found, delete is not possible");
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
                product.setModificationDate(LocalDateTime.now());
                Product updatedProduct = productRepository.save(product);
                optionalProduct = Optional.of(updatedProduct);
                return optionalProduct;
            }
            else {
                log.info("product with id " +
                        product.getProductId() +
                        " not found, update is not possible");
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
            product.setModificationDate(LocalDateTime.now());
            product.setCreationDate(LocalDateTime.now());
            Product newProduct = productRepository.save(product);
            return newProduct.getProductId();
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return 0;
        }
    }

}
