package pl.parser.nbp;


import pl.parser.nbp.exception.AppException;

import java.time.LocalDate;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {
    public static void main(String[] args) throws AppException {


//        MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency("PLN").setNumber(10).create();


        //amount.pow 2013-01-28 2013-01-31
/*
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();*/
        //2013-01-28 2013-01-31
        LocalDate start = LocalDate.parse("2013-01-28");
        LocalDate end = LocalDate.parse("2013-01-31");

    }
}

