package com.company;

public class TransitionFunction {
    public char input;
    public State source;
    public State target;

    public TransitionFunction(State source, char input, State target) {
        this.source = source;
        this.input = input;
        this.target = target;
    }

    public void show() {
        String output = String.format("δ(%s, %c) = %s", this.source.name, input, this.target.name);
        System.out.println(output);
    }
}
