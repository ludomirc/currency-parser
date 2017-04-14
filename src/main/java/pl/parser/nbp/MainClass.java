package pl.parser.nbp;


import pl.parser.nbp.exception.AppException;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {

    private static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(MainClass.class.getName());

    public static void main(String[] args) throws AppException {
        disableDefaultLogger();
        logger.info("app start");
        AppContext.loadConfig();
        CurrencyCheck converter = new CurrencyCheck();
        converter.checkRate(args);

    }

    private static void disableDefaultLogger() {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
    }
}