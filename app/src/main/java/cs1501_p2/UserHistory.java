package cs1501_p2;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class UserHistory implements Dict {

    private class TrieNode 
    {
        TrieNode[] children = new TrieNode[26];
        boolean isEndOfWord;
        int count = 0;
    }
    private TrieNode root = new TrieNode();
    private TrieNode currentNode = root;

    @Override
    public void add(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
        node.count++;
    }

    @Override
    public boolean contains(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) return false;
            node = node.children[index];
        }
        return node.isEndOfWord;
    }

    @Override
    public boolean containsPrefix(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) return false;
            node = node.children[index];
        }
        return true;
    }

    public int searchByChar(char c) {
        if (currentNode == null) return -1;
    
        if (currentNode.children[c - 'a'] != null) {
            currentNode = currentNode.children[c - 'a'];
            
            boolean isWord = currentNode.isEndOfWord;
            boolean isPrefix = false;
            for (int i = 0; i < 26; i++) {
                if (currentNode.children[i] != null) {
                    isPrefix = true;
                    break;
                }
            }
    
            if (isWord && isPrefix) return 2;
            if (isWord) return 1;
            return 0;
        } else {
            currentNode = null;
            return -1;
        }
    }

    public void resetByChar() {
        currentNode = root;
    }

    @Override
    public ArrayList<String> suggest() {
        // Here we will use a priority queue to fetch top words based on the frequency
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> b.count - a.count);
        collectSuggestions(root, "", pq);
        
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 5 && !pq.isEmpty(); i++) {
            result.add(pq.poll().word);
        }
        return result;
    }

    private void collectSuggestions(TrieNode node, String word, PriorityQueue<Pair> pq) {
        if (node == null) return;

        if (node.isEndOfWord) {
            pq.add(new Pair(word, node.count));
        }

        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 'a');
            collectSuggestions(node.children[i], word + ch, pq);
        }
    }

    private class Pair {
        String word;
        int count;

        Pair(String word, int count) {
            this.word = word;
            this.count = count;
        }
    }

    @Override
    public int count() {
        return countRec(root);
    }

    private int countRec(TrieNode node) {
        if (node == null) return 0;

        int count = 0;
        if (node.isEndOfWord) count = 1;

        for (int i = 0; i < 26; i++) {
            count += countRec(node.children[i]);
        }

        return count;
    }

    @Override
    public ArrayList<String> traverse() {
        ArrayList<String> words = new ArrayList<>();
        traverseRec(root, "", words);
        return words;
    }

    private void traverseRec(TrieNode node, String currentWord, ArrayList<String> words) {
        if (node == null) return;

        if (node.isEndOfWord) words.add(currentWord);

        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 'a');
            traverseRec(node.children[i], currentWord + ch, words);
        }
    }




}