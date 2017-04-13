package pl.parser.nbp.dao.impl;

import pl.parser.nbp.dao.DataFile;

import java.io.File;

/**
 * Created by Benek on 11.04.2017.
 */
public abstract class AbstractDataFile implements DataFile {

    private File localFile;
    private String fileName;
    private String cachePath;
    private String currencyProviderUri;

    AbstractDataFile(String fileName, String cachePath) {
        this.fileName = fileName;
        this.cachePath = cachePath;
    }

    AbstractDataFile(String fileName, String cachePath, String currencyProviderUri) {
        this.fileName = fileName;
        this.cachePath = cachePath;
        this.currencyProviderUri = currencyProviderUri;
    }

    protected String getLocalPath() {
        return cachePath + fileName;
    }

    protected String getLocalAbsolutePath() {
        return localFile.getAbsolutePath();
    }

    protected boolean isInLocalCache() {
        return localFile != null && localFile.isFile();
    }

    protected String getFileName() {
        return fileName;
    }

    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected File getLocalFile() {
        return localFile;
    }

    protected void setLocalFile(File localFile) {
        this.localFile = localFile;
    }

    protected void setLocalFile(AbstractDataFile localFile) {
        this.localFile = localFile.getLocalFile();
    }

    protected String getCachePath() {
        return cachePath;
    }

    protected void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public String getCurrencyProviderUri() {
        return currencyProviderUri;
    }

    public void setCurrencyProviderUri(String currencyProviderUri) {
        this.currencyProviderUri = currencyProviderUri;
    }

    @Override
    public boolean isExist() {
        return localFile != null && localFile.exists();
    }
}
