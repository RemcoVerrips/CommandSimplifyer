package me.darkrossi.commands;

public class CommandArgument {

    private String argument;
    private int min;
    private int max = 2;
    private boolean required;

    public CommandArgument(String argument, int min, boolean required){
        this.argument = argument;
        this.min = min;
        this.required = required;
    }

    public CommandArgument(String argument, int min, int max, boolean required){
        this.argument = argument;
        this.min = min;
        this.max = max;
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    public String getArgument() {
        return argument;
    }

    public int argumentLength(){
        return max-min;
    }
}
