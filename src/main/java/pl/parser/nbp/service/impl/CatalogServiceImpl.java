package pl.parser.nbp.service.impl;

import pl.parser.nbp.model.File;
import pl.parser.nbp.service.CatalogService;

import java.util.Collection;

/**
 * Created by Benek on 09.04.2017.
 */
public class CatalogServiceImpl implements CatalogService {

    @Override
    public Collection<File> getCatalog(String startDate, String endDate) {

        /*
         Path path = ...
     URI u = URI.create("http://java.sun.com/");
     try (InputStream in = u.toURL().openStream()) {
         Files.copy(in, path);
     }
         */


        /*
        try (InputStream is =  fileUrlConnection.getInputStream()) {
            Files.copy(is, Paths.get("hello.mp3"));
        }*/
        return null;
    }
}
