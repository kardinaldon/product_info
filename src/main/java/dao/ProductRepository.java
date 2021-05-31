package dao;

import model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitleContainsAndLanguageAndPriceCurrency(String title, String language, String currency);
    List<Product> findByDescriptionContainsAndLanguageAndPriceCurrency(String partOfDescription, String language, String currency);
    List<Product> findAllByLanguageAndPriceCurrency(String language, String currency);

}
