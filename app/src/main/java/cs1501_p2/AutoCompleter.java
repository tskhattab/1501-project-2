package cs1501_p2;

import java.util.ArrayList;
import java.io.*;

public class AutoCompleter implements AutoComplete_Inter {

    private DLB dictionary;
    private UserHistory userHistory;

    public AutoCompleter(String dict_fname) {
        this(dict_fname, null);
    }

    public AutoCompleter(String dict_fname, String uhist_state_fname) {
        dictionary = new DLB();
        loadDictionary(dict_fname);

        userHistory = new UserHistory();
        if(uhist_state_fname != null) {
            loadUserHistory(uhist_state_fname);
        }
    }

    private void loadDictionary(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserHistory(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            userHistory = (UserHistory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> nextChar(char c) {
        dictionary.searchByChar(c); // Update dictionary's state
        ArrayList<String> dictSuggestions = dictionary.suggest();

        // Merge the dictionary suggestions and user history to get the top suggestions.
        return mergeSuggestions(dictSuggestions, userHistory.suggest());
    }

    private ArrayList<String> mergeSuggestions(ArrayList<String> dictSuggestions, ArrayList<String> userSuggestions) {
        ArrayList<String> merged = new ArrayList<>(userSuggestions);
        
        for(String sugg : dictSuggestions) {
            if(!merged.contains(sugg)) {
                merged.add(sugg);
            }
        }
        
        while(merged.size() > 5) {
            merged.remove(merged.size() - 1);
        }
        
        return merged;
    }

    @Override
    public void finishWord(String word) {
        userHistory.add(word); // Update the UserHistory
        dictionary.resetByChar(); // Reset dictionary's state
    }

    @Override
    public void saveUserHistory(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(userHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
