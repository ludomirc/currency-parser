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
public class DataFileProxyTest {

    private Logger logger = LogManager.getLogger(DataFileProxyTest.class.getName());

    @Test
    public void testGetFile() throws Exception {
        logger.info("begin");

        String cacheLocation = "src/test/resources/cache/";

        //file nor exist in cache and on server
        LocalDate date = LocalDate.parse("1990-01-28");
        String dirName = FileUtil.toDirFileName(date);

        DataFile dataFile = new DataFileProxy(dirName, cacheLocation);

        String message = "should be null";
        Assert.assertNull(dataFile.getFile(), message);
        logger.info("file exist neither in local cache and on server: passed");

        //file is in local cache test
        date = LocalDate.parse("2013-01-28");
        dirName = FileUtil.toDirFileName(date);
        dataFile = new LocalDataFile(dirName, cacheLocation);

        message = "File can not be null";
        Assert.assertNotNull(dataFile.getFile(), message);
        logger.info("file is in local cache: passed");

        //file exist on server but not is in cache, download test
        date = LocalDate.parse("2014-01-28");
        dirName = FileUtil.toDirFileName(date);
        dataFile = new RemoteDataFile(dirName, cacheLocation);

        message = "File can not be null";
        Assert.assertNotNull(dataFile.getFile(), message);
        logger.info("file in cache: passed");

        //clean after test
        dataFile.getFile().delete();


        logger.info("end");

    }

}