package me.kamicjusz.randomtp.Files;

import me.kamicjusz.randomtp.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtnsFile {

    private final Main plugin;

    public FileConfiguration btnsCfg;
    public File btnsFile;

    public BtnsFile(Main plugin){
        this.plugin = plugin;
        setup();
    }


    public void setup(){
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdirs();
        }

        btnsFile = new File(plugin.getDataFolder(), "buttons.yml");

        if(!btnsFile.exists()){
            try {
                btnsFile.createNewFile();
                plugin.saveResource("buttons.yml", true);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        btnsCfg = YamlConfiguration.loadConfiguration(btnsFile);
    }

    public FileConfiguration getBtnsCfg() {
        return btnsCfg;
    }

    public void saveBtnsFile(){
        try {
            btnsCfg.save(btnsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadBtnsFile(){
        btnsCfg = YamlConfiguration.loadConfiguration(btnsFile);
    }

}
