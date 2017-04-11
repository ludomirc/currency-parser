package pl.parser.nbp.service;

import org.testng.annotations.Test;
import pl.parser.nbp.dao.CatalogDao;
import pl.parser.nbp.dao.impl.CatalogDaoImpl;
import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.exception.DirectoryNotFoundException;
import pl.parser.nbp.util.FileUtil;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;

/**
 * Created by Benek on 10.04.2017.
 */
public class TestCatalogDao {


    @Test
    public static void getOneYearCatalog() {

        List<String> expectedFileNameList = new LinkedList<String>();

        try (Scanner expectedIn = new Scanner(FileUtil.getResourceAsStream(TestCatalogDao.class, "/pl/parser/nbp/expected_dir2002.txt"))) {
            while (expectedIn.hasNextLine()) {
                expectedFileNameList.add(expectedIn.nextLine());
            }
        }

        CatalogDao catalogDao = new CatalogDaoImpl();
        LocalDate start = LocalDate.parse("2002-02-01");
        LocalDate end = LocalDate.parse("2002-02-01");
        List<MetaFile> actualLsList = null;
        try {
            actualLsList = (List<MetaFile>) catalogDao.lsCatalog(start, end);
        } catch (DirectoryNotFoundException e) {
            e.printStackTrace();
        }

        String message = "should be equal";
        assertEquals(actualLsList.size(), expectedFileNameList.size(), message);

        ListIterator<String> expectedItr = expectedFileNameList.listIterator();
        ListIterator<MetaFile> actualItr = actualLsList.listIterator();

        while (actualItr.hasNext()) {
            assertEquals(actualItr.next().getName(), expectedItr.next(), message);
        }

    }


}
