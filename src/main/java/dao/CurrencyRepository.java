package dao;

import model.PriceCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CurrencyRepository extends JpaRepository<PriceCurrency,String> {
}
