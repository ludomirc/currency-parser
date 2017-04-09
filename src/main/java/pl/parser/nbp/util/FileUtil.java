package pl.parser.nbp.util;

import java.time.LocalDate;

/**
 * Created by Benek on 09.04.2017.
 */
public class FileUtil {

    private static final String DIR_PREFIX = "dir";
    private static final String DIR_SUFFIX = ".txt";

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
    public static String convertDataToDirName(LocalDate date) {
        if (date == null) {
            return null;
        }

        return DIR_PREFIX + date.getYear() + DIR_SUFFIX;
    }

}
