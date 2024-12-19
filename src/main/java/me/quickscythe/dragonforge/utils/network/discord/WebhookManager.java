package me.quickscythe.dragonforge.utils.network.discord;

import json2.JSONArray;
import json2.JSONObject;
import me.quickscythe.dragonforge.exceptions.QuickException;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.network.discord.embed.Embed;
import me.quickscythe.dragonforge.utils.storage.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class WebhookManager extends ConfigManager {
    private final Map<String, Webhook> WEBHOOKS = new HashMap<>();

    public WebhookManager(JavaPlugin plugin) {
        super(plugin, "webhooks");
    }

    @Override
    public void start() {
        super.start();
        if (!config().getData().isEmpty()) for (String key : config().getData().keySet()) {
            JSONObject webhook = config().getData().getJSONObject(key);
            add(key, webhook.getString("id"), webhook.getString("token"));
            CoreUtils.logger().log("WebhookManager", "Added webhook " + key);
        }
    }

    @Override
    public void end() {
        WEBHOOKS.forEach((name, webhook) -> {
            JSONObject data = new JSONObject();
            data.put("id", webhook.id());
            data.put("token", webhook.token());
            config().getData().put(name, data);
        });
        super.end();

    }

    public Webhook add(String name, String id, String token) {
        Webhook hook = new Webhook(id, token);
        WEBHOOKS.put(name, hook);
        return hook;
    }

    public Webhook get(String name) {
        return WEBHOOKS.getOrDefault(name, null);
    }

    public void send(String webhookName, Embed embed) throws QuickException {
        send(get(webhookName), embed);
    }

    public void send(Webhook hook, Embed embed) throws QuickException {
        JSONObject data = new JSONObject();
        data.put("embeds", new JSONArray().put(embed.json()));
        send(hook, data);
    }

    public void send(String webhookName, JSONObject data) throws QuickException {
        send(get(webhookName), data);
    }

    public void send(String webhookName, String message) throws QuickException {
        send(get(webhookName), message);
    }

    public void send(Webhook hook, String message) throws QuickException {
        JSONObject data = new JSONObject();
        data.put("content", message);
        send(hook, data);

    }

    public void send(Webhook hook, JSONObject data) throws QuickException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(hook.url())).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(data.toString())).build();

        final HttpClient client = HttpClient.newHttpClient();

        final HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new QuickException("Failed to send http request!", e);
        }

        final int statusCode = response.statusCode();
        if (!(statusCode >= 200 && statusCode < 300)) {
            throw new QuickException("Http status code " + statusCode + "! Response was: '" + response.body() + "'.");
        }

        // From JDK 21 the HttpClient class extends AutoCloseable, but as we want to support Minecraft versions
        //  that use JDK 17, where HttpClient doesn't extend AutoCloseable, we need to check if it's
        //  an instance of AutoCloseable before trying to close it.
        //noinspection ConstantValue
        if (client instanceof AutoCloseable) {
            try {
                ((AutoCloseable) client).close();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
