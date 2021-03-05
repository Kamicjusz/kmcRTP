package me.kamicjusz.randomtp.Buttons;

import me.kamicjusz.randomtp.Files.BtnsFile;
import me.kamicjusz.randomtp.Main;
import me.kamicjusz.randomtp.Utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ButtonsListener implements Listener {

    private final Main plugin;
    private BtnsFile btnsFile;
    private ButtonManager buttonManager;
    FileConfiguration btnsCfg;
    int range;
    String teleportedMess;


    // list of players who use /rtp create
    List<Player> creators;

    // list of players who use /rtp delete
    List<Player> destroyers;

    // list of buttons
    List<Button> buttons;

    //list of teleported players after using random teleport
    List<Player> teleported;

    //list of blacklisted block where player can't teleport
    List<Material> blacklisted;

    public ButtonsListener(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        creators = new ArrayList<>();
        destroyers = new ArrayList<>();
        buttons = new ArrayList<>();
        teleported = new ArrayList<>();
        blacklisted = new ArrayList<>();
        btnsFile = plugin.getButtonsFile();
        buttonManager = plugin.getButtonManager();
        btnsCfg = btnsFile.getBtnsCfg();
        range = plugin.getConfig().getInt("range");
        blacklisted.add(Material.LAVA);
        blacklisted.add(Material.WATER);
        blacklisted.add(Material.FIRE);
        teleportedMess = plugin.getConfig().getString("teleported_mess");
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event){
        Block clickedBlock = event.getClickedBlock();
        Player player = event.getPlayer();
        if(clickedBlock != null && clickedBlock.getType().toString().contains("BUTTON")){
            Location btnLoc = event.getClickedBlock().getLocation();
            if(creators.contains(player)){
                if(buttonManager.isTeleport(btnLoc)){
                    ChatUtils.sendMessage(player, "&cThat button is already teleport");
                    creators.remove(player);
                }else {
                    buttonManager.addButton(new Button(btnLoc));
                    creators.remove(player);
                    int buttonsCounter = buttonManager.getButtonsCounter();
                    buttonsCounter++;
                    buttonManager.changeButtonsCounter(buttonsCounter);
                    ChatUtils.sendMessage(player, "&aNew teleport created!");
                }
            }else if(destroyers.contains(player)){
                if(buttonManager.isTeleport(btnLoc)){
                    Button button = buttonManager.getButton(btnLoc);
                    buttonManager.deleteButton(button);
                    destroyers.remove(player);
                    int buttonsCounter = buttonManager.getButtonsCounter();
                    buttonsCounter--;
                    buttonManager.changeButtonsCounter(buttonsCounter);
                    ChatUtils.sendMessage(player, "&cTeleport destroyed!");
                }else {
                    ChatUtils.sendMessage(player, "&cThat button isn't teleport");
                    destroyers.remove(player);
                }
            }else {
                if(buttonManager.isTeleport(clickedBlock.getLocation())){
                    randomTeleport(player);
                    teleported.add(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            teleported.remove(player);
                        }
                    }.runTaskLater(plugin, 60);
                }
            }

        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof Player){
            Player player = (Player) entity;
            if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
                if(teleported.contains(player)){
                    event.setCancelled(true);
                }
            }
        }
    }

    public void randomTeleport(Player player){
        int randomX = new Random().nextInt(range);
        int randomZ = new Random().nextInt(range);
        Block highest = player.getWorld().getHighestBlockAt(randomX, randomZ);
        String h2 = highest.getLocation().add(0,2,0).getBlock().getType().toString();
        String h3 = highest.getLocation().add(0,3,0).getBlock().getType().toString();
        String h4 = highest.getLocation().add(0,4,0).getBlock().getType().toString();
        String h5 = highest.getLocation().add(0,5,0).getBlock().getType().toString();
        String h6 = highest.getLocation().add(0,6,0).getBlock().getType().toString();
        String h7 = highest.getLocation().add(0,7,0).getBlock().getType().toString();
        String h8 = highest.getLocation().add(0,8,0).getBlock().getType().toString();
        String h9 = highest.getLocation().add(0,9,0).getBlock().getType().toString();
        String h10 = highest.getLocation().add(0,10,0).getBlock().getType().toString();
        String h11 = highest.getLocation().add(0,11,0).getBlock().getType().toString();
        if(!blacklisted.contains(highest.getType()) && h2.contains("AIR") && h3.contains("AIR") && h4.contains("AIR") && h5.contains("AIR") && h6.contains("AIR")){
            player.teleport(highest.getLocation().add(0,10,0));
            ChatUtils.sendMessage(player, teleportedMess.replace("$X$", String.valueOf(highest.getLocation().getBlockX())).replace("$Y$", String.valueOf(highest.getLocation().getBlockY())).replace("$Z$", String.valueOf(highest.getLocation().getBlockZ())));
        }else {
            randomTeleport(player);
        }
    }


    // void add player to "creators" list from other classes
    public void addCreator(Player player){
        creators.add(player);
    }

    // void add player to "destroyers" list from other classes
    public void addDestroyer(Player player){
        destroyers.add(player);
    }


}
