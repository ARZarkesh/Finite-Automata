package com.company;

import java.util.LinkedList;

public class Machine {
    private State[] states;
    private Alphabet alphabet;
    private TransitionFunction[] functions;
    private State initialState;
    private LinkedList<State> finalStates;

    public Machine(State[] states, Alphabet alphabet, TransitionFunction[] functions) {
        finalStates = new LinkedList<>();

        for (State state : states) {
            if (state.getStatus() == StateStatus.Initial) {
                initialState = state;
            } else if (state.getStatus() == StateStatus.Final) {
                finalStates.push(state);
            }
        }

        this.states = states;
        this.alphabet = alphabet;
        this.functions = functions;

        assignFunctionsToStates();
    }

    public boolean testString(String string) {

        // initialize pointer state
        State ptrState = initialState;

        for (int i = 0; i < string.length(); i++) {
            char ptrInput = string.charAt(i);
            ptrState = ptrState.getNext(ptrInput);
        }

        if (ptrState.getStatus() == StateStatus.Final) return true;

        return false;
    }

    private void assignFunctionsToStates() {
        for (TransitionFunction function : this.functions) {
            function.getSource().addFunction(function);
        }
    }

    public Alphabet getAlphabet() {
        return alphabet;
    }

    public State getInitialState() {
        return initialState;
    }

    public LinkedList<State> getFinalStates() {
        return finalStates;
    }

    public State[] getStates() {
        return states;
    }

    public TransitionFunction[] getFunctions() {
        return functions;
    }
}
