package pl.parser.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.dao.impl.DataFileProxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Benek on 13.04.2017.
 */
public class AppContext {

    private static Logger logger = LogManager.getLogger(AppContext.class.getName());
    private static AppContext instance = null;
    private String configFilePath = "/config";
    private String cachePath = "cache/";
    private String CurrencyProviderName = "NBP";
    private String CurrencyProviderURI = "http://www.nbp.pl/kursy/xml";
    private String[] SupportedCurrency = new String[]{"USD", "EUR", "CHF", "GBP"};
    private String CurrencyLocale = "PLN";

    private AppContext() {
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }

        return instance;
    }

    public static void loadConfig() {
        logger.info("start");
        AppContext appContext = getInstance();

        Properties prop = new Properties();
        InputStream input = null;

        try {
            String filename = appContext.configFilePath;
            input = AppContext.class.getResourceAsStream((filename));
            if (input == null) {
                System.out.println("configuration file not exist: " + filename);
                logger.warn("!!! configuration file not exist: " + filename, ", has be applied default settings");
            } else {

                try {
                    prop.load(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                appContext.setCachePath(prop.getProperty(ConfigParameter.cache_path.name()));
                appContext.setCurrencyProviderName(prop.getProperty(ConfigParameter.currency_provider_name.name()));
                appContext.setCurrencyProviderURI(prop.getProperty(ConfigParameter.currency_provider_uri.name()));
                appContext.setSupportedCurrency(prop.getProperty(ConfigParameter.supported_currency.name()));
                appContext.setCurrencyLocale(prop.getProperty(ConfigParameter.currency_locale.name()));

                logger.info("configuration loaded");
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        logger.info("end");
    }


    public static DataFileProxy factoryDataFileProxy(String file) {
        return new DataFileProxy(file, getInstance().cachePath, getInstance().getCurrencyProviderURI());
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        logger.info("cache path: " + cachePath);
        this.cachePath = cachePath;
    }

    public String getCurrencyProviderName() {
        return CurrencyProviderName;
    }

    public void setCurrencyProviderName(String currencyProviderName) {
        logger.info("currency provider name: " + currencyProviderName);
        CurrencyProviderName = currencyProviderName;
    }

    public String getCurrencyProviderURI() {
        return CurrencyProviderURI;
    }

    public void setCurrencyProviderURI(String currencyProviderURI) {
        logger.info("currency provider URI: " + currencyProviderURI);
        CurrencyProviderURI = currencyProviderURI;
    }

    public String[] getSupportedCurrency() {
        return SupportedCurrency;
    }

    public void setSupportedCurrency(String[] supportedCurrency) {
        logger.info("supported currency: " + Arrays.toString(supportedCurrency));
        SupportedCurrency = supportedCurrency;
    }

    public void setSupportedCurrency(String supportedCurrency) {
        setSupportedCurrency(supportedCurrency.split(","));
    }

    public String getCurrencyLocale() {
        return CurrencyLocale;
    }

    public void setCurrencyLocale(String currencyLocale) {
        logger.info("currency locale: " + currencyLocale);
        CurrencyLocale = currencyLocale;
    }

    public enum ConfigParameter {
        cache_path,
        currency_provider_name,
        currency_provider_uri,
        supported_currency,
        currency_locale
    }
}
