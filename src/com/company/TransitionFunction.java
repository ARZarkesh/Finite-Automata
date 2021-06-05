package com.company;

public class TransitionFunction {
    private State inputState;
    private char inputChoice;
    private State output;

    public TransitionFunction(State inputState, char inputChoice, State output) {
        this.inputState = inputState;
        this.inputChoice = inputChoice;
        this.output = output;
    }

    public char getInputChoice() {
        return inputChoice;
    }

    public State getInputState() {
        return inputState;
    }

    public State getOutput() {
        return output;
    }
}
