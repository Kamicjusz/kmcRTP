package me.kamicjusz.randomtp.Buttons;

import me.kamicjusz.randomtp.Main;
import me.kamicjusz.randomtp.Utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ButtonsCmd implements CommandExecutor, TabCompleter {

    private final Main plugin;
    private ButtonsListener buttonsListener;
    String hasntPerms;

    public ButtonsCmd(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("rtp").setExecutor(this);
        plugin.getCommand("rtp").setTabCompleter(this);
        hasntPerms = plugin.getHasntPerms();
        buttonsListener = plugin.getButtonsListener();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("kmc.randomtp.admin")){
                if(args.length > 0){
                    if(args[0].equalsIgnoreCase("create")){
                        if(buttonsListener.destroyers.contains(player)){
                            buttonsListener.destroyers.remove(player);
                        }
                        buttonsListener.addCreator(player);
                        ChatUtils.sendMessage(player, "&bTo create random teleport &buse &7stone button&b!");
                    }else if(args[0].equalsIgnoreCase("delete")){
                        if(buttonsListener.creators.contains(player)){
                            buttonsListener.creators.remove(player);
                        }
                        buttonsListener.addDestroyer(player);
                        ChatUtils.sendMessage(player, "&bTo delete random teleport use button which is teleport");
                    }else {
                        ChatUtils.sendMessage(player, "&cWrong usage! Try: /rtp create/delete");
                    }
                }else {
                    ChatUtils.sendMessage(player, "&cWrong usage! Try: /rtp create/delete");
                }
            }else {
                ChatUtils.sendMessage(player, hasntPerms);
            }
        }else {
            ChatUtils.sendMessage(sender, "&cThat command can be use only by player!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> completers = new ArrayList<>();
        completers.add("create");
        completers.add("delete");

        return completers;
    }


}
