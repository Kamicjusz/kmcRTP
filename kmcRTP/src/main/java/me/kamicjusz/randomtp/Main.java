package me.kamicjusz.randomtp;

import me.kamicjusz.randomtp.Buttons.*;
import me.kamicjusz.randomtp.Files.BtnsFile;
import me.kamicjusz.randomtp.Utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private BtnsFile buttonsFile;
    private Buttons buttonsClass;
    private ButtonsCmd buttonsCmdClass;
    private ButtonsListener buttonsListener;
    private ButtonManager buttonManager;
    FileConfiguration btnsCfg;
    String hasntPerms;

    @Override
    public void onEnable() {
        config();
        this.buttonsFile = new BtnsFile(this);
        this.buttonManager = new ButtonManager(this);
        this.buttonsClass = new Buttons(this);
        this.buttonsListener = new ButtonsListener(this);
        this.buttonsCmdClass = new ButtonsCmd(this);
        btnsCfg = buttonsFile.getBtnsCfg();
        hasntPerms = ChatUtils.colored(getConfig().getString("hasntPerms"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&f[kmcRTP]"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&f[kmcRTP] Plugin was &aenabled"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&f[kmcRTP] Made by &f&lKamicjusz"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&f[kmcRTP]"));
    }

    @Override
    public void onDisable() {
        int button_count = btnsCfg.getInt("buttons_counter");
        if(buttonManager.buttons.size() != button_count){
            btnsCfg.set("buttons_counter", buttonManager.buttonsCounter);
            btnsCfg.set("buttons", "");
            for(int i = 0; i < buttonManager.buttons.size(); i++){
                Button button = buttonManager.buttons.get(i);
                Location buttonLoc = button.getLocation();
                btnsCfg.set("buttons." + "button_" + i + ".X",  buttonLoc.getBlockX());
                btnsCfg.set("buttons." + "button_" + i + ".Y",  buttonLoc.getBlockY());
                btnsCfg.set("buttons." + "button_" + i + ".Z",  buttonLoc.getBlockZ());
                buttonsFile.saveBtnsFile();
            }
        }
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&f[kmcRTP]"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&f[kmcRTP] Plugin was &cdisabled"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&f[kmcRTP] Made by &f&lKamicjusz"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&f[kmcRTP]"));
    }

    void config(){
        saveDefaultConfig();
    }

    public String getHasntPerms() {
        return hasntPerms;
    }

    public BtnsFile getButtonsFile() {
        return buttonsFile;
    }

    public ButtonsListener getButtonsListener() {
        return buttonsListener;
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }
}
