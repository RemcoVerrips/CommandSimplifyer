package me.darkrossi.commands;

public class CommandArgument {

    private String argument;
    private boolean sentence;
    private boolean required;

    public CommandArgument(String argument, boolean sentence, boolean required){
        this.argument = argument;
        this.sentence = sentence;
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isSentence() {
        return sentence;
    }

    public String getArgument() {
        return argument;
    }

}
