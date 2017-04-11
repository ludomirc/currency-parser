package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by Benek on 11.04.2017.
 */
public class RemoteDataFile extends AbstractDataFile {

    String nbpUri = "http://www.nbp.pl/kursy/xml/";
    private Logger logger = LogManager.getLogger(DataFileProxy.class.getName());

    public RemoteDataFile(String dirFileName, String cachePath) {
        super(dirFileName, cachePath);
    }

    /**
     * Method get file form server save to local storage
     *
     * @return return handler to local file
     */
    @Override
    public File getFile() {
        logger.debug("begin");
        logger.debug("try obtain file from: " + getUri() + "/" + getFileName() + " and write to local cache");
        String fullUri = nbpUri + getFileName();

        Path destination = null;
        URI dirUri = URI.create(fullUri);
        try (InputStream in = dirUri.toURL().openStream()) {
            try {
                destination = Paths.get(getLocalPath());
                Path parent = destination.getParent();
                if (!Files.exists(parent)) {
                    Files.createDirectory(parent);
                }
                Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
                logger.debug("file: " + destination.toFile().getAbsolutePath() + " saved in local cache");
            } catch (IOException e) {
                logger.warn(e);
            }
        } catch (IOException e) {
            logger.warn(e);
        }
        File file = destination.toFile();
        logger.debug("end");
        return file;
    }

    protected String getUri() {
        return nbpUri;
    }
}
