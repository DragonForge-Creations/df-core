package me.quickscythe.dragonforge.core.tests.utils;

import me.quickscythe.dragonforge.core.PaperIntegration;

import java.io.File;

public class TestPaperIntegration extends PaperIntegration {

    File dataFolder = new File("test");

    @Override
    public void enable() {
        super.enable();
        if(!dataFolder.exists()) dataFolder.mkdir();
    }

    @Override
    public File dataFolder() {
        return dataFolder;
    }
}
