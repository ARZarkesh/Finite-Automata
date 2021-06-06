package com.company;

public class Main {

    public static void main(String[] args) {
        State q0 = new State("q0", StateStatus.Initial);
        State q1 = new State("q1", StateStatus.Final);
        State q2 = new State("q2", StateStatus.Normal);

        TransitionFunction f1 = new TransitionFunction(q0, 'a', q0);
        TransitionFunction f2 = new TransitionFunction(q0, 'b', q1);
        TransitionFunction f3 = new TransitionFunction(q1, 'a', q2);
        TransitionFunction f4 = new TransitionFunction(q1, 'b', q2);
        TransitionFunction f5 = new TransitionFunction(q2, 'a', q2);
        TransitionFunction f6 = new TransitionFunction(q2, 'b', q2);

        State[] states = {q0, q1, q2};
        Alphabet alphabet = new Alphabet(new char[]{'a', 'b'});
        TransitionFunction[] functions = new TransitionFunction[]{f1, f2, f3, f4, f5, f6};

        Machine machine = new Machine(states, alphabet, functions);


        boolean output = machine.testString("");
        System.out.println(output);
    }
}
