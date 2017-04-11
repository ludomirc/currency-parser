package pl.parser.nbp.util;

import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by Benek on 09.04.2017.
 */
public class TestDataFileUtil {

    @Test
    void convertDataToCatalogName() {

        String actualDirName = FileUtil.convertToFileName(LocalDate.parse("2010-01-01"));
        String expectedDirName = "dir2010.txt";

        String message = "should be equals";
        assertEquals(actualDirName, expectedDirName, message);
        System.out.println(actualDirName);

        actualDirName = FileUtil.convertToFileName(null);
        message = "should be null";

        assertNull(actualDirName, message);
    }

}
