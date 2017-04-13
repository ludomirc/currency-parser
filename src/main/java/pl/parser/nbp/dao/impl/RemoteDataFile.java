package pl.parser.nbp.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.parser.nbp.exception.AppException;
import pl.parser.nbp.exception.TechnicalException;

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

    private final Logger logger = LogManager.getLogger(RemoteDataFile.class.getName());


    public RemoteDataFile(String dirFileName, String cachePath, String currencyProviderUri) {
        super(dirFileName, cachePath, currencyProviderUri);
    }

    @Override
    public File getFile() throws AppException {
        logger.debug("begin");
        logger.debug("try obtain file from: " + getCurrencyProviderUri() + getFileName() + " and write to local cache");
        String fullUri = getCurrencyProviderUri() + getFileName();

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
            logger.error(e);
            throw new TechnicalException(e);
        }

        logger.debug("end");
        return localCopy;
    }
}
