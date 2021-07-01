package com.company;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        State q0 = new State("q0", false, true);
        State q1 = new State("q1", false, false);
        State q2 = new State("q2", false, false);
        State q3 = new State("q3", true, false);

        TransitionFunction f1 = new TransitionFunction(q0, 'a', q1);
        TransitionFunction f2 = new TransitionFunction(q0, 'b', q2);
        TransitionFunction f3 = new TransitionFunction(q2, 'λ', q3);

        LinkedList<State> states = new LinkedList<>();
        states.push(q0);
        states.push(q1);
        states.push(q2);
        states.push(q3);

        Alphabet alphabet = new Alphabet(new char[]{'a', 'b'});
        LinkedList<TransitionFunction> functions = new LinkedList<>();
        functions.push(f1);
        functions.push(f2);
        functions.push(f3);

        try {
            Machine machine = new Machine(states, alphabet, functions);
//            machine.testString("a");
            Machine dfa = machine.convertToDFA();
            for (TransitionFunction f : dfa.getFunctions()) {
                f.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
