package com.company;

public class State {
    private boolean isInitial;
    private boolean isFinal;

    public State(boolean isInitial, boolean isFinal) {
        this.isInitial = isInitial;
        this.isFinal = isFinal;
    }

    public boolean getIsInitial() {
        return isInitial;
    }

    public boolean getIsFinal() {
        return isFinal;
    }
}
