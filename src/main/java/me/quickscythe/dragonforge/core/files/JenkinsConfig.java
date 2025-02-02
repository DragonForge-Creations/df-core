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

@ConfigTemplate(name = "jenkins")
public class JenkinsConfig extends Config {

    @ConfigValue
    public String url = "https://ci.quickscythe.me";

    @ConfigValue
    public String username = "admin";

    @ConfigValue
    public String password = "password";

    @ConfigValue
    public String api_endpoint = "/api/json";

    public JenkinsConfig(File file, String name, DragonForgeIntegration plugin) {
        super(file, name, plugin);
    }
}
