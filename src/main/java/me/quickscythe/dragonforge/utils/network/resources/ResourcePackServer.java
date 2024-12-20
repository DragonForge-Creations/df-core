package me.quickscythe.dragonforge.utils.network.resources;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.network.NetworkUtils;
import me.quickscythe.dragonforge.utils.storage.DataManager;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static net.kyori.adventure.text.Component.text;

public class ResourcePackServer {

    private final File pack;
    private final int port;
    String url = "";

    public ResourcePackServer(int port) {
        pack = new File(DataManager.getDataFolder(), "resources/pack.zip");
        if (!pack.getParentFile().isDirectory()) pack.getParentFile().mkdirs();
        this.port = port;
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/resources.zip", new ResourcePackHandler(this));
            server.createContext("/update", new ResourcePackUpdater(this));
            server.setExecutor(null);
            server.start();
            updatePack();
        } catch (IOException e) {
            CoreUtils.logger().error("ResourcePackServer", e);
        }
    }

    public boolean enabled() {
        return !url.isEmpty();
    }

    public void setUrl(String url) {
        this.url = url;
        updatePack();
    }

    public void updatePack() {
        if (!enabled()) return;
        String[] props = new String[]{CoreUtils.config().getData().getString("jenkins_user"), CoreUtils.config().getData().getString("jenkins_password"), CoreUtils.config().getData().getString("jenkins_url"), CoreUtils.config().getData().getString("jenkins_api_endpoint")};
        try {
            NetworkUtils.saveStream(NetworkUtils.downloadFile(url, props[0], props[1]), new FileOutputStream(pack));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public File pack() {
        return pack;
    }

    public void setPack(Player player) throws IOException, NoSuchAlgorithmException {
        String url = "http://localhost:" + port + "/resources.zip";
        byte[] buffer = new byte[8192];
        int count;
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        try (BufferedInputStream bis = new BufferedInputStream(NetworkUtils.downloadFile(url))) {
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
        }

        byte[] hash = digest.digest();
        player.setResourcePack(url, hash, text("This pack is required for the best experience on this server."));
    }

    static class ResourcePackHandler implements HttpHandler {

        private final ResourcePackServer server;

        public ResourcePackHandler(ResourcePackServer server) {
            this.server = server;
        }


        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String filePath = server.pack().getPath(); // Update this path to your file
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

            // Set the response headers and status code
            exchange.sendResponseHeaders(200, fileBytes.length);

            // Write the file bytes to the response body
            OutputStream os = exchange.getResponseBody();
            os.write(fileBytes);
            os.close();
        }
    }

    static class ResourcePackUpdater implements HttpHandler {

        private final ResourcePackServer server;

        public ResourcePackUpdater(ResourcePackServer server) {
            this.server = server;
        }


        @Override
        public void handle(HttpExchange exchange) throws IOException {
            server.updatePack();
        }
    }
}
