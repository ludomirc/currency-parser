package pl.parser.nbp;


import pl.parser.nbp.model.TabelaKursow;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {
    public static void main(String[] args) {
        try {

            File file = new File("C:\\file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TabelaKursow.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TabelaKursow tabelaKursow = (TabelaKursow) jaxbUnmarshaller.unmarshal(file);
            System.out.println(tabelaKursow);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
