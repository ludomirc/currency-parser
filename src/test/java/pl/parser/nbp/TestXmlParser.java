package pl.parser.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.domain.Course;
import pl.parser.nbp.domain.Currency;
import pl.parser.nbp.util.FileUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.Collection;

/**
 * Created by Benek on 10.04.2017.
 */
public class TestXmlParser {

    private Logger logger = LogManager.getLogger(TestXmlParser.class.getName());

    @Test
    public void parseXml() {
        try {

            logger.info("test start");

            JAXBContext jaxbContext = JAXBContext.newInstance(Course.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Course eRateTable = (Course) jaxbUnmarshaller.unmarshal(FileUtil.getResourceAsStream(this.getClass(), "c001z020102.xml"));

            String messageNotNull = "can not be null";
            Assert.assertNotNull(eRateTable, messageNotNull);
            logger.info("assertNotNull eRateTable: passed");


            Collection<Currency> currency = eRateTable.getCurrency();
            Assert.assertNotNull(currency, messageNotNull);
            logger.info("assertNotNull currency: passed");

            int expectedCCSize = 26;
            String message = "sould be equal";
            Assert.assertEquals(currency.size(), expectedCCSize, message);
            logger.info("currency.size: passed");

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        logger.info("test finish");
    }
}