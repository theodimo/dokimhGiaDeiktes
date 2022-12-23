package api;

import java.util.ArrayList;

/**
 * This class contains functions that edit text. Every class that at some point wants to use these functions, has to extend it
 */
public abstract class StringEditor {

    /**
     * Splits the text into words. The splitting is done when we find '.' or ',' or ' '
     * @param text the string which we want to split into words
     * @return an arrayList containing the words that compose the givent ext
     */
    protected ArrayList<String> decompose(String text) {
        ArrayList<String> words = new ArrayList<>();
        String word = "";
        char letter;
        int i = 0;
        while (i < text.length()) {
            letter = text.charAt(i);
            if (letter == '.' || letter == ',' || letter == ' ') { //found terminator letter.
                if (word.length() > 0) { //if a word is formed, then add it to words prepare for new word
                    words.add(word);
                    word = "";
                }
            } else {
                word += letter;
            }
            i += 1;
        }
        //add last word that might not got added previously
        if (word.length() > 0) {
            words.add(word);
        }
        return words;
    }

    /**
     * Concatenates the 2 given Array Lists into one, by creating an arrayList that contains the items of both lists
     * @param s1 the first array list
     * @param s2 the second array list
     * @return the result array list
     */
    protected ArrayList<String> concatenate(ArrayList<String> s1, ArrayList<String> s2) {
        ArrayList<String> words = s1;
        for (String word: s2) {
            words.add(word);
        }
        return words;
    }
}
