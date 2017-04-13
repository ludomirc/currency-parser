package pl.parser.nbp.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;
import pl.parser.nbp.dao.ExchangeRateDao;
import pl.parser.nbp.domain.CurrencyEntry;
import pl.parser.nbp.exception.AppException;
import pl.parser.nbp.exception.BusinessException;
import pl.parser.nbp.service.CurrencyService;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by Benek on 12.04.2017.
 */
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger logger = LogManager.getLogger(CurrencyServiceImpl.class.getName());

    private ExchangeRateDao rateDao;
    private CurrencyUnit localUnit;

    public CurrencyServiceImpl(ExchangeRateDao rateDao, CurrencyUnit localUnit) {
        this.rateDao = rateDao;
        this.localUnit = localUnit;
    }

    @Override
    public MonetaryAmount getAverageBuyRate(LocalDate begin, LocalDate end, CurrencyUnit currency) throws BusinessException {
        logger.debug("begin");
        MonetaryAmount average = getCurrencyArithmeticAverage(begin, end, currency, MarketOperationTypeEnum.BUY);
        logger.debug("end");
        return average;
    }

    @Override
    public MonetaryAmount getAverageSalesRate(LocalDate begin, LocalDate end, CurrencyUnit currency) throws BusinessException {
        logger.debug("begin");
        MonetaryAmount average = getCurrencyArithmeticAverage(begin, end, currency, MarketOperationTypeEnum.SALE);
        logger.debug("end");
        return average;
    }

    @Override
    public MonetaryAmount getBuyRateStandardDeviation(LocalDate begin, LocalDate end, CurrencyUnit currency) throws AppException {
        logger.debug("begin");
        MonetaryAmount sDeviation = getCurrencyStandardDeviation(begin, end, currency, MarketOperationTypeEnum.BUY);
        logger.debug("end");
        return sDeviation;
    }

    @Override
    public MonetaryAmount getSalesRateStandardDeviation(LocalDate begin, LocalDate end, CurrencyUnit currency) throws AppException {
        logger.debug("begin");
        MonetaryAmount sDeviation = getCurrencyStandardDeviation(begin, end, currency, MarketOperationTypeEnum.SALE);
        logger.debug("end");
        return sDeviation;
    }

    protected MonetaryAmount getCurrencyStandardDeviation(LocalDate begin, LocalDate end, CurrencyUnit currency, MarketOperationTypeEnum operationType) throws AppException {
        logger.debug("begin");
        Collection<CurrencyEntry> cEntries = null;
        try {
            cEntries = rateDao.getExchangeRate(begin, end, currency.toString());
        } catch (AppException e) {
            e.printStackTrace();
        }
        MonetaryAmount average = getArithmeticAverage(cEntries, operationType, localUnit);
        MonetaryAmount sDeviation = getStandardDeviation(cEntries, average, operationType);
        logger.debug("end");
        return sDeviation;
    }

    protected MonetaryAmount getCurrencyArithmeticAverage(LocalDate begin, LocalDate end, CurrencyUnit currency, MarketOperationTypeEnum operationType) throws BusinessException {
        logger.debug("begin");
        Collection<CurrencyEntry> cEntries = null;
        try {
            cEntries = rateDao.getExchangeRate(begin, end, currency.toString());
        } catch (AppException e) {
            e.printStackTrace();
        }
        MonetaryAmount average = getArithmeticAverage(cEntries, operationType, localUnit);
        logger.debug("end");
        return average;
    }

    protected MonetaryAmount getArithmeticAverage(Collection<CurrencyEntry> entries, MarketOperationTypeEnum operationType, CurrencyUnit currencyUnit) {
        logger.debug("begin");
        MonetaryAmount average = Money.of(BigDecimal.ZERO, currencyUnit);
        for (CurrencyEntry entry : entries) {
            switch (operationType) {
                case BUY:
                    average = average.add(Money.of(Double.parseDouble(entry.getBuyingRate()), currencyUnit));
                    break;
                case SALE:
                    average = average.add(Money.of(Double.parseDouble(entry.getSaleRate()), currencyUnit));
                    break;
            }
        }
        average = average.divide(entries.size());
        logger.debug("end");
        return average;
    }

    protected MonetaryAmount getStandardDeviation(Collection<CurrencyEntry> entries, MonetaryAmount arithmeticAverage, MarketOperationTypeEnum operationType) {
        logger.debug("begin");
        CurrencyUnit cUnit = arithmeticAverage.getCurrency();
        MonetaryAmount collector = Money.of(BigDecimal.ZERO, cUnit);
        MonetaryAmount tmpAmount = null;
        for (CurrencyEntry entry : entries) {
            switch (operationType) {
                case BUY:
                    tmpAmount = Money.of(Double.parseDouble(entry.getBuyingRate()), cUnit);
                    break;
                case SALE:
                    tmpAmount = Money.of(Double.parseDouble(entry.getSaleRate()), cUnit);
                    break;
            }

            tmpAmount = tmpAmount.subtract(arithmeticAverage);
            tmpAmount = tmpAmount.multiply(tmpAmount.getNumber());
            collector = collector.add(tmpAmount);
        }

        //variance
        MonetaryAmount s2 = collector.divide(entries.size());

        //Standard deviation
        MonetaryAmount sDeviation = Money.of(Math.sqrt(s2.getNumber().doubleValue()), localUnit);
        logger.debug("end");
        return sDeviation;
    }


}
