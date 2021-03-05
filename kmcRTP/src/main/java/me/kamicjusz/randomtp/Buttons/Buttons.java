package me.kamicjusz.randomtp.Buttons;

import me.kamicjusz.randomtp.Files.BtnsFile;
import me.kamicjusz.randomtp.Main;

public class Buttons {

    private final Main plugin;
    private BtnsFile buttonsFile;

    public Buttons(Main plugin){
        this.plugin = plugin;
        buttonsFile = plugin.getButtonsFile();
    }

}
