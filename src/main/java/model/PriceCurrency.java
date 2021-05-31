package model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "currency")
@Data
public class PriceCurrency {

    @Id
    @Column(name = "currency_id")
    private String currencyId;

}
