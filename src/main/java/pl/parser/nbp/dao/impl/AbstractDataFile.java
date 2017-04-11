package pl.parser.nbp.dao.impl;

import pl.parser.nbp.dao.DataFile;

/**
 * Created by Benek on 11.04.2017.
 */
public abstract class AbstractDataFile implements DataFile {

    private java.io.File localFile;
    private String fileName;
    private String cachePath;

    AbstractDataFile(String fileName, String cachePath) {
        this.fileName = fileName;
        this.cachePath = cachePath;
    }

    protected String getLocalPath() {
        return cachePath + fileName;
    }

    protected String getLocalAbsolvePath() {
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

    protected java.io.File getLocalFile() {
        return localFile;
    }

    protected void setLocalFile(AbstractDataFile localFile) {
        this.localFile = localFile.getLocalFile();
    }

    protected void setLocalFile(java.io.File localFile) {
        this.localFile = localFile;
    }

    protected String getCachePath() {
        return cachePath;
    }

    protected void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }


}
