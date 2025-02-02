/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package me.quickscythe.dragonforge.core.files;


import me.quickscythe.dragonforge.api.DragonForgeIntegration;
import me.quickscythe.dragonforge.api.config.Config;
import me.quickscythe.dragonforge.api.config.ConfigTemplate;
import me.quickscythe.dragonforge.api.config.ConfigValue;

import java.io.File;

@ConfigTemplate(name = "hashes")
public class HashesConfig extends Config {

    @ConfigValue
    public String encrypted_zip_hash = "";

    @ConfigValue
    public String commit_hash = "";

    public HashesConfig(File file, String name, DragonForgeIntegration plugin) {
        super(file, name, plugin);
    }
}
