package com.company;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        State q0 = new State("q0", false, true);
        State q1 = new State("q1", false, false);
        State q2 = new State("q2", true, false);
        State q3 = new State("q3", false, false);
        State q4 = new State("q4", true, false);

        TransitionFunction f1 = new TransitionFunction(q0, '0', q1);
        TransitionFunction f2 = new TransitionFunction(q0, '1', q3);
        TransitionFunction f3 = new TransitionFunction(q1, '0', q2);
        TransitionFunction f4 = new TransitionFunction(q1, '1', q4);
        TransitionFunction f5 = new TransitionFunction(q2, '0', q1);
        TransitionFunction f6 = new TransitionFunction(q2, '1', q4);
        TransitionFunction f7 = new TransitionFunction(q3, '0', q2);
        TransitionFunction f8 = new TransitionFunction(q3, '1', q4);
        TransitionFunction f9 = new TransitionFunction(q4, '0', q4);
        TransitionFunction f10 = new TransitionFunction(q4, '1', q4);

        LinkedList<State> states = new LinkedList<>();
        states.push(q0);
        states.push(q1);
        states.push(q2);
        states.push(q3);
        states.push(q4);

        Alphabet alphabet = new Alphabet(new char[]{'0', '1'});
        LinkedList<TransitionFunction> functions = new LinkedList<>();
        functions.add(f1);
        functions.add(f2);
        functions.add(f3);
        functions.add(f4);
        functions.add(f5);
        functions.add(f6);
        functions.add(f7);
        functions.add(f8);
        functions.add(f9);
        functions.add(f10);

        try {
            Machine machine = new Machine(states, alphabet, functions);
            Machine minimized = machine.minimizeDFA();
//            machine.testString("a");
//            Machine dfa = machine.convertToDFA();
//            for (TransitionFunction f : dfa.getFunctions()) {
//                f.show();
//            }
            for (TransitionFunction function : minimized.getFunctions()) {
                function.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
