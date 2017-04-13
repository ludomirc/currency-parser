package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.exception.AppException;

import java.io.File;

/**
 * Created by Benek on 11.04.2017.
 */
public class DataFileProxy extends AbstractDataFile {

    private Logger logger = LogManager.getLogger(DataFileProxy.class.getName());

    public DataFileProxy(String fileName, String cachePath, String currencyProviderUri) {
        super(fileName, cachePath, currencyProviderUri);
    }

    @Override
    public File getFile() throws AppException {

        logger.debug("begin");

        if (!isInLocalCache()) {
            getFileFromLocalCache();
            if (!isInLocalCache()) {

                RemoteDataFile remoteFile = new RemoteDataFile(getFileName(), getCachePath(), getCurrencyProviderUri());
                File rFile = remoteFile.getFile();
                setLocalFile(rFile);
            }
        }

        logger.debug("end");
        return getLocalFile();
    }

    private void getFileFromLocalCache() {
        LocalDataFile localDFile = new LocalDataFile(getFileName(), getCachePath());
        setLocalFile(localDFile.getFile());
    }

}
