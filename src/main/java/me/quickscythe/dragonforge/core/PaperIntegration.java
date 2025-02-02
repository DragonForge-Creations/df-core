package me.quickscythe.dragonforge.core;

import me.quickscythe.dragonforge.api.DragonForgeIntegration;
import me.quickscythe.dragonforge.api.logger.DragonForgeLogger;
import me.quickscythe.dragonforge.core.utils.CoreUtils;

import java.io.File;

public class PaperIntegration extends DragonForgeIntegration {

    private final String name = "DragonForgeCore";
    private File dataFolder;


    public PaperIntegration() {

    }

    public void enable(){
        dataFolder = new File("plugins/" + name());
        if (!dataFolder.exists()) dataFolder.mkdir();
    }


    @Override
    public File dataFolder() {
        return dataFolder;
    }

    @Override
    public String name() {
        return name;
    }


}
