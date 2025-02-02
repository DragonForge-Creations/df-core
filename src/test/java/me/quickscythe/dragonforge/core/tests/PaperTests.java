package me.quickscythe.dragonforge.core.tests;

import me.quickscythe.dragonforge.core.Initializer;
import me.quickscythe.dragonforge.core.PaperIntegration;
import me.quickscythe.dragonforge.core.tests.utils.ObjectFactory;
import me.quickscythe.dragonforge.core.utils.CoreUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.eclipse.jgit.transport.PackParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PaperTests {

    private PaperIntegration paperIntegration;
    private Initializer initializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        try (MockedStatic<Bukkit> mockedBukkit = Mockito.mockStatic(Bukkit.class)) {
            String expectedVersionMessage = "Mocked Version Message";
            mockedBukkit.when(Bukkit::getVersionMessage).thenReturn(expectedVersionMessage);

//            Server server = mock(Server.class);
//            PluginManager pluginManager = mock(PluginManager.class);
//            mockedBukkit.when(Bukkit::getServer).thenReturn(server);
//            when(server.getUnsafe()).thenReturn(ObjectFactory.getUnsafeValues());
//            mockedBukkit.when(Bukkit::getUnsafe).thenReturn(ObjectFactory.getUnsafeValues());
//            when(server.getPluginManager()).thenReturn(pluginManager);

            initializer = mock(Initializer.class);
//            when(server.getPluginManager().getPlugin("DragonForgeCore")).thenReturn(initializer);
            CoreUtils.init(initializer, new PaperIntegration());
            paperIntegration = CoreUtils.integration();
        }
    }

    @Test
    public void testEnable() {

//         Call the enable method
        paperIntegration.enable();

        // Add assertions to verify the expected behavior
        File dataFolder = paperIntegration.dataFolder();
        assertNotNull(dataFolder, "Data folder should not be null");
        assertTrue(dataFolder.exists(), "Data folder should exist");
    }

    @Test
    public void testName() {
        // Verify the name of the integration
        assertEquals("DragonForgeCore", paperIntegration.name(), "Integration name should be 'DragonForgeCore'");
    }

    @AfterEach
    void tearDown() throws IOException {
        paperIntegration.destroy();
    }
}