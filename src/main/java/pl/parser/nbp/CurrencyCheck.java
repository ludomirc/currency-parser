package pl.parser.nbp;

import org.javamoney.moneta.format.CurrencyStyle;
import pl.parser.nbp.exception.AppException;
import pl.parser.nbp.exception.BusinessException;
import pl.parser.nbp.exception.ErrorCode;
import pl.parser.nbp.exception.TechnicalException;
import pl.parser.nbp.service.CurrencyService;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Benek on 13.04.2017.
 */
public class CurrencyCheck {

    private final int CURRENCY_CODE = 0;
    private final int START_DATE = 1;
    private final int END_DATE = 2;
    CurrencyService cService;
    private MonetaryAmount xBuyingRate;
    private MonetaryAmount sDeviation;

    public CurrencyCheck() {
        cService = AppContext.factoryCurrencyService();

    }

    public void checkRate(String[] args) throws AppException {

        if (args.length != 3) {
            throw new TechnicalException(ErrorCode.ErrorCode_2002, ": expected 3, actual:" + args.length);
        }

        CurrencyUnit currency = parseCurrency(args[CURRENCY_CODE]);
        LocalDate start = parseDate(args[START_DATE]);
        LocalDate end = parseDate(args[END_DATE]);
        checkSequenceOfDates(start, end);

        xBuyingRate = cService.getAverageBuyRate(start, end, currency);
        sDeviation = cService.getSalesRateStandardDeviation(start, end, currency);

        showResult();

    }

    private void checkSequenceOfDates(LocalDate start, LocalDate end) throws BusinessException {
        if (start.compareTo(end) > 0) {
            throw new BusinessException(ErrorCode.ErrorCode_3003, " first should be a start date next an end date");
        }
    }

    private LocalDate parseDate(String date) throws AppException {
        LocalDate lDate;
        try {
            lDate = LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            throw new TechnicalException(ex, ErrorCode.ErrorCode_2003, ": expected YYYY-MM-DD, actual: " + date);
        }
        //2002 is init year of the rate data
        if (lDate.getYear() < 2002) {
            throw new BusinessException(ErrorCode.ErrorCode_3002, ": yest should be greater then 2001, actual: " + date);
        }

        if (lDate.compareTo(LocalDate.now()) > 0) {
            throw new BusinessException(ErrorCode.ErrorCode_3004, " input date is in future: " + date);
        }

        return lDate;
    }

    private CurrencyUnit parseCurrency(String currency) throws BusinessException {
        String[] supportedCurrency = AppContext.getInstance().getSupportedCurrency();
        currency = currency.trim();
        boolean isSupported = false;
        for (String elCurrency : supportedCurrency) {

            if (elCurrency.compareTo(currency) == 0) {
                isSupported = true;
                break;
            }
        }

        if (!isSupported) {
            throw new BusinessException(ErrorCode.ErrorCode_3001, ", expected: " + Arrays.toString(supportedCurrency) + " actual: " + currency);
        }
        CurrencyUnit cUnit = Monetary.getCurrency(currency);

        return cUnit;
    }


    private void showResult() {
        MonetaryAmountFormat customFormat = getMonetaryAmountFormat();

        System.out.println(customFormat.format(xBuyingRate));
        System.out.println(customFormat.format(sDeviation));
    }

    private MonetaryAmountFormat getMonetaryAmountFormat() {
        return MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(new Locale("pl"))
                        .set(CurrencyStyle.NUMERIC_CODE)
                        .set("pattern", "0.0000")
                        .build());
    }
}
