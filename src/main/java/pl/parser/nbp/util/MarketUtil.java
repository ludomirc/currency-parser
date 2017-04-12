package pl.parser.nbp.util;

import org.javamoney.moneta.Money;
import pl.parser.nbp.domain.CurrencyEntry;

import javax.money.MonetaryAmount;
import java.util.Collection;

/**
 * Created by Benek on 12.04.2017.
 */
public class MarketUtil {

    public static MonetaryAmount getArithmeticAverage(Collection<CurrencyEntry> entries, OperationType operationType, String currency) {
        MonetaryAmount average = Money.of(0, currency);
        for (CurrencyEntry entry : entries) {
            switch (operationType) {
                case BUY:
                    average = average.add(Money.of(Double.parseDouble(entry.getBuyingRate()), currency));
                    break;
                case SALE:
                    average = average.add(Money.of(Double.parseDouble(entry.getSalaryRate()), currency));
                    break;
            }
        }
        average = average.divide(entries.size());
        return average;
    }

    public enum OperationType {BUY, SALE}
}
