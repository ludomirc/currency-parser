package pl.parser.nbp.dao.impl;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Benek on 13.04.2017.
 */
public class NetUtil {

    private boolean isReachable(String URLName) {

        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
