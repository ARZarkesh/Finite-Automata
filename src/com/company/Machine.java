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

        int ptrInput = -1;
        LinkedList<State> possibleStates = new LinkedList<>();
        possibleStates.push(initialState);

        while (possibleStates.size() > 0 && ptrInput < string.length()) {

            for (State state : possibleStates) {
                if (isAccept(state, string, ptrInput)) {
                    System.out.println("Accept");
                    return true;
                }
            }
            ptrInput++;
            if (ptrInput >= string.length()) {
                System.out.println("Reject");
                return false;
            }
            char input = string.charAt(ptrInput);

            LinkedList<State> newPossibilities = new LinkedList<>();
            for (int i = 0; i < possibleStates.size(); i++) {
                State state = possibleStates.get(i);
                pushList(newPossibilities, state.getNexts(input));
            }
            possibleStates = newPossibilities;
        }

        System.out.println("Reject");
        return false;
    }

    private boolean isAccept(State state, String string, int index) {
        return state.getStatus() == StateStatus.FINAL && index == string.length() - 1;
    }

    private void pushList(LinkedList<State> src, LinkedList<State> target) {
        for (State state : target) {
            src.push(state);
        }
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
