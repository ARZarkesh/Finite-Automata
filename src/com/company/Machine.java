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
    private LinkedList<State> currentStates;

    public Machine(State[] states, Alphabet alphabet, TransitionFunction[] functions) throws InvalidCountOfInitialStatesException {
        this.finalStates = new LinkedList<>();
        for (State state : states) {
            if (state.isInitial) {
                initialState = state;
            }
            if (state.isFinal) {
                finalStates.push(state);
            }
        }

        this.states = states;
        this.alphabet = alphabet;
        this.functions = functions;
        checkInitialStates();
    }

    public boolean testString(String string) throws InvalidInputStringException {
        this.currentStates = new LinkedList<>();

        if (!alphabet.contains(string)) {
            throw new InvalidInputStringException("Input is not allowed");
        }

        int ptrInput = -1;

        this.currentStates.push(initialState);
        pushList(this.currentStates, getNextStates(initialState, 'λ'));

        while (this.currentStates.size() > 0 && ptrInput < string.length()) {

            for (State state : this.currentStates) {
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
            for (int i = 0; i < this.currentStates.size(); i++) {
                State state = this.currentStates.get(i);
                pushList(newPossibilities, getNextStates(state, input));
            }
            this.currentStates = newPossibilities;
        }

        System.out.println("Reject");
        return false;
    }

    private boolean isAccept(State state, String string, int index) {
        return state.isFinal && index == string.length() - 1;
    }

    private LinkedList<State> pushList(LinkedList<State> src, LinkedList<State> target) {
        for (State state : target) {
            src.push(state);
        }

        return src;
    }

    private void checkInitialStates() throws InvalidCountOfInitialStatesException {
        int count = 0;
        for (State state : this.states)
            if (state.isInitial) count++;

        if (count != 1)
            throw new InvalidCountOfInitialStatesException("There must be only one initial state in the machine");
    }

    public LinkedList<State> getNextStates(State state, char input) {
        LinkedList<State> output = new LinkedList<>();
        for (TransitionFunction function : this.functions) {
            if (function.input == input && function.source == state) {
                output.push(function.target);
                pushList(output, getNextStates(function.target, 'λ'));
            }
        }

        return output;
    }
}
