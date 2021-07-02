package com.company;

import com.company.Exception.InvalidCountOfInitialStatesException;
import com.company.Exception.InvalidInputStringException;

import java.util.HashMap;
import java.util.LinkedList;

public class Machine {
    private LinkedList<State> states;
    private Alphabet alphabet;
    private LinkedList<TransitionFunction> functions;
    private State initialState;
    private LinkedList<State> finalStates;
    private LinkedList<State> currentStates;

    public Machine(LinkedList<State> states, Alphabet alphabet, LinkedList<TransitionFunction> functions) throws InvalidCountOfInitialStatesException {
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

    public Machine convertToDFA() throws InvalidCountOfInitialStatesException {
        if (isDFA()) {
            System.out.println("There is no need conversion because this machine is already a DFA");
            return null;
        }

        LinkedList<State> dfaStates = new LinkedList<>();
        LinkedList<TransitionFunction> dfaFunctions = new LinkedList<>();
        State trapState = new State("Trap", false, false);
        for (char letter : this.alphabet.getLetters()) {
            dfaFunctions.add(new TransitionFunction(trapState, letter, trapState));
        }

        String initialStateName = "{" + this.initialState.name;
        for (State state : getNextStates(this.initialState, 'λ')) {
            initialStateName += state.name;
        }
        initialStateName += "}";
        State dfaInitialState = new State(initialStateName, false, true);

        dfaStates.add(dfaInitialState);

        for (int i = 0; i < dfaStates.size(); i++) {
            State currentState = dfaStates.get(i);
            if (currentState == trapState) break;

            for (char letter : this.alphabet.getLetters()) {
                LinkedList<TransitionFunction> result = new LinkedList<>();

                for (TransitionFunction function : this.functions) {
                    if (function.input == letter && currentState.name.contains(function.source.name)) {
                        LinkedList<State> targets = getNextStates(function.source, letter);

                        for (State target : targets) {
                            result.add(new TransitionFunction(currentState, letter, target));
                        }
                    }
                }

                if (result.size() == 0) {
                    dfaFunctions.add(new TransitionFunction(currentState, letter, trapState));
                    if (!dfaStates.contains(trapState)) {
                        dfaStates.add(trapState);
                    }
                } else if (result.size() == 1) {
                    State target = result.get(0).target;
                    dfaFunctions.add(new TransitionFunction(currentState, letter, target));
                    if (!dfaStates.contains(target)) {
                        dfaStates.add(target);
                    }
                } else if (result.size() > 1) {
                    String name = "{";
                    boolean isFinal = false;
                    for (TransitionFunction function : result) {
                        if (!name.contains(function.target.name)) name += function.target.name;
                        if (function.target.isFinal) isFinal = true;
                    }
                    name += "}";
                    State combinedState = new State(name, isFinal, false);
                    dfaFunctions.add(new TransitionFunction(currentState, letter, combinedState));
                    if (!dfaStates.contains(combinedState)) {
                        dfaStates.add(combinedState);
                    }
                }
            }
        }

        Machine dfa = new Machine(dfaStates, this.alphabet, dfaFunctions);
        return dfa;
    }

    private boolean isDFA() {
        for (State state : this.states) {
            for (char letter : this.alphabet.getLetters()) {
                if (getNextStates(state, letter).size() != 1) return false;
            }
        }

        return true;
    }

    public boolean isDistinguishable(State s1, State s2) {
        if (!s1.isFinal && s2.isFinal) return true;
        if (s1.isFinal && !s2.isFinal) return true;

        for (char letter : this.alphabet.getLetters()) {
            if (getNextStates(s1, letter).get(0) != getNextStates(s2, letter).get(0))
                return true;
        }

        return false;
    }

    public Machine minimizeDFA() throws InvalidCountOfInitialStatesException {
        Machine minimizedDFA = new Machine(this.states, this.alphabet, this.functions);

        if (!isDFA()) {
            System.out.println("The machine is not a DFA, please first convert it to a DFA then retry again.");
            return null;
        }

        for (int i = 0; i < minimizedDFA.states.size() - 1; i++) {
            for (int j = i + 1; j < minimizedDFA.states.size(); j++) {
                State s1 = minimizedDFA.states.get(i);
                State s2 = minimizedDFA.states.get(j);
                if (!isDistinguishable(s1, s2)) {
                    s1.name = s1.name + s2.name;
                    minimizedDFA.states.remove(s2);
                    for (int k = 0; k < minimizedDFA.functions.size(); k++) {
                        TransitionFunction function = minimizedDFA.functions.get(k);
                        if (function.source == s2) {
                            minimizedDFA.functions.remove(function);
                            k--;
                        } else if (function.target == s2) {
                            function.target = s1;
                        }
                    }
                }
            }
        }

        return minimizedDFA;
    }

    public LinkedList<TransitionFunction> getFunctions() {
        return functions;
    }

    public void show() {
        for (TransitionFunction function : this.functions) {
            function.show();
        }
    }
}
