package test;


import java.io.*;
import java.util.Scanner;

public class IOSearcher {

    static public boolean search(String word, String f1, String f2) throws IOException {
        File file = new File(f1);
        Scanner input = new Scanner(file);
        while (input.hasNext()) {
            String w = input.next();
            if (w.equals(word)) {
                return true;
            }
        }
        File file2 = new File(f2);
        Scanner input2 = new Scanner(file2);
        while (input2.hasNext()) {
            String w = input2.next();
            if (w.equals(word)) {
                return true;
            }
        }
        return false;
    }
}
