package service;

import dao.CurrencyRepository;
import model.PriceCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyService {

    private final static Logger log = LoggerFactory.getLogger(CurrencyService.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    Optional<PriceCurrency> optionalCurrency;

    public Optional<PriceCurrency> getCurrencyById(String currencyId){
        try {
            optionalCurrency = currencyRepository.findById(currencyId);
            return optionalCurrency;
        }
        catch (Exception ex) {
            log.info(ex.getMessage());
            return Optional.empty();
        }
    }
}
