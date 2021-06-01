package dao;

import model.Language;
import model.PriceCurrency;
import model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Optional<Product> findByProductIdAndLanguageAndPriceCurrency(long productId,
                                                          Language language,
                                                          PriceCurrency currency);
    List<Product> findByTitleContainsAndLanguageAndPriceCurrency(String title,
                                                                 Language language,
                                                                 PriceCurrency currency,
                                                                 Pageable pagination);
    List<Product> findByDescriptionContainsAndLanguageAndPriceCurrency(String partOfDescription,
                                                                       Language language,
                                                                       PriceCurrency currency,
                                                                       Pageable pagination);
    List<Product> findAllByLanguageAndPriceCurrency(Language language,
                                                    PriceCurrency currency,
                                                    Pageable pagination);

}
