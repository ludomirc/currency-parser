package pl.parser.nbp.service;

import pl.parser.nbp.exception.AppException;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Benek on 10.04.2017.
 */
public interface CurrencyService extends Serializable {

    public MonetaryAmount getAverageBuyRate(LocalDate begin, LocalDate end, CurrencyUnit currency) throws AppException;

    public MonetaryAmount getAverageSalesRate(LocalDate begin, LocalDate end, CurrencyUnit currency) throws AppException;

    public MonetaryAmount getBuyRateStandardDeviation(LocalDate begin, LocalDate end, CurrencyUnit currency) throws AppException;

    public MonetaryAmount getSalesRateStandardDeviation(LocalDate begin, LocalDate end, CurrencyUnit currency) throws AppException;
}
