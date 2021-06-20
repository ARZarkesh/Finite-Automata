package com.company;

import java.util.LinkedList;

enum StateStatus {
    FINAL,
    NON_FINAL,
}

public class State {
    private StateStatus status;
    private String name;
    private LinkedList<TransitionFunction> functions;
    public boolean isInitial;

    public State(String name, StateStatus status, boolean isInitial) {
        this.functions = new LinkedList<>();
        this.name = name;
        this.status = status;
        this.isInitial = isInitial;
    }

    public StateStatus getStatus() {
        return status;
    }

    public void addFunction(TransitionFunction function) {
        this.functions.push(function);
    }

    public LinkedList<TransitionFunction> getFunctions() {
        return functions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public State getNext(char input) {
        for (TransitionFunction function : this.functions) {
            if (function.getInput() == input && function.getSource() == this) {
                return function.getTarget();
            }
        }

        return null;
    }

    public LinkedList<State> getNexts(char input) {
        LinkedList<State> output = new LinkedList<>();
        for (TransitionFunction function : this.functions) {
            if (function.getInput() == input && function.getSource() == this) {
                output.push(function.getTarget());
            }
            if (function.getInput() == 'λ' && function.getSource() == this) {
                output.push(function.getTarget());
            }
        }

        return output;
    }
}