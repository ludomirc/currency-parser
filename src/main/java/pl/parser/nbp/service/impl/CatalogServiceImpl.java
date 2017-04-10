package pl.parser.nbp.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.service.CatalogService;
import pl.parser.nbp.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Benek on 09.04.2017.
 */
public class CatalogServiceImpl implements CatalogService {

    /**
     * BUY_SELL_TABLE - See http://www.nbp.pl/home.aspx?f=/kursy/instrukcja_pobierania_kursow_walut.html
     * c - tabela kursów kupna i sprzedaży;
     */
    public static final char BUY_SELL_TABLE = 'c';
    private Logger logger = LogManager.getLogger(CatalogServiceImpl.class.getName());

    /**
     * @param startDate the text to  such as "2007-12-03", not null
     * @param endDate   the text to  such as "2007-12-03", not null
     * @return
     */
    @Override
    public Collection<MetaFile> getCatalog(LocalDate startDate, LocalDate endDate) {

        List<MetaFile> metaFiles = new LinkedList<MetaFile>();
        for (LocalDate i = LocalDate.parse(startDate.toString()); i.getYear() <= endDate.getYear(); i = i.plusYears(1l)) {
            logger.debug(i);
            metaFiles.addAll(getMetaFiles(startDate));
        }

        return metaFiles;
    }

    private List<MetaFile> getMetaFiles(LocalDate date) {
        List<MetaFile> metaFiles = new LinkedList<MetaFile>();
        //!todo remove hardoced server path
        URI u = URI.create("http://www.nbp.pl/kursy/xml/" + FileUtil.convertDataToDirName(date));
        try (InputStream in = u.toURL().openStream()) {
            try (Scanner inScanner = new Scanner(in)) {

                String line = null;
                MetaFile file = null;
                while (inScanner.hasNextLine()) {
                    line = inScanner.nextLine();
                    if (line.charAt(0) == BUY_SELL_TABLE) {
                        metaFiles.add(FileUtil.convertDataToMetaFile(line));
                    }

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metaFiles;
    }
}
