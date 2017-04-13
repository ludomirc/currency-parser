package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.exception.AppException;
import pl.parser.nbp.exception.TechnicalException;

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

        //file exist on server
        LocalDate date = LocalDate.parse("2014-01-28");
        String dirName = FileUtil.toDirFileName(date);
        RemoteDataFile dataFile = new RemoteDataFile(dirName, cacheLocation);

        String message = "File can not be null, ";
        Assert.assertNotNull(dataFile.getFile(), message);
        logger.info("file in cache: passed");

        //clean after test
        dataFile.getFile().delete();

        logger.info("end");

    }

    @Test(expectedExceptions = {TechnicalException.class})
    public void throwIfOrderIsNotExists() throws AppException {

        String cacheLocation = "src/test/resources/cache/";

        //file not exist on remote location
        LocalDate date = LocalDate.parse("1990-01-28");
        String dirName = FileUtil.toDirFileName(date);

        RemoteDataFile dataFile = new RemoteDataFile(dirName, cacheLocation);

        dataFile.getFile();

        logger.info("RemoteDataFile TechnicalException: passed");
    }

}