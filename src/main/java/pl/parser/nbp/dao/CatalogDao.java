package pl.parser.nbp.dao;

import pl.parser.nbp.domain.MetaFile;
import pl.parser.nbp.exception.DirectoryNotFoundException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by Benek on 09.04.2017.
 */
public interface CatalogDao extends Serializable {
    Collection<MetaFile> lsCatalog(LocalDate startDate, LocalDate endDate) throws DirectoryNotFoundException;

}
