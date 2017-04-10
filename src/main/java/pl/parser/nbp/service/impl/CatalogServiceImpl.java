package pl.parser.nbp.service.impl;

import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.service.CatalogService;
import pl.parser.nbp.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Benek on 09.04.2017.
 */
public class CatalogServiceImpl implements CatalogService {

    /**
     * @param startDate the text to  such as "2007-12-03", not null
     * @param endDate   the text to  such as "2007-12-03", not null
     * @return
     */
    @Override
    public Collection<MetaFile> getCatalog(String startDate, String endDate) {
        List<String> dirList = new LinkedList<String>();


        URI u = URI.create("http://www.nbp.pl/kursy/xml/" + FileUtil.convertDataToDirName(LocalDate.parse(startDate)));
        try (InputStream in = u.toURL().openStream()) {
            Files.copy(in, Paths.get("hello.mp3"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
