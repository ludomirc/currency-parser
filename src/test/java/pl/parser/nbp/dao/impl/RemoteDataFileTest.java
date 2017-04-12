package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

/**
 * Created by Benek on 11.04.2017.
 */
public class RemoteDataFileTest {

    private final Logger logger = LogManager.getLogger(RemoteDataFileTest.class.getName());

    @Test
    public void testGetFile() throws Exception {

        logger.info("begin");

        String cacheLocation = "src/test/resources/cache/";

        //file not exist on remote location
        LocalDate date = LocalDate.parse("1990-01-28");
        String dirName = FileUtil.toDirFileName(date);

        RemoteDataFile dataFile = new RemoteDataFile(dirName, cacheLocation);

        String message = "should be null, ";
        Assert.assertNull(dataFile.getFile(), message);
        logger.info("file not exist on server: passed");

        //file exist on server
        date = LocalDate.parse("2014-01-28");
        dirName = FileUtil.toDirFileName(date);
        dataFile = new RemoteDataFile(dirName, cacheLocation);

        message = "File can not be null, ";
        Assert.assertNotNull(dataFile.getFile(), message);
        logger.info("file in cache: passed");

        //clean after test
        dataFile.getFile().delete();

        logger.info("end");

    }

}