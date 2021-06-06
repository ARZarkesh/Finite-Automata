package com.company;

import com.company.Exception.InvalidInputStringException;

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
            if (state.isInitial) {
                initialState = state;
            }
            if (state.getStatus() == StateStatus.FINAL) {
                finalStates.push(state);
            }
        }

        this.states = states;
        this.alphabet = alphabet;
        this.functions = functions;

        assignFunctionsToStates();
    }

    public boolean testString(String string) throws InvalidInputStringException {

        if (!alphabet.contains(string)) {
            throw new InvalidInputStringException("Input is not allowed");
        }

        // initialize pointer state
        State ptrState = initialState;

        for (int i = 0; i < string.length(); i++) {
            char ptrInput = string.charAt(i);
            ptrState = ptrState.getNext(ptrInput);
        }

        if (ptrState.getStatus() == StateStatus.FINAL) {
            System.out.println("Accept");
            return true;
        }

        System.out.println("Reject");
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
