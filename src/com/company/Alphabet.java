package com.company;

public class Alphabet {
    private char[] letters;

    public Alphabet(char[] letters) {
        this.letters = letters;
    }

    public char[] getLetters() {
        return letters;
    }

    public boolean contains(char ch) {
        for (char alphabetLetter : letters) {
            if (ch == alphabetLetter) return true;
        }
        return false;
    }
}
