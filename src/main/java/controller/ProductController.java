package controller;

import model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public ResponseEntity getProductById() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllProducts() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postProduct(Product product) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
