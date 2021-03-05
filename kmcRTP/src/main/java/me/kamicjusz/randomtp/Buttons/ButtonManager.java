package me.kamicjusz.randomtp.Buttons;

import me.kamicjusz.randomtp.Files.BtnsFile;
import me.kamicjusz.randomtp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ButtonManager {

    private final Main plugin;
    private BtnsFile btnsFile;
    FileConfiguration btnsCfg;

    public List<Button> buttons;
    public int buttonsCounter;

    public ButtonManager(Main plugin){
        this.plugin = plugin;
        buttons = new ArrayList<>();
        btnsFile = plugin.getButtonsFile();
        btnsCfg = btnsFile.getBtnsCfg();

        // load list of button from file
        buttonsCounter = btnsCfg.getInt("buttons_counter");
        for(int i = 0; i < buttonsCounter; i++){
            int x = btnsCfg.getInt("buttons." + "button_" + i + ".X");
            int y = btnsCfg.getInt("buttons." + "button_" + i + ".Y");
            int z = btnsCfg.getInt("buttons." + "button_" + i + ".Z");
            Location location = new Location(Bukkit.getWorld("world"), x, y, z);
            addButton(new Button(location));
        }
        //

        // save list of buttons to file every 30 minutes
        new BukkitRunnable() {
            @Override
            public void run() {
                int button_count = btnsCfg.getInt("buttons_counter");
                if(buttons.size() != button_count){
                    btnsCfg.set("buttons_counter", buttonsCounter);
                    btnsCfg.set("buttons", "");
                    for(int i = 0; i < buttons.size(); i++){
                        Button button = buttons.get(i);
                        Location buttonLoc = button.getLocation();
                        btnsCfg.set("buttons." + "button_" + i + ".X",  buttonLoc.getBlockX());
                        btnsCfg.set("buttons." + "button_" + i + ".Y",  buttonLoc.getBlockY());
                        btnsCfg.set("buttons." + "button_" + i + ".Z",  buttonLoc.getBlockZ());
                        btnsFile.saveBtnsFile();
                    }
                }

            }
        }.runTaskTimer(plugin, 0, 36000);
        //
    }

    public void addButton(Button button){
        buttons.add(button);
    }

    public void deleteButton(Button button){
        buttons.remove(button);
    }


    public List<Button> getButtonList() {
        return buttons;
    }


    public Button getButton(Location location){
        for(int i = 0; i < buttons.size(); i++){
            Button button = buttons.get(i); // empty list?
            Location buttonLoc = button.getLocation();
            if(buttonLoc.getBlockX() == location.getBlockX()){
                if(buttonLoc.getBlockY() == location.getBlockY()){
                    if(buttonLoc.getBlockZ() == location.getBlockZ()){
                        return button;
                    }
                }
            }
        }
        return null;
    }

    public boolean isTeleport(Location location){
        if(getButton(location) != null){
            return true;
        }else {
            return false;
        }
    }

    public void changeButtonsCounter(int i){
        buttonsCounter = i;
    }

    public int getButtonsCounter() {
        return buttonsCounter;
    }
}
