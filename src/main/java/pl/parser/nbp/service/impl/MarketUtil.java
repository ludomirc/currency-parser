package pl.parser.nbp.service.impl;

import org.javamoney.moneta.Money;
import pl.parser.nbp.domain.CurrencyEntry;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by Benek on 12.04.2017.
 */
public class MarketUtil {

    private MarketUtil() {
    }

    public static MonetaryAmount getArithmeticAverage(Collection<CurrencyEntry> entries, OperationType operationType, CurrencyUnit currencyUnit) {
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
        return average;
    }

    public static MonetaryAmount getStandardDeviation(Collection<CurrencyEntry> entries, MonetaryAmount arithmeticAverage, OperationType operationType) {

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
        return Money.of(Math.sqrt(s2.getNumber().doubleValue()), "PLN");
    }

    public enum OperationType {BUY, SALE}
}
