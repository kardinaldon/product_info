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

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity getProductById(@PathVariable long id,
                                         @PathVariable String language,
                                         @PathVariable String currency) {
        if (id != 0) {
            optionalProduct = productService.getById(id);
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

    @GetMapping(path = "/{title}", produces = "application/json")
    public ResponseEntity getProductByTitle(@PathVariable @NotNull String title,
                                            @PathVariable String language,
                                            @PathVariable String currency) {
        if (!title.isEmpty()) {
            productList = productService.getByPartOfTitle(title, language, currency);
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

    @GetMapping(path = "/{description}", produces = "application/json")
    public ResponseEntity getProductByPartOfDescription(@PathVariable @NotNull String description,
                                                        @PathVariable String language,
                                                        @PathVariable String currency) {
        if (!description.isEmpty()) {
            productList = productService.getByPartOfDescription(description, language, currency);
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

    @GetMapping(produces = "application/json")
    public ResponseEntity getAllProducts(@PathVariable String language,
            @PathVariable String currency) {
        productList = productService.getAllProductsByLanguageAndPriceCurrency(language, currency);
        if(!productList.isEmpty()){
            return new ResponseEntity(productList, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
