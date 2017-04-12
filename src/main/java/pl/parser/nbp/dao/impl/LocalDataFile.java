package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by Benek on 11.04.2017.
 */
public class LocalDataFile extends AbstractDataFile {

    private final Logger logger = LogManager.getLogger(LocalDataFile.class.getName());

    public LocalDataFile(String dirFileName, String cachePath) {
        super(dirFileName, cachePath);
    }

    /**
     * Method return chandler to file DataFile
     *
     * @return return  null if file not exist otherwise file.
     */
    @Override
    public File getFile() {
        logger.debug("begin");
        File file = null;
        if (getLocalFile() == null) {
            file = new File(getLocalPath());
            if (!file.isFile()) {
                file = null;
            }
        }
        logger.debug("file: " + (file != null ? getFileName() + " is in local cache" : getFileName() + " not exits in local cache"));

        logger.debug("end");
        return file;
    }
}
