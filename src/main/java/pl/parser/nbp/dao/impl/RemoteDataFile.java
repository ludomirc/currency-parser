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


    /**
     * !todo - remove to configuration file or factory
     */
    String nbpUri = "http://www.nbp.pl/kursy/xml/";
    private Logger logger = LogManager.getLogger(RemoteDataFile.class.getName());

    public RemoteDataFile(String dirFileName, String cachePath) {
        super(dirFileName, cachePath);
    }

    @Override
    public File getFile() {
        logger.debug("begin");
        logger.debug("try obtain file from: " + getUri() + "/" + getFileName() + " and write to local cache");
        String fullUri = nbpUri + getFileName();

        Path destination = null;
        URI dirUri = URI.create(fullUri);
        File localCopy = null;
        try (InputStream in = dirUri.toURL().openStream()) {

            destination = Paths.get(getLocalPath());
            Path parent = destination.getParent();

            if (!Files.exists(parent)) {
                Files.createDirectory(parent);
                logger.info("Cache directory not exist, created cache directory in " + parent.toAbsolutePath() + " location.");
            }

            Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
            logger.debug("file: " + destination.toFile().getAbsolutePath() + " saved in local cache");

            localCopy = destination.toFile();
            setLocalFile(localCopy);
        } catch (IOException e) {
            logger.warn(e);
        }

        logger.debug("end");
        return localCopy;
    }

    protected String getUri() {
        return nbpUri;
    }
}
