package me.quickscythe.dragonforge.core.utils.items;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.json.JSONObject;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ItemUtils {

    public static ItemStack deserializeVanillaString(String string) {
        // String will look something like: minecraft:stone[display:{Name:"{\"text\":\"Stone\",\"color\":\"red\"}"}]
        String extractedMaterial = string.contains("\\[") ? string.split("\\[")[0] : string;
        String extractedData = string.replaceFirst(extractedMaterial, "");
//        JSONObject components = extractedData.isBlank() ? new JSONObject() : serializeComponents(extractedData);
        NamespacedKey itemKey = NamespacedKey.fromString(extractedMaterial);
        ItemType itemType = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM).get(itemKey);
        ItemStack itemStack = itemType.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        return itemStack;
    }

    public static JSONObject serializeComponents(String componentString) {
        componentString = componentString.substring(1, componentString.length() - 1);

        String[] pairs = componentString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

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

    public static JSONObject serializeComponents(ItemStack itemStack) {
        String input = itemStack.getItemMeta().getAsComponentString();
        return serializeComponents(input);
    }

    public static boolean inventoryHasRoom(@NotNull PlayerInventory inventory, ItemStack reward) {
        boolean placed = false;
        for (ItemStack stack : inventory.getStorageContents()) {
            if (stack == null || stack.getType().isAir()) {
                placed = true;
                break;
            }
            if (stack.getType().equals(reward.getType()) && stack.getAmount() + reward.getAmount() <= stack.getMaxStackSize()) {
                placed = true;
                break;
            }
        }
        return placed;
    }
}
