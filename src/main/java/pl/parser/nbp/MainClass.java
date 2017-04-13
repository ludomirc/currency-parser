package pl.parser.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.format.CurrencyStyle;
import pl.parser.nbp.dao.impl.ExchangeRateDaoImpl;
import pl.parser.nbp.domain.CurrencyEntry;
import pl.parser.nbp.exception.AppException;
import pl.parser.nbp.service.impl.MarketUtil;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {

    private static Logger logger = LogManager.getLogger(MainClass.class.getName());

    public static void main(String[] args) throws AppException {

        AppContext.loadConfig();
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


        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        //2013-01-28 2013-01-31
/*
        LocalDate start = LocalDate.parse("2013-01-28");
        LocalDate end = LocalDate.parse("2013-01-31");
*/

        /**
         !todo brak notowanie na tą daę, dobry przykład żeby sprawdzić busines expection

         LocalDate start = LocalDate.parse("2013-01-20");
         LocalDate end = LocalDate.parse("2013-01-20");
         */


        LocalDate start = LocalDate.parse("2013-01-21");
        LocalDate end = LocalDate.parse("2013-01-21");

        Collection<CurrencyEntry> cEntries = exchangeRateDao.getExchangeRate(start, end, "EUR");

        //arithmetic average
        MonetaryAmount xBuyingRate = null;
        CurrencyUnit plnUnit = Monetary.getCurrency("PLN");
        xBuyingRate = MarketUtil.getArithmeticAverage(cEntries, MarketUtil.OperationType.BUY, plnUnit);

        MonetaryAmount xSaleRate = MarketUtil.getArithmeticAverage(cEntries, MarketUtil.OperationType.SALE, plnUnit);
        MonetaryAmount s = MarketUtil.getStandardDeviation(cEntries, xSaleRate, MarketUtil.OperationType.SALE);

        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(new Locale("pl"))
                        .set(CurrencyStyle.NUMERIC_CODE)
                        .set("pattern", "0.0000")
                        .build());

        System.out.println("Srednia arytmetyczna x: " + customFormat.format(xBuyingRate) + " " + xBuyingRate.getNumber());
        System.out.println("odchylenie standardowe s: " + customFormat.format(s) + " " + s.getNumber());


        loadConfig();
    }

    private static void loadConfig() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "/config";
            input = MainClass.class.getResourceAsStream((filename));
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            try {
                prop.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //get the property value and print it out
            System.out.println("config loaded");
            System.out.println(prop.getProperty("currency_provider_name"));
            System.out.println(prop.getProperty("currency_provider_url"));

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

}