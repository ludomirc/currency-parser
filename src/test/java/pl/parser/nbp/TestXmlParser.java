package pl.parser.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.domain.CurrencyCourseTable;
import pl.parser.nbp.domain.CurrencyEntry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.Collection;

/**
 * Created by Benek on 10.04.2017.
 */
public class TestXmlParser {

    private final Logger logger = LogManager.getLogger(TestXmlParser.class.getName());

    @Test
    public void parseXml() {
        try {

            logger.info("test start");

            JAXBContext jaxbContext = JAXBContext.newInstance(CurrencyCourseTable.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            CurrencyCourseTable eRateTable = (CurrencyCourseTable) jaxbUnmarshaller.unmarshal(TUtil.getResourceAsStream(this.getClass(), "c001z020102.xml"));

            String messageNotNull = "can not be null";
            Assert.assertNotNull(eRateTable, messageNotNull);
            logger.info("assertNotNull eRateTable: passed");


            Collection<CurrencyEntry> currencyEntry = eRateTable.getCurrencyEntry();
            Assert.assertNotNull(currencyEntry, messageNotNull);
            logger.info("assertNotNull currencyEntry: passed");

            int expectedCCSize = 26;
            String message = "sould be equal";
            Assert.assertEquals(currencyEntry.size(), expectedCCSize, message);
            logger.info("currencyEntry.size: passed");

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        logger.info("test finish");
    }
}
