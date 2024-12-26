package me.quickscythe.dragonforge.utils.advancements;


import io.papermc.paper.advancement.AdvancementDisplay;
import json2.JSONObject;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.chat.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static net.kyori.adventure.text.Component.text;

public class EphemeralAdvancement {

    private final NamespacedKey key;

    public EphemeralAdvancement(JavaPlugin plugin, JSONObject data) {
        this.key = new NamespacedKey(plugin, "ephemeral_" + System.currentTimeMillis() + "_" + data.toString().hashCode());
        Bukkit.getUnsafe().loadAdvancement(key, data.toString());
    }

    public void send(Player player) {
        AdvancementProgress progress = player.getAdvancementProgress(Objects.requireNonNull(player.getServer().getAdvancement(key)));
        progress.getRemainingCriteria().forEach(progress::awardCriteria);
        Bukkit.getScheduler().runTaskLaterAsynchronously(CoreUtils.plugin(), () -> {
            progress.getAwardedCriteria().forEach(progress::revokeCriteria);
        }, 20 * 5);

    }

    public NamespacedKey key() {
        return key;
    }

    public void remove() {
        Bukkit.getUnsafe().removeAdvancement(key);
    }


    public static class Builder {

        private final JavaPlugin plugin;
        private final JSONObject data = new JSONObject();

        public Builder(JavaPlugin plugin) {
            this.plugin = plugin;
            JSONObject display = new JSONObject();
            display.put("title", new JSONObject(MessageUtils.serialize(text("title"))));
            display.put("description", "description");
            JSONObject displayIcon = new JSONObject();
            displayIcon.put("id", "minecraft:stone");
            display.put("icon", displayIcon);
            display.put("frame", "task");
            display.put("show_toast", true);
            display.put("announce_to_chat", false);
            display.put("hidden", false);

            JSONObject criteria = new JSONObject();
            criteria.put("criteria1", new JSONObject().put("trigger", "minecraft:impossible"));

            data.put("display", display);
            data.put("criteria", criteria);
        }

        public Builder title(String title) {
            data.getJSONObject("display").put("title", new JSONObject(MessageUtils.serialize(text(title))));
            return this;
        }

        public Builder title(Component title) {
            data.getJSONObject("display").put("title", new JSONObject(MessageUtils.serialize(title)));
            return this;
        }

        public Builder description(String description) {
            data.getJSONObject("display").put("description", new JSONObject(MessageUtils.serialize(text(description))));
            return this;
        }

        public Builder description(Component description) {
            data.getJSONObject("display").put("description", new JSONObject(MessageUtils.serialize(description)));
            return this;
        }

        public Builder icon(ItemStack itemStack) {
            JSONObject icon = new JSONObject();
            icon.put("id", itemStack.getType().getKey().toString());
            icon.put("count", itemStack.getAmount());
            //todo add components
            JSONObject components = new JSONObject();
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) components = new JSONObject();
            else {
                PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
                for (NamespacedKey key : dataContainer.getKeys()) {
                    String namespaceKey = key.getNamespace() + ":" + key.getKey();
                    components.put(namespaceKey, dataContainer.get(key, getDataType(dataContainer, key)));
                }
            }
            icon.put("components", components);
            data.getJSONObject("display").put("icon", icon);
            return this;
        }

        private PersistentDataType<?,?> getDataType(PersistentDataContainer container, NamespacedKey key) {
            if(container.has(key, PersistentDataType.STRING)) return PersistentDataType.STRING;
            if(container.has(key, PersistentDataType.BYTE)) return PersistentDataType.BYTE;
            if(container.has(key, PersistentDataType.SHORT)) return PersistentDataType.SHORT;
            if(container.has(key, PersistentDataType.INTEGER)) return PersistentDataType.INTEGER;
            if(container.has(key, PersistentDataType.LONG)) return PersistentDataType.LONG;
            if(container.has(key, PersistentDataType.FLOAT)) return PersistentDataType.FLOAT;
            if(container.has(key, PersistentDataType.DOUBLE)) return PersistentDataType.DOUBLE;
            if(container.has(key, PersistentDataType.BYTE_ARRAY)) return PersistentDataType.BYTE_ARRAY;
            if(container.has(key, PersistentDataType.INTEGER_ARRAY)) return PersistentDataType.INTEGER_ARRAY;
            if(container.has(key, PersistentDataType.LONG_ARRAY)) return PersistentDataType.LONG_ARRAY;
            if(container.has(key, PersistentDataType.TAG_CONTAINER)) return PersistentDataType.TAG_CONTAINER;
            if(container.has(key, PersistentDataType.BOOLEAN)) return PersistentDataType.BOOLEAN;
            if(container.has(key, PersistentDataType.INTEGER_ARRAY)) return PersistentDataType.INTEGER_ARRAY;
            if(container.has(key, PersistentDataType.LONG_ARRAY)) return PersistentDataType.LONG_ARRAY;
            return PersistentDataType.PrimitivePersistentDataType.STRING;
        }

        public Builder icon(Material item) {
            data.getJSONObject("display").getJSONObject("icon").put("id", item.getKey());
            return this;
        }


        public Builder frame(AdvancementDisplay.Frame frame) {
            data.getJSONObject("display").put("frame", frame.name().toLowerCase());
            return this;
        }

        public Builder toast(boolean showToast) {
            data.getJSONObject("display").put("show_toast", showToast);
            return this;
        }

        public Builder chat(boolean announceToChat) {
            data.getJSONObject("display").put("announce_to_chat", announceToChat);
            return this;
        }

        public Builder hidden(boolean hidden) {
            data.getJSONObject("display").put("hidden", hidden);
            return this;
        }

        public Builder criteria(String criteria, String trigger) {
            data.getJSONObject("criteria").put(criteria, new JSONObject().put("trigger", trigger));
            return this;
        }

        public EphemeralAdvancement build() {
            return new EphemeralAdvancement(plugin, data);
        }

    }
}

