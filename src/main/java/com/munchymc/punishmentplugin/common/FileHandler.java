package com.munchymc.punishmentplugin.common;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    private final File file;
    private ConfigurationSection cachedConfig; //Used later for YamlConfigurations.
    private final Map<String, String> messages = new HashMap<>();

    public FileHandler(File dataFolder) throws IOException {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        this.file = new File(dataFolder, "Config.cfg");
        this.cachedConfig = loadConfig();

        if (file.exists()) {
            loadConfig();
            return;
        }
        file.createNewFile();

        YamlConfiguration conf =  new YamlConfiguration();
        conf.createSection("Database");
        conf.getConfigurationSection("Database").set("User", "Root");
        conf.getConfigurationSection("Database").set("Password", "Banana");
        conf.getConfigurationSection("Database").set("Database", "Main");
        conf.getConfigurationSection("Database").set("IP", "localhost");
        conf.getConfigurationSection("Database").set("Port", 3306);

        conf.save(file);

        System.out.println("Config Generated!");
        Thread.currentThread().interrupt();
    }

    //Just for later functionality.
    public void reloadConf() {
        this.cachedConfig = loadConfig();
    }

    public String getUser(){
        return cachedConfig.getString("Database.User");
    }

    public String getPassword(){
        return cachedConfig.getString("Database.Password");
    }

    public String getDatabase(){
        return cachedConfig.getString("Database.Database");
    }

    public String getIP(){
        return cachedConfig.getString("Database.IP");
    }

    public int getPort(){
        return cachedConfig.getInt("Database.Port");
    }

    public YamlConfiguration loadConfig(){
        return YamlConfiguration.loadConfiguration(file);
    }
}