package com.company;

import java.util.LinkedList;

enum StateStatus {
    Final,
    Initial,
    Normal,
}

public class State {
    private StateStatus status;
    private String name;
    private LinkedList<TransitionFunction> functions;

    public State(String name, StateStatus status) {
        this.functions = new LinkedList<>();
        this.name = name;
        this.status = status;
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
}