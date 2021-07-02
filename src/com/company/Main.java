package com.company;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean shouldContinue = true;

        System.out.println("Enter your language alphabet letters (don't separate letters): ");
        String letters = scanner.next();
        Alphabet alphabet = new Alphabet(letters.toCharArray());

        LinkedList<State> states = new LinkedList<>();
        while (shouldContinue) {
            System.out.println("Enter state name: ");
            String name = scanner.next();

            System.out.println("is final? (y/n) ");
            boolean isFinal = scanner.next().equalsIgnoreCase("y");

            System.out.println("is initial? (y/n) ");
            boolean isInitial = scanner.next().equalsIgnoreCase("y");

            State state = new State(name, isFinal, isInitial);
            states.push(state);

            System.out.println("Do you want add another state? (y/n) ");
            shouldContinue = scanner.next().equalsIgnoreCase("y");
        }
        shouldContinue = true;

        LinkedList<TransitionFunction> functions = new LinkedList<>();
        while (shouldContinue) {
            System.out.println("Enter function sourceState: ");
            String sourceName = scanner.next();

            System.out.println("Enter function characterInput: ");
            char input = scanner.next().charAt(0);

            System.out.println("Enter function targetState: ");
            String targetName = scanner.next();

            State source = null;
            State target = null;

            for (State state : states) {
                if (state.name.equals(sourceName)) source = state;
                if (state.name.equals(targetName)) target = state;
            }

            TransitionFunction function = new TransitionFunction(source, input, target);
            functions.add(function);

            System.out.println("Do you want add another function? (y/n) ");
            shouldContinue = scanner.next().equalsIgnoreCase("y");
        }
        shouldContinue = true;

        System.out.println("Choose operation: (1 or 2 or 3) ");
        System.out.println("1. Test string in this machine");
        System.out.println("2. Convert NFA to DFA");
        System.out.println("3. Minimize DFA");
        int operation = scanner.nextInt();

        try {
            Machine machine = new Machine(states, alphabet, functions);

            switch (operation) {
                case 1:
                    while (shouldContinue) {
                        System.out.println("Your string: ");
                        String string = scanner.next();
                        machine.testString(string);

                        System.out.println("Do you want test another string? (y/n) ");
                        shouldContinue = scanner.next().equalsIgnoreCase("y");
                    }
                    break;
                case 2:
                    Machine dfa = machine.convertToDFA();
                    dfa.show();
                    break;
                case 3:
                    Machine minimizedDFA = machine.minimizeDFA();
                    minimizedDFA.show();
                    break;
                default:
                    System.out.println("Invalid operation");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
