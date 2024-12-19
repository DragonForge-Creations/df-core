package me.quickscythe.dragonforge.utils;


import me.quickscythe.dragonforge.utils.chat.Logger;
import me.quickscythe.dragonforge.utils.chat.MessageUtils;
import me.quickscythe.dragonforge.utils.chat.placeholder.PlaceholderUtils;
import me.quickscythe.dragonforge.utils.config.ConfigFile;
import me.quickscythe.dragonforge.utils.config.ConfigFileManager;
import me.quickscythe.dragonforge.utils.storage.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoreUtils {

    private static Logger logger;
    private static ConfigFile config;
    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        CoreUtils.plugin = plugin;
        logger = new Logger(plugin);
        DataManager.init(plugin);
        config = ConfigFileManager.getFile(plugin, "config", "config.json");

        PlaceholderUtils.registerPlaceholders();
        MessageUtils.start();


    }


    public static Logger logger() {
        return logger;
    }

    public static JavaPlugin plugin() {
        return plugin;
    }

    public static ConfigFile config() {
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


    public static Color generateColor(double seed, double frequency) {
        return generateColor(seed, frequency, 100);
    }

    public static Color generateColor(double seed, double frequency, int amp) {

        if (amp > 127) amp = 127;
        int peak = 255 - amp;
        int red = (int) (Math.sin(frequency * (seed) + 0) * amp + peak);
        int green = (int) (Math.sin(frequency * (seed) + 2 * Math.PI / 3) * amp + peak);
        int blue = (int) (Math.sin(frequency * (seed) + 4 * Math.PI / 3) * amp + peak);
        if (red > 255) red = 255;
        if (green > 255) green = 255;
        if (blue > 255) blue = 255;

        return new Color(red, green, blue);
    }

    public static PageResult pagify(List<Object> things, int items) {
        return pagify(things, items, 1);
    }

    public static PageResult pagify(List<Object> things, int items, int page) {

        List<Object> rtn = new ArrayList<>();
        int totalposts = 0;
        int pagetracker = 0;

        for (Object s : things) {
            // REFERENCE for (int i = (page - 1) * pageResult; i < page * pageResult; i++)
            totalposts = totalposts + 1;
            pagetracker = pagetracker + 1;
            if (pagetracker < page * items && pagetracker > ((page - 1) * items) - 1) {
                rtn.add(s);
            }

        }
        int pages = totalposts / items;
//		if (pages > 1) {
//			if (page == 1) {
//				// can't go back
//			} else {
//				// can go back
//			}
//			// page / pages
//			if (page == pages) {
//				// can't go forward
//			} else {
//				// can go forward
//			}
//
//		}

        return new PageResult(rtn, page, pages);

    }

    public static String formatMessage(String message, String... values) {
        int i = 0;
        String string = message;
        while (string.contains("@")) {
            string = string.replaceFirst("@", values[i]);
            i = i + 1;
        }
        return string;
    }


    public static List<String> getPageResults(List<String> rules, int page, int pageResult) {
        List<String> rturn = new ArrayList<String>();
        for (int i = (page - 1) * pageResult; i < page * pageResult; i++) {
            if (i < rules.size()) rturn.add(rules.get(i));
        }
        return rturn;
    }


    public static String formatDate(long ms, String tcolor, String ncolor) {

        int l = (int) (ms / 1000);
        int sec = l % 60;
        int min = (l / 60) % 60;
        int hours = ((l / 60) / 60) % 24;
        int days = (((l / 60) / 60) / 24) % 7;
        int weeks = (((l / 60) / 60) / 24) / 7;

        if (weeks > 0) {
            return ncolor + weeks + tcolor + " weeks" + (days > 0 ? ", " + ncolor + days + tcolor + " days" : "") + (hours > 0 ? ", " + ncolor + hours + tcolor + " hours" : "") + (min > 0 ? ", " + ncolor + min + tcolor + " minutes" : "") + (sec > 0 ? ", and " + ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds") : "");
        }
        if (days > 0) {
            return ncolor + days + tcolor + " days" + (hours > 0 ? ", " + ncolor + hours + tcolor + " hours" : "") + (min > 0 ? ", " + ncolor + min + tcolor + " minutes" : "") + (sec > 0 ? ", and " + ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds") : "");
        }
        if (hours > 0) {
            return ncolor + hours + tcolor + " hours" + (min > 0 ? ", " + ncolor + min + tcolor + " minutes" : "") + (sec > 0 ? ", and " + ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds") : "");
        }
        if (min > 0) {
            return ncolor + min + tcolor + " minutes" + (sec > 0 ? ", and " + ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds") : "");
        }
        if (sec > 0) {
            return ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds");
        }

        return ncolor + "less than a second" + tcolor + "";
    }

    public static String formatDateRaw(long ms) {
        return formatDate(ms, "", "");
    }

    public static String formatDateTime(long ms, String ncolor, String tcolor) {

        int sec60 = (int) (ms % 1000) / 10;

        int l = (int) (ms / 1000);

        int sec = l % 60;
        int min = (l / 60) % 60;
        int hours = ((l / 60) / 60) % 24;
        int days = (((l / 60) / 60) / 24) % 7;
        int weeks = (((l / 60) / 60) / 24) / 7;

        DecimalFormat format = new DecimalFormat("00");

        if (weeks > 0) {
            return ncolor + format.format(weeks) + tcolor + ":" + ncolor + format.format(days) + tcolor + ":" + ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor + ":" + ncolor + format.format(sec60) + tcolor;

        }
        if (days > 0) {
            return ncolor + format.format(days) + tcolor + ":" + ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor + ":" + ncolor + format.format(sec60) + tcolor;
        }
        if (hours > 0) {
            return ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor + ":" + ncolor + format.format(sec60) + tcolor;
        }
        if (min > 0) {
            return ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor + ":" + ncolor + format.format(sec60) + tcolor;
        }
        if (sec > 0) {
            return ncolor + format.format(sec) + tcolor + ":" + ncolor + format.format(sec60) + tcolor;
        }
        if (sec60 > 0) {
            return ncolor + "00" + tcolor + ":" + ncolor + format.format(sec60) + tcolor;
        }

        return ncolor + "less than a millisecond" + tcolor + "";
    }

    public static String formatDateTimeRaw(long ms) {
        return formatDateTime(ms, "", "");
    }

    public static String getSimpleTimeFormat(long ms) {
        return formatDate(ms, "&c", "&4");
    }


    public static boolean consumeItem(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found) return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed) player.getInventory().setItem(index, null);
            else stack.setAmount(stack.getAmount() - removed);

            if (count <= 0) break;
        }

        player.updateInventory();
        return true;
    }
}
