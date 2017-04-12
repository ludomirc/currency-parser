package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.domain.MetaFile;

import java.time.LocalDate;

import static org.testng.Assert.assertNull;

/**
 * Created by Benek on 11.04.2017.
 */
public class FileUtilTest {

    private final Logger logger = LogManager.getLogger(FileUtilTest.class.getName());

    @Test
    public void testToDirFileName() throws Exception {
        logger.info("begin");

        String actualDirName = FileUtil.toDirFileName(LocalDate.parse("2010-01-01"));
        String expectedDirName = "dir2010.txt";
        Assert.assertEquals(actualDirName, expectedDirName);
        logger.info("directory name test: passed");


        actualDirName = FileUtil.toDirFileName(null);
        assertNull(actualDirName);
        logger.info("null directory test: passed");

        logger.info("end");
    }

    @Test
    public void testToMetaFile() throws Exception {
        logger.info("begin");

        String fileName = "c001z130102";
        MetaFile mFile = FileUtil.toMetaFile(fileName);

        Assert.assertNotNull(mFile);
        logger.info("Is merta file exist test: passed");

        Assert.assertEquals(mFile.getName(), "c001z130102.xml");
        logger.info("MetaFile name test: passed");

        Assert.assertEquals(mFile.getData(), LocalDate.parse("2013-01-02"));
        logger.info("MetaFile date test: passed");

        logger.info("end");
    }

    @Test
    public void testGetResourceAsStream() throws Exception {
    }

}