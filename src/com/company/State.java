package com.company;

public class State {
    public String name;
    public boolean isInitial;
    public boolean isFinal;

    public State(String name, boolean isFinal, boolean isInitial) {
        this.name = name;
        this.isFinal = isFinal;
        this.isInitial = isInitial;
    }
}