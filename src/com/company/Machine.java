package com.company;

import com.company.Exception.InvalidCountOfInitialStatesException;
import com.company.Exception.InvalidInputStringException;

import java.util.LinkedList;

public class Machine {
    private State[] states;
    private Alphabet alphabet;
    private TransitionFunction[] functions;
    private State initialState;
    private LinkedList<State> finalStates;

    public Machine(State[] states, Alphabet alphabet, TransitionFunction[] functions) throws InvalidCountOfInitialStatesException {
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
        checkInitialStates();

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

    private void checkInitialStates() throws InvalidCountOfInitialStatesException {
        int count = 0;
        for (State state : this.states)
            if (state.isInitial) count++;

        if (count != 1)
            throw new InvalidCountOfInitialStatesException("There must be only one initial state in the machine");
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
