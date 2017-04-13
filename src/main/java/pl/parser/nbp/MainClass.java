package pl.parser.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.exception.AppException;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {

    private static Logger logger = LogManager.getLogger(MainClass.class.getName());

    public static void main(String[] args) throws AppException {
        AppContext.loadConfig();
        CurrencyCheck converter = new CurrencyCheck();
        String[] arg = new String[]{"EUR", "2013-01-28", "2013-01-31"};
        converter.checkRate(arg);
    }

    private static void back() throws AppException {
        /**
         !todo brak notowanie na tą daę, dobry przykład żeby sprawdzić busines expection

         LocalDate start = LocalDate.parse("2013-01-20");
         LocalDate end = LocalDate.parse("2013-01-20");
         */
    }

}