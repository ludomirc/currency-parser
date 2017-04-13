package pl.parser.nbp.dao;

import pl.parser.nbp.exception.AppException;

import java.io.File;

/**
 * Created by Benek on 11.04.2017.
 */
public interface DataFile {

    File getFile() throws AppException;

    boolean isExist();
}
