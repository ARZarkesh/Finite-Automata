package com.company;

public class Machine {
    private State[] states;
    private TransitionFunction[] transitionFunctions;
    private Alphabet alphabet;
    private State initialState;
    private State[] finalStates;

    public Machine(State[] states, TransitionFunction[] transitionFunctions, Alphabet alphabet, State initialState, State[] finalStates) {
        this.states = states;
        this.transitionFunctions = transitionFunctions;
        this.alphabet = alphabet;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public Alphabet getAlphabet() {
        return alphabet;
    }

    public State getInitialState() {
        return initialState;
    }

    public State[] getFinalStates() {
        return finalStates;
    }

    public State[] getStates() {
        return states;
    }

    public TransitionFunction[] getTransitionFunctions() {
        return transitionFunctions;
    }
}
