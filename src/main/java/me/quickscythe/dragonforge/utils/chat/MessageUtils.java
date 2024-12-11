package me.quickscythe.dragonforge.utils.chat;


import json2.JSONObject;
import me.quickscythe.dragonforge.utils.CoreUtils;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

public class MessageUtils {

    private static final File file = new File(CoreUtils.plugin().getDataFolder() + "/messages.json");
    private static JSONObject messages = new JSONObject();

    public static void start() {
        createDefaultMessages();
        StringBuilder data = new StringBuilder();
        try {
            if (!file.exists())
                file.createNewFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        JSONObject loaded = data.toString().isEmpty() ? new JSONObject() : new JSONObject(data.toString());
        //messages = what is in memory
        //need to check what is in memory and hasn't been loaded then add it to file
        boolean discrepency = false;
        for(Map.Entry<String, Object> entry : messages.toMap().entrySet()){
            String key = entry.getKey();
            String text = (String) entry.getValue();
            if(!loaded.has(key)){
                discrepency = true;
                loaded.put(key,text);
            }
        }
        messages = loaded;
        if(discrepency) loadChangesToFile();
    }

    public static void loadChangesToFile() {
        try {
            FileWriter f2 = new FileWriter(file, false);
            f2.write(messages.toString(2));
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMessage(String key, String value){
        if(!messages.has(key)) messages.put(key, value);
    }

    private static void createDefaultMessages() {
        addMessage("cmd.error.no_player", "&cSorry \"[0]\" couldn't be find. If the player is offline their username must be typed exactly.");
        addMessage("cmd.error.no_perm", "&cSorry, you don't have the permission to run that command.");
        addMessage("gui.error.not_exist", "&cThere was an error opening that GUI. Does it exist?");
        addMessage("cmd.error.no_command", "&cSorry, couldn't find the command \"[0]\". Please check your spelling and try again.");
    }

    public static void log(String message) {
        CoreUtils.plugin().getLogger().log(Level.ALL, colorize(message));
    }


    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }




    public static String fade(String fromHex, String toHex, String string) {
        int[] start = getRGB(fromHex);
        int[] last = getRGB(toHex);

        StringBuilder sb = new StringBuilder();

        Integer dR = numberFade(start[0], last[0], string.length());
        Integer dG = numberFade(start[1], last[1], string.length());
        Integer dB = numberFade(start[2], last[2], string.length());

        for (int i = 0; i < string.length(); i++) {
            Color c = new Color(start[0] + dR * i, start[1] + dG * i, start[2] + dB * i);

            sb.append(net.md_5.bungee.api.ChatColor.of(c) + "" + string.charAt(i));
        }
        return sb.toString();
    }

    private static int[] getRGB(String rgb) {
        int[] ret = new int[3];
        for (int i = 0; i < 3; i++) {
            ret[i] = hexToInt(rgb.charAt(i * 2), rgb.charAt(i * 2 + 1));
        }
        return ret;
    }

    private static int hexToInt(char a, char b) {
        int x = a < 65 ? a - 48 : a - 55;
        int y = b < 65 ? b - 48 : b - 55;
        return x * 16 + y;
    }

    private static Integer numberFade(int i, int f, int n) {
        int d = (f - i) / (n - 1);
        return d;
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

    public static String formatTime(long ms, String ncolor, String tcolor) {
        int l = (int) (ms / 1000);

        int sec = l % 60;
        int min = (l / 60) % 60;
        int hours = ((l / 60) / 60) % 24;
        int days = (((l / 60) / 60) / 24) % 7;
        int weeks = (((l / 60) / 60) / 24) / 7;

        DecimalFormat format = new DecimalFormat("00");

        if (weeks > 0) {
            return ncolor + format.format(weeks) + tcolor + ":" + ncolor + format.format(days) + tcolor + ":" + ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor;

        }
        if (days > 0) {
            return ncolor + format.format(days) + tcolor + ":" + ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor;
        }
        if (hours > 0) {
            return ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor;
        }
        if (min > 0) {
            return ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor;
        }
        if (sec > 0) {
            return ncolor + "00" + tcolor + ":" + ncolor + format.format(sec) + tcolor;
        }

        return ncolor + "00" + tcolor + ":" + ncolor + "00" + tcolor;
    }

    public static String formatTimeRaw(long ms) {
        return formatTime(ms, "", "");
    }




    public static String getMessage(String key, Object... replacements){
        String a = getMessage(key);
        for(int i=0;i!=replacements.length;i++)
            a = a.replaceFirst("\\[" + i + "]", replacements[i].toString());
        return a;
    }

    private static String getMessage(String key) {
        return messages.has(key) ? colorize(messages.getString(key)) : key;
    }
}
