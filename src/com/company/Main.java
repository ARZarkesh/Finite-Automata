package com.company;

public class Main {

    public static void main(String[] args) {
        State q0 = new State(true, false);
        State q1 = new State(false, true);

        TransitionFunction delta1 = new TransitionFunction(q0, '0', q0);
        TransitionFunction delta2 = new TransitionFunction(q0, '1', q1);
        TransitionFunction delta3 = new TransitionFunction(q1, '0', q0);
        TransitionFunction delta4 = new TransitionFunction(q1, '1', q1);


        State[] states = { q0, q1 };
        TransitionFunction[] transitionFunctions = { delta1, delta2, delta3, delta4 };
        char[] alphabet = { '0', '1' };
        State initialState = q0;
        State[] finalStates = { q1 };

        Machine machine = new Machine(states, transitionFunctions, alphabet, initialState, finalStates);

    }
}
