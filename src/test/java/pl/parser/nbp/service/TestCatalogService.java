package pl.parser.nbp.service;

import org.testng.annotations.Test;
import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.service.impl.CatalogServiceImpl;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Benek on 10.04.2017.
 */
public class TestCatalogService {


    @Test
    public static void getCatalog() {
        CatalogService catalogService = new CatalogServiceImpl();

        LocalDate start = LocalDate.parse("2002-02-01");
        LocalDate end = LocalDate.parse("2002-02-01");

        List<MetaFile> lsList = (List<MetaFile>) catalogService.getCatalog(start, end);

        lsList.forEach(m -> System.out.println(m));


    }

}
