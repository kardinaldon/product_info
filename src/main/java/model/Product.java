package model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Schema(description = "Product")
@Entity
@Table(name = "product")
@Data
public class Product {

    @Schema(description = "required field when searching by id or updating, deleting product, " +
            "but not necessary when creating a new product or searching in other fields",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    @Schema(description = "required field when creating and updating a product",
            example = "test title")
    @Column(name = "title")
    private String title;

    @Schema(description = "product description",
            example = "test description")
    @Column(name = "description")
    private String description;

    @Schema(description = "product data language",
            example = "en")
    @ManyToOne
    @JoinColumn(name="language_id", nullable=false)
    private Language language;

    @Schema(description = "the price of product, required field when creating and updating a product",
            example = "200")
    @Column(name = "price")
    private long price;

    @Schema(description = "product currency of price",
            example = "usd")
    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private PriceCurrency priceCurrency;

    @Schema(description = "product creation date, when creating a product, it is filled in automatically",
            example = "2021-06-01T04:03:39.076402")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Schema(description = "product modification date, when updating and creating product, it is filled in automatically",
            example = "2021-06-01T04:03:39.076402")
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

}
