package me.darkrossi.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CommandHandler implements CommandExecutor {

    private CommandTree cmd;
    private Player player;
    private HashMap<String, String> arguments;

    /**
     *
     * @param cmd
     */
    public CommandHandler(CommandTree cmd) {
        this.cmd = cmd;
        Bukkit.getPluginCommand(this.cmd.getName()).setExecutor(this);
    }

    /**
     *
     * @param player
     */
    private void setPlayer(Player player) {
        this.player = player;
    }

    /**
     *
     * @return
     */
    private Player getPlayer() {
        return this.player;
    }

    /**
     *
     * @param sender
     * @param command
     * @param label
     * @param args
     * @return
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.arguments = new HashMap<>();

        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot execute this command as console.");
        } else {
            this.setPlayer((Player) sender);
            checkArguments(args, this.cmd);
        }

        return true;
    }

    /**
     *
     * @param args
     * @param cmd
     */
    private void checkArguments(String[] args, CommandTree cmd) {
        if (cmd.hasArgs() && cmd.getArg().isRequired()) {
            argumentFilter(args, cmd);
        } else {
            if (args.length > 0) {
                this.findChild(cmd, args);
            } else {
                this.executeCommand(cmd);
            }
        }
    }

    /**
     *
     * @param cmd
     */
    private void executeCommand(CommandTree cmd){
        cmd.getCommand().onCommand(this.getPlayer(), this.arguments);
    }

    /**
     *
     * @param cmd
     */
    private void executeMissingArguments(CommandTree cmd){
        cmd.getCommand().missingArguments(this.getPlayer(), "example");
    }

    /**
     *
     * @param args
     * @param cmd
     */
    private void argumentFilter(String[] args, CommandTree cmd){
        if (args.length > 0) {
            if (args.length < 2) {
                this.executeCommand(cmd);
            } else {
                this.findChild(cmd, args);
            }
        } else {
            if (cmd.getArg().isRequired()) {
                this.executeMissingArguments(cmd);
            } else {
                this.executeCommand(cmd);
            }
        }
    }

    /**
     *
     * @param cmd
     * @param args
     */
    private void findChild(CommandTree cmd, String[] args) {
        List children = cmd.getChildren();
        for(int i = 0; i < children.size(); i++){
            CommandTree child = (CommandTree) children.get(i);
            if (cmd.hasArgs()) {
                if (child.getName().equalsIgnoreCase(args[1])) {
                    this.arguments.put(cmd.getArg().getArgument(), args[0]);
                    this.checkArguments(this.subList(args, 2), child);
                    break;
                }
            } else {
                this.checkArguments(this.subList(args, 1), child);
                break;
            }
        }
    }

    /**
     *
     * @param args
     * @param shrinkSize
     * @return
     */
    private String[] subList(String[] args, int shrinkSize) {
        int listLength = args.length - shrinkSize;
        String[] arguments;

        if (listLength > 0) {
            arguments = new String[listLength];

            for (int i = shrinkSize; i < args.length; i++) {
                arguments[i - shrinkSize] = args[i];
            }
        } else {
            arguments = new String[0];
        }

        return arguments;
    }
}