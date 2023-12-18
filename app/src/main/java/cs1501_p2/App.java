/**
 * A driver for CS1501 Project 2
 * @author    Dr. Farnan
 */
package cs1501_p2;

//import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.checkerframework.checker.units.qual.A;

public class App {
    public static void main(String[] args) {
        String eng_dict_fname = "build/resources/main/dictionary.txt";
        String uhist_state_fname = "build/resources/main/uhist_state.p2";

        AutoCompleter ac;
        File check = new File(uhist_state_fname);
        if (check.exists()) {
            ac = new AutoCompleter(eng_dict_fname, uhist_state_fname);
        } else {
            ac = new AutoCompleter(eng_dict_fname);
        }

        printPredictions(ac, 't');
        printPredictions(ac, 'h');
        printPredictions(ac, 'e');
        printPredictions(ac, 'r');
        printPredictions(ac, 'e');

        String word = "thereabout";
        System.out.printf("Selected: %s\n\n", word);
        ac.finishWord(word);

        printPredictions(ac, 't');
        printPredictions(ac, 'h');
        printPredictions(ac, 'e');
        printPredictions(ac, 'r');
        printPredictions(ac, 'e');

        ac.saveUserHistory(uhist_state_fname);
    }

    private static void printPredictions(AutoCompleter ac, char next) {
        System.out.printf("Entered: %c\n", next);

        ArrayList<String> preds = ac.nextChar(next);

        System.out.println("Predictions:");
        int c = 0;
        for (String p : preds) {
            System.out.printf("\t%d: %s\n", ++c, p);
        }
        System.out.println();
    }

    String dict_fname = "build/resources/test/dictionary.txt";

    DLB dlb = new DLB();
    // try (Scanner s = new Scanner(new File(dict_fname))) {
    //             while (s.hasNext()) {
    //                 dlb.add(s.nextLine());
    //             }
    // } catch (IOException e) {
    //             e.printStackTrace();
    //      }


//     dlb.add("A");
//     dlb.add("a");
//     dlb.add("definite");
//     dlb.add("dict");
//     dlb.add("dictionary");
//     dlb.add("is");
//     dlb.add("this");



//     dlb.searchByChar('q');
//     dlb.resetByChar();
//     dlb.searchByChar('d');
//     dlb.searchByChar('i');
//     dlb.searchByChar('c');
//     dlb.searchByChar('t');
//     dlb.resetByChar();
//     dlb.searchByChar('i');
//     dlb.contains("this");
//     dlb.searchByChar('s');
//     dlb.resetByChar();

//     dlb.searchByChar('d');
//     //ArrayList<String> sugs = dlb.suggest();
//     String[] expected = new String[] { "definite", "dict", "dictionary" };

//     //System.out.println(sugs.get(1));
//     //System.out.println(expected[1]);
//     expected = new String[] { "A", "a", "definite", "dict", "dictionary", "is", "this" };
//     ArrayList<String> trav = dlb.traverse();

//     System.out.println(trav.get(0));
//     System.out.println(expected[0]);
// }
}
