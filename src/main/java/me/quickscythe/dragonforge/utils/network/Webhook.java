package me.quickscythe.dragonforge.utils.network;

public record Webhook(String id, String token) {

    public String url() {
        return "https://discord.com/api/webhooks/" + id() + "/" + token();
    }
}
