package com.company;

public class Main {

    public static void main(String[] args) {
        State q0 = new State("q0", false, true);
        State q1 = new State("q1", false, false);
        State q2 = new State("q2", false, false);
        State q3 = new State("q3", false, false);
        State q4 = new State("q4", true, false);

        TransitionFunction f1 = new TransitionFunction(q0, 'a', q1);
        TransitionFunction f2 = new TransitionFunction(q1, 'λ', q2);
        TransitionFunction f3 = new TransitionFunction(q2, 'λ', q3);
        TransitionFunction f4 = new TransitionFunction(q3, 'λ', q4);

        State[] states = {q0, q1, q2, q3, q4};
        Alphabet alphabet = new Alphabet(new char[]{'a'});
        TransitionFunction[] functions = new TransitionFunction[]{f1, f2, f3, f4};

        try {
            Machine machine = new Machine(states, alphabet, functions);
            machine.testString("a");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
