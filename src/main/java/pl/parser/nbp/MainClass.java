package pl.parser.nbp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.format.CurrencyStyle;
import pl.parser.nbp.dao.impl.ExchangeRateDaoImpl;
import pl.parser.nbp.domain.CurrencyEntry;
import pl.parser.nbp.exception.AppException;
import pl.parser.nbp.util.MarketUtil;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {

    private static Logger logger = LogManager.getLogger(MainClass.class.getName());

    public static void main(String[] args) throws AppException {


//        MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency("PLN").setNumber(10).create();


        //amount.pow 2013-01-28 2013-01-31

        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        //2013-01-28 2013-01-31
        LocalDate start = LocalDate.parse("2013-01-28");
        LocalDate end = LocalDate.parse("2013-01-31");

        Collection<CurrencyEntry> cEntries = exchangeRateDao.getExchangeRate(start, end, "EUR");


        //arithmetic average
        MonetaryAmount xBuyingRate = null;

        CurrencyUnit plnUnit = Monetary.getCurrency("PLN");
        xBuyingRate = MarketUtil.getArithmeticAverage(cEntries, MarketUtil.OperationType.BUY, plnUnit);

        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(new Locale("pl"))
                        .set(CurrencyStyle.NUMERIC_CODE)
                        .set("pattern", "0.0000")
                        .build());

        MonetaryAmount xSaleRate = MarketUtil.getArithmeticAverage(cEntries, MarketUtil.OperationType.SALE, plnUnit);
        MonetaryAmount s = MarketUtil.getStandardDeviation(cEntries, xSaleRate, MarketUtil.OperationType.SALE);

        System.out.println("Srednia arytmetyczna x: " + customFormat.format(xBuyingRate) + " " + xBuyingRate.getNumber());
        System.out.println("odchylenie standardowe s: " + customFormat.format(s) + " " + s.getNumber());
    }


}

