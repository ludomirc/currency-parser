package pl.parser.nbp;


import pl.parser.nbp.dao.impl.DataFileProxy;
import pl.parser.nbp.util.FileUtil;

import java.io.File;
import java.time.LocalDate;

/**
 * Created by Benek on 09.04.2017.
 */
public class MainClass {
    public static void main(String[] args) {

//        MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency("PLN").setNumber(10).create();

        LocalDate date = LocalDate.parse("2020-01-28");
        String dirName = FileUtil.convertDataToDirName(date);
        String fullUri = "http://www.nbp.pl/kursy/xml/" + dirName;

        System.out.println(fullUri);

        String cache = "cache/";

        DataFileProxy directoryFile = new DataFileProxy(dirName, cache);
        File file = directoryFile.getFile();

        System.out.println(file.getAbsolutePath());
      /*  URI dirUri = URI.create(fullUri);
        String pathCache= "cache/";
        try (InputStream in = dirUri.toURL().openStream()) {
            try {
                Path destination = Paths.get(pathCache+ dirName);
                Path parent = destination.getParent();
                if(!Files.exists(parent)){
                    Files.createDirectory(parent);
                }
                Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(destination.toFile().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //amount.pow

  /*      CatalogDao catalogDao = new CatalogDaoImpl();
//2013-01-28 2013-01-31
        LocalDate start = LocalDate.parse("2013-01-28");
        LocalDate end = LocalDate.parse("2013-01-31");
        List<MetaFile> lsList = (List<MetaFile>) catalogDao.lsCatalog(start, end);

        lsList.forEach(m -> System.out.println(m));*/
    }
}

