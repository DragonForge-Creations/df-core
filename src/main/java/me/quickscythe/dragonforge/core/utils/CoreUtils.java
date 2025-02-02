package me.quickscythe.dragonforge.core.utils;


import me.quickscythe.dragonforge.core.PaperIntegration;
import me.quickscythe.dragonforge.api.config.ConfigManager;
import me.quickscythe.dragonforge.core.files.DefaultConfig;
import me.quickscythe.dragonforge.core.files.ResourceConfig;
import me.quickscythe.dragonforge.core.utils.resources.ResourcePackServer;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import me.quickscythe.dragonforge.core.utils.chat.MessageUtils;
import me.quickscythe.dragonforge.core.utils.chat.placeholder.PlaceholderUtils;
import me.quickscythe.dragonforge.core.utils.sessions.SessionManager;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreUtils {

    private static DefaultConfig config;
    private static JavaPlugin plugin;

    private static PaperIntegration integration;

    private static ResourcePackServer packserver;


    public static void init(JavaPlugin plugin) {
        init(plugin, new PaperIntegration());

    }

    public static void init(JavaPlugin plugin, @Nullable PaperIntegration integration) {
        CoreUtils.plugin = plugin;
        CoreUtils.integration = integration == null ? new PaperIntegration() : integration;
        integration.enable();
        config = ConfigManager.registerConfig(integration(), DefaultConfig.class);
        ResourceConfig resourceConfig = ConfigManager.registerConfig(integration, ResourceConfig.class);

        SessionManager.init(integration);
        PlaceholderUtils.registerPlaceholders();
        MessageUtils.start();
        packserver = new ResourcePackServer();
        if (!resourceConfig.repo_url.isEmpty()) {
            packserver.setUrl(resourceConfig.repo_url);
        }

    }

    public static ResourcePackServer packServer() {
        return packserver;
    }


    public static JavaPlugin plugin() {
        return plugin;
    }

    public static DefaultConfig config() {
        return config;
    }



    public static void playTotemAnimation(Player player, int customModelData) {
        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta meta = totem.getItemMeta();
        if (meta == null) return;
        meta.setCustomModelData(customModelData);
        totem.setItemMeta(meta);
        ItemStack hand = player.getInventory().getItemInMainHand();
        player.getInventory().setItemInMainHand(totem);
        player.playEffect(EntityEffect.TOTEM_RESURRECT);
        player.getInventory().setItemInMainHand(hand);
    }


    public static String encryptLocation(Location loc) {
        String r = loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getPitch() + ":" + loc.getYaw();
        r = r.replaceAll("\\.", ",");
        r = "location:" + r;
        return r;
    }

    public static Location decryptLocation(String s) {
        if (s.startsWith("location:")) s = s.replaceAll("location:", "");

        if (s.contains(",")) s = s.replaceAll(",", ".");
        String[] args = s.split(":");
        Location r = new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        if (args.length >= 5) {
            r.setPitch(Float.parseFloat(args[4]));
            r.setYaw(Float.parseFloat(args[5]));
        }
        return r;
    }


    public static JSONObject serializeComponents(ItemStack itemStack) {
        String input = itemStack.getItemMeta().getAsComponentString();
        input = input.substring(1, input.length() - 1);
        String[] pairs = input.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        JSONObject jsonObject = new JSONObject();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            if (value.startsWith("{") && value.endsWith("}")) {
                jsonObject.put(key, new JSONObject(value));
            } else {
                jsonObject.put(key, Integer.parseInt(value));
            }
        }
        return jsonObject;
    }

    public static PaperIntegration integration() {
        return integration;
    }
}
