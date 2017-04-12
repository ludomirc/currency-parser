package pl.parser.nbp.dao;

import pl.parser.nbp.domain.CurrencyEntry;
import pl.parser.nbp.exception.AppException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by Benek on 09.04.2017.
 */
public interface ExchangeRateDao extends Serializable {

    Collection<CurrencyEntry> getExchangeRate(LocalDate from, LocalDate to, String currency) throws AppException;
}
