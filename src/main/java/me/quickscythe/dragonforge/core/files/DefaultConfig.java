package me.quickscythe.dragonforge.core.files;

import me.quickscythe.dragonforge.api.DragonForgeIntegration;
import me.quickscythe.dragonforge.api.config.Config;
import me.quickscythe.dragonforge.api.config.ConfigTemplate;
import me.quickscythe.dragonforge.api.config.ConfigValue;

import java.io.File;

@ConfigTemplate(name = "config")
public class DefaultConfig extends Config {

    @ConfigValue
    public String testValue = "me/quickscythe/quipt/test";

    public DefaultConfig(File file, String name, DragonForgeIntegration integration) {
        super(file, name, integration);
    }
}
