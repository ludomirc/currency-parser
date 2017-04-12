package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.domain.MetaFile;

import java.time.LocalDate;

/**
 * Created by Benek on 09.04.2017.
 */
class FileUtil {

    private static final String XML_SUFFIX = ".xml";
    private static final String DIR_PREFIX = "dir";
    private static final String TXT_DIR_SUFFIX = ".txt";
    private static final Logger logger = LogManager.getLogger(FileUtil.class.getName());

    private FileUtil() {
    }

    /**
     * Tool method to convert data to dir name
     * <p>
     * <p>
     * Dodatkowo w lokalizacji tej znajduje się plik:
     * <p>
     * http://www.nbp.pl/kursy/xml/dir.txt
     * zawierający listę nazw plików (bez rozszerzenia .xml) z tabelami kursów walut.
     * <p>
     * Uwaga!
     * <p>
     * Od dnia 1 lipca 2015 r. plik dir.txt zawiera dane tylko z bieżącego roku. Dane z lat poprzednich
     * umieszczone są w plikach dir2002.txt, dir2003.txt ... dir 2015.txt. Format pliku nie uległ zmianie.
     *
     * @param date - date
     * @return return a dir name for interesting data
     */
    static String toDirFileName(LocalDate date) {
        if (date == null) {
            return null;
        }

        return DIR_PREFIX + date.getYear() + TXT_DIR_SUFFIX;
    }

    /**
     * Method crate MetaFile form string line.
     *
     * @param input - shut be popper e..g h003z130104 name of file without xml suffix
     *              for more information look into NBP doc
     * @return MetaFile
     */
    static MetaFile toMetaFile(String input) {
        if (input == null) {
            return null;
        }
        String fileName = null;
        LocalDate localDate = null;
        try {
            fileName = input + XML_SUFFIX;
            String datePart = input.split("z")[1];
            String date = "20" + datePart.substring(0, 2) + "-" + datePart.substring(2, 4) + "-" + datePart.substring(4, 6);
            localDate = LocalDate.parse(date);

        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }

        return new MetaFile(fileName, localDate);
    }
}
