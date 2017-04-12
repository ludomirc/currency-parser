package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.dao.CatalogDao;
import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.exception.DirectoryNotFoundException;
import pl.parser.nbp.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by Benek on 09.04.2017.
 */
public class CatalogDaoImpl implements CatalogDao {

    /**
     * BUY_SELL_TABLE - See http://www.nbp.pl/home.aspx?f=/kursy/instrukcja_pobierania_kursow_walut.html
     * c - tabela kursów kupna i sprzedaży;
     */
    public static final char BUY_SELL_TABLE = 'c';
    private Logger logger = LogManager.getLogger(CatalogDaoImpl.class.getName());

    /**
     * @param startDate the text to  such as "2007-12-03", not null
     * @param endDate   the text to  such as "2007-12-03", not null
     * @return
     */
    @Override
    public Collection<MetaFile> lsCatalog(LocalDate startDate, LocalDate endDate) throws DirectoryNotFoundException {
        logger.debug("begin");
        List<MetaFile> metaFiles = new LinkedList<MetaFile>();
        for (LocalDate i = LocalDate.parse(startDate.toString()); i.getYear() <= endDate.getYear(); i = i.plusYears(1l)) {
            logger.debug(i);
            metaFiles.addAll(loadMetaFiles(i, startDate, endDate));
        }

        logger.debug("end");
        return metaFiles;
    }

    private List<MetaFile> loadMetaFiles(LocalDate dirDate, LocalDate startDate, LocalDate endDate) throws DirectoryNotFoundException {

        logger.debug("begin");
        //!todo remove hardcoded initialization parameter, maybe mow to factory
        String cache = "cache/";
        DataFileProxy dirProxy = new DataFileProxy(FileUtil.toDirFileName(dirDate), cache);

        File dirFile = dirProxy.getFile();
        if (!dirProxy.isExist()) {
            throw new DirectoryNotFoundException("File " + dirProxy.getFileName() + " exist neither in local cache  nor on server");
        }

        List<MetaFile> metaFiles = getMetaFiles(startDate, endDate, dirFile);

        logger.debug("end");
        return metaFiles;
    }

    private List<MetaFile> getMetaFiles(LocalDate startDate, LocalDate endDate, File dirFile) {
        List<MetaFile> metaFiles = new LinkedList<MetaFile>();
        logger.debug(dirFile.getAbsolutePath());

        try (FileInputStream fIn = new FileInputStream(dirFile)) {
            try (Scanner in = new Scanner(fIn)) {
                String line = "";
                MetaFile mFile = null;
                while (in.hasNextLine()) {
                    line = in.nextLine();
                    if (line.charAt(0) == BUY_SELL_TABLE) {
                        mFile = FileUtil.toMetaFile(line);
                        if (isDataInRange(startDate, endDate, mFile.getData())) {
                            metaFiles.add(mFile);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.warn(e);
        } catch (IOException e) {
            logger.warn(e);
        }
        return metaFiles;
    }

    private Boolean isDataInRange(LocalDate startDate, LocalDate endDate, LocalDate date) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }
}
