package me.darkrossi.commands;

import org.bukkit.entity.Player;

import java.util.HashMap;

public interface ICommand {
    Boolean onCommand(Player paramPlayer, HashMap<String, String> paramArguments);
    Boolean missingArguments(Player player, String args);
}

