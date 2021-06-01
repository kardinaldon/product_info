package controller;

import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ProductService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product_resource")
public class ProductClientResource {

    @Autowired
    private ProductService productService;

    private Optional<Product> optionalProduct;
    private List<Product> productList;
    private Product product;

    @GetMapping(path = "/id", produces = "application/json")
    public ResponseEntity getProductById(@RequestParam @NotNull long id,
                                         @RequestParam @NotNull String language,
                                         @RequestParam @NotNull String currency) {
        if (id != 0) {
            optionalProduct = productService.getByProductIdLanguageCurrency(id, language, currency);
            if(optionalProduct.isPresent()) {
                product = optionalProduct.get();
                return new ResponseEntity(product, HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/title",produces = "application/json")
    public ResponseEntity getProductByTitle(@RequestParam @NotNull String title,
                                            @RequestParam String language,
                                            @RequestParam String currency,
                                            @RequestParam int size,
                                            @RequestParam int page) {
        if (!title.isEmpty()) {
            productList = productService.getByPartOfTitle(title, language, currency, size, page);
            if(!productList.isEmpty()) {
                return new ResponseEntity(productList, HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/description",produces = "application/json")
    public ResponseEntity getProductByPartOfDescription(@RequestParam @NotNull String description,
                                                        @RequestParam String language,
                                                        @RequestParam String currency,
                                                        @RequestParam int size,
                                                        @RequestParam int page) {
        if (!description.isEmpty()) {
            productList = productService.getByPartOfDescription(description, language, currency, size, page);
            if(!productList.isEmpty()) {
                return new ResponseEntity(productList, HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/all",produces = "application/json")
    public ResponseEntity getAllProducts(@RequestParam String language,
                                         @RequestParam String currency,
                                         @RequestParam int size,
                                         @RequestParam int page) {
        productList = productService.getAllProductsByLanguageAndPriceCurrency(language, currency, size, page);
        if(!productList.isEmpty()){
            return new ResponseEntity(productList, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
