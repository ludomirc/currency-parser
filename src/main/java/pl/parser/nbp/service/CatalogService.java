package pl.parser.nbp.service;

import pl.parser.nbp.model.File;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Benek on 09.04.2017.
 */
public interface CatalogService extends Serializable {
    Collection<File> getCatalog(String startDate, String endDate);
}
