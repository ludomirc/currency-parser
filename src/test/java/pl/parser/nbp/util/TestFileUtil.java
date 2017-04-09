package pl.parser.nbp.util;

import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by Benek on 09.04.2017.
 */
public class TestFileUtil {

    @Test
    void convertDataToCatalogName() {

        String actualDirName = FileUtil.convertDataToDirName(LocalDate.of(2010, 1, 1));
        String expectedDirName = "dir2010.txt";

        String message = "should be equals";
        assertEquals(actualDirName, expectedDirName, message);

        actualDirName = FileUtil.convertDataToDirName(null);
        message = "should be null";

        assertNull(actualDirName, message);
    }
}
