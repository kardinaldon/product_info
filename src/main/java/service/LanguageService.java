package service;

import dao.CurrencyRepository;
import dao.LanguageRepository;
import model.Language;
import model.PriceCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {

    private final static Logger log = LoggerFactory.getLogger(LanguageService.class);

    @Autowired
    private LanguageRepository languageRepository;

    private Optional<Language> optionalLanguage;

    public Optional<Language> getLanguageById(String languageId){
        try {
            optionalLanguage = languageRepository.findById(languageId);
            return optionalLanguage;
        }
        catch (Exception ex) {
            log.info(ex.getMessage());
            return Optional.empty();
        }
    }
}
