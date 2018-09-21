package me.darkrossi.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public interface ICommand {
    Boolean onCommand(Player paramPlayer, HashMap<String, String> paramArguments);

    default void missingArguments(Player paramPlayer, String paramMissingArg){
        paramPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou missed one or more Arguments"));
    }
    default void noPermission(Player paramPlayer){
        paramPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have the permission to perform this command"));
    }
}

