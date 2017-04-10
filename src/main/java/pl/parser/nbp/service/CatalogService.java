package pl.parser.nbp.service;

import pl.parser.nbp.domain.MetaFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by Benek on 09.04.2017.
 */
public interface CatalogService extends Serializable {
    Collection<MetaFile> getCatalog(LocalDate startDate, LocalDate endDate);

}
