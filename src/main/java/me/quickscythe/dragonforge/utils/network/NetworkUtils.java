package me.quickscythe.dragonforge.utils.network;

import json2.JSONObject;
import me.quickscythe.dragonforge.exceptions.QuickException;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.chat.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {




    public static InputStream downloadFile(String url, String... auth) {
        try {
            URL myUrl = new URI(url).toURL();
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setDoOutput(true);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestMethod("GET");

            if (auth != null && auth.length >= 2) {
                String userCredentials = auth[0].trim() + ":" + auth[1].trim();
                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
                conn.setRequestProperty("Authorization", basicAuth);
            }
            return conn.getInputStream();
        } catch (Exception ex) {
            CoreUtils.logger().log(Logger.LogLevel.ERROR, "There was an error downloading that file. (" + url + ")");
            CoreUtils.logger().error(ex);
        }
        return InputStream.nullInputStream();
    }

    public static void saveStream(InputStream in, FileOutputStream out) {
        try {
            int c;
            byte[] b = new byte[1024];
            while ((c = in.read(b)) != -1) out.write(b, 0, c);

            in.close();
            out.close();
        } catch (IOException ex) {
            CoreUtils.logger().error(ex);
        }
    }
}
