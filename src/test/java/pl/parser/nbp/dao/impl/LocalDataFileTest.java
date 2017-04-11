package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.dao.DataFile;
import pl.parser.nbp.util.FileUtil;

import java.time.LocalDate;

/**
 * Created by Benek on 11.04.2017.
 */
public class LocalDataFileTest {

    private Logger logger = LogManager.getLogger(LocalDataFileTest.class.getName());

    @Test
    public void testGetFile() throws Exception {
        logger.info("begin");

        String cacheLocation = "src/test/resources/cache/";

        LocalDate date = LocalDate.parse("1990-01-28");
        String dirName = FileUtil.convertToFileName(date);

        DataFile dataFile = new LocalDataFile(dirName, cacheLocation);

        String message = "should be null";
        Assert.assertNull(dataFile.getFile(), message);
        logger.info("cache is empty: passed");

        date = LocalDate.parse("2013-01-28");
        dirName = FileUtil.convertToFileName(date);
        dataFile = new LocalDataFile(dirName, cacheLocation);

        message = "File can not be null";
        Assert.assertNotNull(dataFile.getFile(), message);
        logger.info("file in cache: passed");

        logger.info("end");
    }

}