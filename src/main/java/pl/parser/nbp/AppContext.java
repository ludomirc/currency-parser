package pl.parser.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.dao.ExchangeRateDao;
import pl.parser.nbp.dao.impl.DataFileProxy;
import pl.parser.nbp.dao.impl.ExchangeRateDaoImpl;
import pl.parser.nbp.service.CurrencyService;
import pl.parser.nbp.service.impl.CurrencyServiceImpl;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
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
    private String currencyProviderName = "NBP";
    private String currencyProviderURI = "http://www.nbp.pl/kursy/xml";
    private String[] supportedCurrency = new String[]{"USD", "EUR", "CHF", "GBP"};
    private String currencyLocale = "PLN";

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

    public static CurrencyService factoryCurrencyService() {
        ExchangeRateDao rateDao = new ExchangeRateDaoImpl();
        CurrencyUnit localUnit = Monetary.getCurrency(getInstance().currencyLocale);

        return new CurrencyServiceImpl(rateDao, localUnit);
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        logger.info("cache path: " + cachePath);
        this.cachePath = cachePath;
    }

    public String getCurrencyProviderName() {
        return currencyProviderName;
    }

    public void setCurrencyProviderName(String currencyProviderName) {
        logger.info("currency provider name: " + currencyProviderName);
        this.currencyProviderName = currencyProviderName;
    }

    public String getCurrencyProviderURI() {
        return currencyProviderURI;
    }

    public void setCurrencyProviderURI(String currencyProviderURI) {
        logger.info("currency provider URI: " + currencyProviderURI);
        this.currencyProviderURI = currencyProviderURI;
    }

    public String[] getSupportedCurrency() {
        return supportedCurrency;
    }

    public void setSupportedCurrency(String[] supportedCurrency) {
        logger.info("supported currency: " + Arrays.toString(supportedCurrency));
        this.supportedCurrency = supportedCurrency;
    }

    public void setSupportedCurrency(String supportedCurrency) {
        String cArray[] = supportedCurrency.split(",");
        for (int i = 0; i < cArray.length; i++) {
            cArray[i] = cArray[i].trim();
        }
        setSupportedCurrency(cArray);
    }

    public String getCurrencyLocale() {
        return currencyLocale;
    }

    public void setCurrencyLocale(String currencyLocale) {
        logger.info("currency locale: " + currencyLocale);
        this.currencyLocale = currencyLocale;
    }

    public enum ConfigParameter {
        cache_path,
        currency_provider_name,
        currency_provider_uri,
        supported_currency,
        currency_locale
    }
}
