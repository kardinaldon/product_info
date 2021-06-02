package controller;

import exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ProductService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name="User controller",
        description="intended for receiving products by customers")
@RestController
@RequestMapping("/product_resource")
public class ProductClientResource {

    private final static Logger log = LoggerFactory.getLogger(ProductClientResource.class);

    @Autowired
    private ProductService productService;

    private Optional<Product> optionalProduct;
    private List<Product> productList;
    private Product product;
    private Map<String, Object> errorRequestBody;

    @Autowired
    private CustomException customException;

    @Operation(
            summary = "Get product by ID",
            description = "allows to get a product by a unique identifier, language and currency"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "400", description = "product id not specified",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "product not found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(ref = "#/components/schemas/Exception")))})
    @GetMapping(path = "/id", produces = "application/json")
    public ResponseEntity getProductById(@RequestParam @Min(1) @Parameter(description = "unique product identifier") long id,
                                         @RequestParam @NotNull @Parameter(description = "product data language") String language,
                                         @RequestParam @NotNull @Parameter(description = "product currency of price") String currency) throws CustomException {
        if (id != 0) {
            optionalProduct = productService.getById(id);
            if(optionalProduct.isPresent()){
                product = optionalProduct.get();
                if(!language.isEmpty() && !currency.isEmpty()){
                    if(product.getLanguage().getLanguageId().equals(language)
                            && product.getPriceCurrency().getCurrencyId().equals(currency)){
                        return new ResponseEntity(product, HttpStatus.OK);
                    }
                    else {
                        customException.setErrorCode(404);
                        customException.setErrorMessage("the product obtained by the identifier " +
                                "does not have data in the specified language and/or currency");
                        throw customException;
                    }
                }
                else{
                    return new ResponseEntity(product, HttpStatus.OK);
                }
            }
            else {
                customException.setErrorCode(404);
                customException.setErrorMessage("product with " + id + " not found");
                throw customException;
            }
        }
        else {
            customException.setErrorCode(400);
            customException.setErrorMessage("product id not specified");
            throw customException;
        }

        /*optionalProduct = productService.getByProductIdLanguageCurrency(id, language, currency);
            if(optionalProduct.isPresent()) {
                product = optionalProduct.get();
                return new ResponseEntity(product, HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }*/
    }

    @Operation(
            summary = "Get products by Title",
            description = "allows to get a products by part of title, language and currency"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))
            }),
            @ApiResponse(responseCode = "400", description = "one of the required fields is not filled",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "products with this title, language, currency not found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(ref = "#/components/schemas/Exception")))})
    @GetMapping(path = "/title",produces = "application/json")
    public ResponseEntity getProductByTitle(@RequestParam @NotNull @Parameter(description = "full title or part of it") String title,
                                            @RequestParam @NotNull @Parameter(description = "product data language") String language,
                                            @RequestParam @NotNull @Parameter(description = "product currency of price") String currency,
                                            @RequestParam @Min(0) @Parameter(description = "number of results per page") int size,
                                            @RequestParam @Min(0) @Parameter(description = "number of page") int page) {
        if (!title.isEmpty() && !language.isEmpty() && !currency.isEmpty()) {
            productList = productService.getByPartOfTitle(title, language, currency, size, page);
            if(!productList.isEmpty()) {
                return new ResponseEntity(productList, HttpStatus.OK);
            }
            else {
                customException.setErrorCode(404);
                customException.setErrorMessage("products with this title, language, currency not found");
                throw customException;
            }
        }
        else {
            customException.setErrorCode(400);
            customException.setErrorMessage("title, language, currency - required fields");
            throw customException;
        }
    }

    @Operation(
            summary = "Get products by description",
            description = "allows to get a products by part of description, language and currency"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))
                    }),
            @ApiResponse(responseCode = "400", description = "one of the required fields is not filled",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "products with this description, language, currency not found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(ref = "#/components/schemas/Exception")))})
    @GetMapping(path = "/description",produces = "application/json")
    public ResponseEntity getProductByPartOfDescription(@RequestParam @NotNull @Parameter(description = "full description or part of it") String description,
                                                        @RequestParam @NotNull @Parameter(description = "product data language") String language,
                                                        @RequestParam @NotNull @Parameter(description = "product currency of price") String currency,
                                                        @RequestParam @Min(0) @Parameter(description = "number of results per page") int size,
                                                        @RequestParam @Min(0) @Parameter(description = "number of page") int page) {
        if (!description.isEmpty() && !language.isEmpty() && !currency.isEmpty()) {
            productList = productService.getByPartOfDescription(description, language, currency, size, page);
            if(!productList.isEmpty()) {
                return new ResponseEntity(productList, HttpStatus.OK);
            }
            else {
                customException.setErrorCode(404);
                customException.setErrorMessage("products with this description, language, currency not found");
                throw customException;
            }
        }
        else {
            customException.setErrorCode(400);
            customException.setErrorMessage("description, language, currency - required fields");
            throw customException;
        }
    }

    @Operation(
            summary = "Get all products by description",
            description = "allows to get all products with data in the specified language and currency"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))
                    }),
            @ApiResponse(responseCode = "400", description = "one of the required fields is not filled",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "products with data in the specified language and currency not found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(ref = "#/components/schemas/Exception")))})
    @GetMapping(path = "/all",produces = "application/json")
    public ResponseEntity getAllProducts(@RequestParam @NotNull @Parameter(description = "product data language") String language,
                                         @RequestParam @NotNull @Parameter(description = "product currency of price") String currency,
                                         @RequestParam @Min(0) @Parameter(description = "number of results per page") int size,
                                         @RequestParam @Min(0) @Parameter(description = "number of page") int page) {
        productList = productService.getAllProductsByLanguageAndPriceCurrency(language, currency, size, page);
        if (!language.isEmpty() && !currency.isEmpty()) {
            if(!productList.isEmpty()){
                return new ResponseEntity(productList, HttpStatus.OK);
            }
            else {
                customException.setErrorCode(404);
                customException.setErrorMessage("products with this language and currency not found");
                throw customException;
            }
        }
        else {
            customException.setErrorCode(400);
            customException.setErrorMessage("language, currency - required fields");
            throw customException;
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleException() {
        errorRequestBody = new HashMap<>();
        errorRequestBody.put("errorCode",customException.getErrorCode());
        errorRequestBody.put("errorMessage",customException.getErrorMessage());
        log.info(errorRequestBody.toString());
        return new ResponseEntity(errorRequestBody,
                HttpStatus.resolve(customException.getErrorCode()));
    }

}
