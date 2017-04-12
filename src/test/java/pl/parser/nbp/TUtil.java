package pl.parser.nbp;

import java.io.InputStream;

/**
 * Created by Benek on 12.04.2017.
 */
public class TUtil {

    private TUtil() {
    }

    public static InputStream getResourceAsStream(Class clazz, String filePath) {
        if (clazz == null || filePath == null) {
            return null;
        }

        return clazz.getResourceAsStream(filePath);
    }
}
