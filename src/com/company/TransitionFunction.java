package com.company;

public class TransitionFunction {
    private char input;
    private State source;
    private State target;

    public TransitionFunction(State source, char input, State target) {
        this.source = source;
        this.input = input;
        this.target = target;
    }

    public char getInput() {
        return input;
    }

    public State getTarget() {
        return target;
    }

    public State getSource() {
        return source;
    }

    public void show() {
        String output = String.format("δ(%s, %c) = %s", this.source.getName(), input, this.target.getName());
        System.out.println(output);
    }
}
