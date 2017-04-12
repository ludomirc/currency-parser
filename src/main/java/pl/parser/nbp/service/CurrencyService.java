package pl.parser.nbp.service;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Benek on 10.04.2017.
 */
public interface CurrencyService extends Serializable {

    MonetaryAmount averagePurchaseRate(LocalDate begin, LocalDate end, String currency);

    MonetaryAmount averageSalesRates(LocalDate begin, LocalDate end, String currency);

    MonetaryAmount standardDeviationSalesRates(LocalDate begin, LocalDate end, String currency);
}
