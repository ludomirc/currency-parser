package pl.parser.nbp;


import pl.parser.nbp.domain.Course;
import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.service.CatalogService;
import pl.parser.nbp.service.impl.CatalogServiceImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {
    public static void main(String[] args) {


        CatalogService catalogService = new CatalogServiceImpl();

        LocalDate start = LocalDate.parse("2002-02-01");
        LocalDate end = LocalDate.parse("2002-02-01");
        List<MetaFile> lsList = (List<MetaFile>) catalogService.getCatalog(start, end);

        lsList.forEach(m -> System.out.println(m));


        parseXml();

    }

    //!todo only for rest
    private static void parseXml() {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Course.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Course tabelaKursow = (Course) jaxbUnmarshaller.unmarshal(MainClass.class.getResourceAsStream("c001z020102.xml"));
            System.out.println(tabelaKursow);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
