package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.dao.ExchangeRateDao;
import pl.parser.nbp.domain.CurrencyCourseTable;
import pl.parser.nbp.domain.CurrencyEntry;
import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.exception.AppException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
public class ExchangeRateDaoImpl implements ExchangeRateDao {

    /**
     * BUY_SELL_TABLE - See http://www.nbp.pl/home.aspx?f=/kursy/instrukcja_pobierania_kursow_walut.html
     * c - tabela kursów kupna i sprzedaży;
     */
    public static final char BUY_SELL_TABLE = 'c';
    private final Logger logger = LogManager.getLogger(ExchangeRateDaoImpl.class.getName());

    @Override
    public Collection<CurrencyEntry> getExchangeRate(LocalDate from, LocalDate to, String currency) throws AppException {
        logger.debug("begin");

        Collection<File> catalogList = getCatalogs(from, to);

        //filter file by range and table type
        Collection<File> dataFileList = null;
        for (File elDir : catalogList) {
            dataFileList = getDataFilesByTableType(elDir, from, to, BUY_SELL_TABLE);

        }

        Collection<CurrencyCourseTable> cRateTables = getCurrencyCourseTables(dataFileList);

        //filter by currency code
        Collection<CurrencyEntry> currencyEntries = new LinkedList<CurrencyEntry>();
        for (CurrencyCourseTable elRateTable : cRateTables) {

            Collection<CurrencyEntry> cEntryList = elRateTable.getCurrencyEntry();
            for (CurrencyEntry elCurrency : cEntryList) {
                if (elCurrency.getCode().compareTo(currency) == 0) {
                    currencyEntries.add(elCurrency);
                }
            }
        }

        logger.debug("end");
        return currencyEntries;
    }

    protected Collection<CurrencyCourseTable> getCurrencyCourseTables(Collection<File> dataFileList) {
        logger.debug("begin");

        JAXBContext jaxbContext = null;
        Unmarshaller jaxbUnmarshaller = null;
        try {
            jaxbContext = JAXBContext.newInstance(CurrencyCourseTable.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        Collection<CurrencyCourseTable> eRateTables = new LinkedList<CurrencyCourseTable>();
        for (File elFile : dataFileList) {

            try {
                CurrencyCourseTable eRateTable = (CurrencyCourseTable) jaxbUnmarshaller.unmarshal(elFile);
                eRateTables.add(eRateTable);
            } catch (JAXBException e) {
                e.printStackTrace();
            }

        }
        logger.debug("end");

        return eRateTables;
    }


    protected Collection<File> getCatalogs(LocalDate startDate, LocalDate endDate) {
        logger.debug("begin");

        //!todo remove hardcoded initialization parameter, maybe mow to factory
        String cache = "cache/";

        DataFileProxy dirProxy = null;
        List<File> catalogList = new LinkedList<File>();
        for (LocalDate dirDate = LocalDate.parse(startDate.toString()); dirDate.getYear() <= endDate.getYear(); dirDate = dirDate.plusYears(1L)) {
            dirProxy = new DataFileProxy(FileUtil.toDirFileName(dirDate), cache);
            File dir = dirProxy.getFile();
            catalogList.add(dir);
        }

        logger.debug("end");
        return catalogList;
    }

    protected Collection<File> getDataFilesByTableType(File catalog, LocalDate from, LocalDate to, char tableType) {
        logger.debug("begin");

        //!todo remove hardcoded initialization parameter, maybe mow to factory
        String cache = "cache/";

        List<File> dataFiles = new LinkedList<File>();
        DataFileProxy dFileProxy = null;
        try (FileInputStream fIn = new FileInputStream(catalog)) {
            try (Scanner in = new Scanner(fIn)) {

                String line = "";
                MetaFile mFile = null;
                File file = null;
                while (in.hasNextLine()) {

                    line = in.nextLine();
                    if (line.charAt(0) == tableType) {

                        mFile = FileUtil.toMetaFile(line);
                        if (isDataInRange(from, to, mFile.getData())) {

                            dFileProxy = new DataFileProxy(mFile.getName(), cache);
                            file = dFileProxy.getFile();
                            dataFiles.add(file);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            //!todo throw expection
            logger.warn(e);
        } catch (IOException e) {
            logger.warn(e);
        }

        logger.debug("end");
        return dataFiles;
    }

    private Boolean isDataInRange(LocalDate startDate, LocalDate endDate, LocalDate date) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }
}
