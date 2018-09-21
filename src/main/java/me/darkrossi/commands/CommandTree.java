package me.darkrossi.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandTree<T> {

    private List<CommandTree<T>> children = new ArrayList<CommandTree<T>>();
    private CommandTree<T> parent = null;
    private T name = null;
    private ICommand command = null;
    private CommandArgument arg = null;
    private List<CommandArgument> args = null;
    private String permission = null;

    public CommandTree(T name, ICommand command) {
        this.name = name;
        this.command = command;
    }

    public CommandTree(T name, ICommand command, String permission) {
        this.name = name;
        this.command = command;
        this.permission = permission;
    }

    public CommandTree(T name, ICommand command, CommandArgument argument) {
        this.name = name;
        this.command = command;
        this.arg = argument;
    }

    public CommandTree(T name, ICommand command, CommandArgument argument, String permission) {
        this.name = name;
        this.command = command;
        this.arg = argument;
        this.permission = permission;
    }

    // Not implemented yet
    public CommandTree(T name, ICommand command, List<CommandArgument> args) {
        this.name = name;
        this.command = command;
        this.args = args;
    }

    // Not implemented yet
    public CommandTree(T name, ICommand command, List<CommandArgument> args, String permission) {
        this.name = name;
        this.command = command;
        this.args = args;
        this.permission = permission;
    }

    public List<CommandTree<T>> getChildren() {
        return children;
    }


    public void setParent(CommandTree<T> parent) {
//        parent.addChild(this);
        this.parent = parent;
    }

    public void addChild(CommandTree<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public String getName() {
        return this.name.toString();
    }

    public CommandArgument getArg() {
        if(this.args != null){
            return this.args.get(0);
        }

        return this.arg;
    }

    public CommandArgument getArg(int size) {
        if(this.args != null){
            return this.args.get(size);
        }

        return this.arg;
    }


    public ICommand getCommand() {
        return command;
    }

    public boolean hasArgs() {
        if (this.arg != null || this.args != null) {
            return true;
        }

        return false;
    }

    public void removeParent() {
        this.parent = null;
    }

    public int argumentSize(){
        if(this.args != null) {
            return this.args.size();
        } else {
            if(this.arg != null){
                return 1;
            }
        }

        return 0;
    }


    public String getPermissison(){
        return this.permission;
    }
}