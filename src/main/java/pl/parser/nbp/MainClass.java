package pl.parser.nbp;


import pl.parser.nbp.model.Course;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {
    public static void main(String[] args) {
        try {

           /* File file = new File("C:\\file.xml");*/
            JAXBContext jaxbContext = JAXBContext.newInstance(Course.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Course tabelaKursow = (Course) jaxbUnmarshaller.unmarshal(MainClass.class.getResourceAsStream("c001z020102.xml"));
            System.out.println(tabelaKursow);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
