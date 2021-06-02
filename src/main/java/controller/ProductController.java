package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ProductService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Tag(name="Admin controller",
        description="the controller contains operations for adding, changing, deleting products")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Optional<Product> optionalProduct;
    private Product product;

    @Operation(
            summary = "Get product by ID",
            description = "allows to get a product by a unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "400", description = "product id not specified"),
            @ApiResponse(responseCode = "404", description = "product not found")})
    @GetMapping(produces = "application/json")
    public ResponseEntity getProductById(@RequestParam @NotNull @Parameter(description = "unique product identifier") long id) {
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

    @Operation(
            summary = "New product",
            description = "allows to create a new product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "product successfully created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "400", description = "one or more of the required fields of the product is not filled"),
            @ApiResponse(responseCode = "500", description = "an error occurred on the server while creating a new product")})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity postProduct(@RequestBody Product product) {
        if (product.getTitle() != null && !product.getTitle().isEmpty()
                && product.getPrice() >= 0
                && product.getDescription() != null
                && product.getLanguage() != null && product.getPriceCurrency() != null) {
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

    @Operation(
            summary = "Update",
            description = "allows update product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product successfully updated",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "400", description = "one or more of the required fields of the product is not filled"),
            @ApiResponse(responseCode = "500", description = "an error occurred on the server while updating product")})
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity updateProduct(@RequestBody Product product){
        if(product.getProductId() != 0
                && product.getTitle() != null && !product.getTitle().isEmpty()
                && product.getPrice() > 0
                && product.getDescription() != null
                && product.getLanguage() != null && product.getPriceCurrency() != null) {
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

    @Operation(
            summary = "Delete",
            description = "allows delete product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product successfully deleted",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "400", description = "an error occurred on the server while deleting product"),
            @ApiResponse(responseCode = "404", description = "product not found"),
            @ApiResponse(responseCode = "500", description = "an error occurred on the server while deleting product")})
    @DeleteMapping(produces = "application/json")
    public ResponseEntity deleteProduct(@RequestParam @Min(1) @Parameter(description = "unique product identifier") long id) {
        if (id != 0) {
            boolean result = productService.deleteProduct(id);
            if(result) {
                return new ResponseEntity(result, HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
