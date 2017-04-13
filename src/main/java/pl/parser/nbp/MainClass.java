package pl.parser.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.format.CurrencyStyle;
import pl.parser.nbp.dao.impl.ExchangeRateDaoImpl;
import pl.parser.nbp.exception.AppException;
import pl.parser.nbp.service.CurrencyService;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {

    private static Logger logger = LogManager.getLogger(MainClass.class.getName());

    public static void main(String[] args) throws AppException {

        AppContext.loadConfig();
        CurrencyCheck converter = new CurrencyCheck();
        String[] arg = new String[]{"EUR", "2013-01-28", "2013-01-31"};
        converter.checkRate(arg);

        /**
         * Uruchomienie programu z przykładowymi poprawnymi danymi wejściowymi:
         java pl.parser.nbp.MainClass EUR 2013-01-28 2013-01-31

         Poprawne dane wyjściowe dla powyżej przedstawionego przykładowego wywołania:
         4,1505
         0,0125
         Opis danych wejściowych i wyjściowych:
         EUR › kod waluty
         2013-01-28 › data początkowa
         2012-01-31 › data końcowa
         4,1505 › średni kurs kupna
         0,0125 › odchylenie standardowe kursów sprzedaży
         */
        //back();

    }

    private static void back() throws AppException {
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        //2013-01-28 2013-01-31
        LocalDate start = LocalDate.parse("2013-01-28");
        LocalDate end = LocalDate.parse("2013-01-31");

        /**
         !todo brak notowanie na tą daę, dobry przykład żeby sprawdzić busines expection

         LocalDate start = LocalDate.parse("2013-01-20");
         LocalDate end = LocalDate.parse("2013-01-20");
         */
        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(new Locale("pl"))
                        .set(CurrencyStyle.NUMERIC_CODE)
                        .set("pattern", "0.0000")
                        .build());


        CurrencyService cService = AppContext.factoryCurrencyService();

        CurrencyUnit currency = Monetary.getCurrency("EUR");
        MonetaryAmount xBuyingRate = cService.getAverageBuyRate(start, end, currency);
        MonetaryAmount s = cService.getSalesRateStandardDeviation(start, end, currency);
        System.out.println("Srednia arytmetyczna x: " + customFormat.format(xBuyingRate));
        System.out.println("odchylenie standardowe s: " + customFormat.format(s));
    }

}