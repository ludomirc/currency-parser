package pl.parser.nbp;


import pl.parser.nbp.dao.CatalogDao;
import pl.parser.nbp.dao.impl.CatalogDaoImpl;
import pl.parser.nbp.domain.MetaFile;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {
    public static void main(String[] args) {


        CatalogDao catalogDao = new CatalogDaoImpl();
//2013-01-28 2013-01-31
        LocalDate start = LocalDate.parse("2013-01-28");
        LocalDate end = LocalDate.parse("2013-01-31");
        List<MetaFile> lsList = (List<MetaFile>) catalogDao.lsCatalog(start, end);

        lsList.forEach(m -> System.out.println(m));
    }
}
