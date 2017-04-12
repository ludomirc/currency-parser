package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Created by Benek on 12.04.2017.
 */
public class ExchangeRateDaoImplTest extends ExchangeRateDaoImpl {

    static private Logger logger = LogManager.getLogger(ExchangeRateDaoImplTest.class.getName());

    LocalDate start = LocalDate.parse("2013-01-28");
    LocalDate end = LocalDate.parse("2013-01-31");

    @Test
    public void testGetExchangeRate() throws Exception {

    }

    @Test
    public void testGetCatalogs() throws Exception {
        logger.info("begin");

        ExchangeRateDaoImpl rateDao = new ExchangeRateDaoImpl();

        List<File> catalogs = (List<File>) rateDao.getCatalogs(start, end);
        Assert.assertEquals(catalogs.size(), 1);
        logger.info("catalogs size test: passed");

        catalogs.forEach(catalog -> {
            Assert.assertEquals(catalog.getName(), "dir2013.txt");
        });
        logger.info("catalog dir names test: passed");


        logger.info("end");
    }

    @Test
    public void testGetDataFiles() throws Exception {
        logger.info("begin");
        ExchangeRateDaoImpl rateDao = new ExchangeRateDaoImpl();
        List<File> catalogs = (List<File>) rateDao.getCatalogs(start, end);

        catalogs.forEach(catalog -> {
            System.out.println("catalog: " + catalog);
            Collection<File> dataFile = rateDao.getDataFiles(catalog, start, end, 'c');
            dataFile.forEach(dFile -> System.out.println(dFile));
        });
        logger.info("end");
    }

}