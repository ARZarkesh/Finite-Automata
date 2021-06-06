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

    public boolean contains(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!contains(string.charAt(i))) return false;
        }

        return true;
    }
}
