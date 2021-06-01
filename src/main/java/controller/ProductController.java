package controller;

import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ProductService;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Optional<Product> optionalProduct;
    private Product product;

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity getProductById(@PathVariable long id) {
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

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity postProduct(@RequestBody @NotNull Product product) {
        if (product.getTitle() != null
                && product.getPrice() >= 0
                && product.getDescription() != null) {
            long newProductId = productService.createProduct(product);
            if(newProductId != 0) {
                return new ResponseEntity(newProductId, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity(newProductId, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity updateProduct(@RequestBody @NotNull Product product){
        if(product.getProductId() != 0
                && product.getTitle() != null
                && product.getPrice() >= 0
                && product.getDescription() != null) {
            optionalProduct = productService.updateProduct(product);
            if(optionalProduct.isPresent()){
                return new ResponseEntity(optionalProduct, HttpStatus.OK);
            }
            else{
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
