package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.util.FileUtil;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Benek on 12.04.2017.
 */
public class ExchangeRateDaoImplTest extends ExchangeRateDaoImpl {

    static private final Logger logger = LogManager.getLogger(ExchangeRateDaoImplTest.class.getName());

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


        Set<String> expectedFileName = new HashSet<String>();
        try (Scanner in = new Scanner(FileUtil.getResourceAsStream(this.getClass(), "expected_filenames_2013-01-28to31_type_c.txt"))) {
            in.forEachRemaining(line -> expectedFileName.add(line));
        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }


        catalogs.forEach(catalog -> {
            Collection<File> dataFile = rateDao.getDataFilesByTableType(catalog, start, end, 'c');
            dataFile.forEach(dFile -> {
                Assert.assertTrue(expectedFileName.contains(dFile.getName()));
            });
        });
        logger.info("file names test: passed");

        logger.info("end");
    }

}