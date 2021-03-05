package me.kamicjusz.randomtp.Buttons;

import org.bukkit.Location;

public class Button {

    Location location;

    private ButtonManager buttonManager;

    public Button(Location loc){
        this.location = loc;
    }



    public Location getLocation() {
        return location;
    }



}
