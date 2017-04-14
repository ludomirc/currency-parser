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
        logger.info("app start");
        AppContext.loadConfig();
        CurrencyCheck converter = new CurrencyCheck();
        converter.checkRate(args);
    }
}