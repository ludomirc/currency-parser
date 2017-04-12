package pl.parser.nbp;


import pl.parser.nbp.dao.ExchangeRateDao;
import pl.parser.nbp.dao.impl.ExchangeRateDaoImpl;
import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.exception.DirectoryNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {
    public static void main(String[] args) {


//        MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency("PLN").setNumber(10).create();


        //amount.pow 2013-01-28 2013-01-31

        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
        //2013-01-28 2013-01-31
        LocalDate start = LocalDate.parse("2013-01-28");
        LocalDate end = LocalDate.parse("2013-01-31");
        List<MetaFile> lsList = null;
        try {
            lsList = (List<MetaFile>) exchangeRateDao.lsCatalog(start, end);
        } catch (DirectoryNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("metafile list size :" + lsList.size());
        lsList.forEach(m -> System.out.println(m));
    }
}

