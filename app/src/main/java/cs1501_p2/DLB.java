package cs1501_p2;

import java.util.ArrayList;

public class DLB implements Dict
{
    private static DLBNode root;
    private static DLBNode lastSearchNode;

    public DLB()
    {
        root = new DLBNode('/');
        lastSearchNode = root;
    }

    private char[] toCharArray(String key)
    {
        char[] word = new char[key.length()];
        for(int x = 0; x < key.length(); x++)
        {
            word[x] = key.charAt(x);
        }
        return word;
    }

     /**
    * Add a new word to the dictionary
    *
     * @param key New word to be added to the dictionary
     */
    public void add(String key)
    {
        char[] word = toCharArray(key);
        root = add_rec(root, word, 0);
    }

    private DLBNode add_rec(DLBNode node, char [] word, int depth)
    {
        if(node == null)
        {
            node = new DLBNode(word[depth]);
        }
    
        if(word[depth] == node.getLet())
        {
            if(depth == word.length - 1)
            {
                if (node.getDown() == null || node.getDown().getLet() != '^') {
                    node.setDown(new DLBNode('^'));
                }
            }
            else
            {
                node.setDown(add_rec(node.getDown(), word, depth + 1));
            }
        }
        else if (word[depth] < node.getLet())
        {
            // The current word should come before the current node
            DLBNode newNode = new DLBNode(word[depth]);
            newNode.setRight(node);
            if (depth < word.length - 1)
            {
                newNode.setDown(add_rec(newNode.getDown(), word, depth + 1));
            }
            return newNode;
        }
        else
        {
            node.setRight(add_rec(node.getRight(), word, depth));
        }
        return node;
    }


    /**
     * Check if the dictionary contains a word
     *
     * @param key Word to search the dictionary for
     *
     * @return true if key is in the dictionary, false otherwise
     */
    public boolean contains(String key)
    {
        char[] word = toCharArray(key);

        return contains_rec(root, word, 0);
    }

    private boolean contains_rec(DLBNode curr, char[] word, int depth)
    { 
        if(curr == null && depth != word.length )
        {
            return false;
        }
        
        if(depth == word.length)
        {
            return true;
        }
        
        if(word[depth] == curr.getLet())
        {
            return contains_rec(curr.getDown(), word, depth + 1);
        }
        return contains_rec(curr.getRight(), word, depth);
    }

    @Override
    /**
     * Check if a String is a valid prefix to a word in the dictionary
     *
     * @param pre Prefix to search the dictionary for
     *
     * @return true if prefix is valid, false otherwise
     */
    public boolean containsPrefix(String pre)
    {
        DLBNode current = root;
        char[] word = pre.toCharArray();

        for (char c : word) 
        {
            if (current == null) 
            {
                return false;
            }
            if (current.getLet() != c) 
            {
                current = current.getRight(); 
            }
            else
            {
                current = current.getDown();
            } 
        }
        return true;
    }

    /**
     * Search for a word one character at a time
     *
     * @param next Next character to search for
     *
     * @return int value indicating result for current by-character search:
     *         -1: not a valid word or prefix
     *         0: valid prefix, but not a valid word
     *         1: valid word, but not a valid prefix to any other words
     *         2: both valid word and a valid prefix to other words
     */
    public int searchByChar(char next)
    {
        if (lastSearchNode == null) 
        {
            lastSearchNode = root;
        }
        DLBNode tempNode = lastSearchNode;
        int result;
        if(lastSearchNode.getLet() == '/')
        {
            result = searchByChar_rec(next, tempNode.getRight());
        }
        else
        {
            result = searchByChar_rec(next, tempNode.getDown());
        }
        

        if(result != -1)
        {
            if(lastSearchNode.getLet() != '/')
            {
                lastSearchNode = lastSearchNode.getDown();
            }
            while(lastSearchNode != null && lastSearchNode.getLet() != next)
            {
                lastSearchNode = lastSearchNode.getRight();
                
            }  
        }
        return result;
    }

    private int searchByChar_rec(char key, DLBNode curr)
    {
        int x,y;
        if(curr == null)
        {
            return -1;
        }

        //lastSearchNode = curr;
        if(curr.getLet() == key)
        {
            if(curr.getDown().getLet() == '^' && curr.getDown().getRight() != null )
            { 
                return 2;
            }
            else {
                
                if(curr.getDown().getLet() == '^'  && curr.getDown().getRight() == null)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }

        }
        x = searchByChar_rec(key, curr.getRight());
        return x;
    }


    @Override
    public void resetByChar() {
        lastSearchNode = root;
    }

    @Override
    public ArrayList<String> suggest() 
    {
        ArrayList<String> suggestions = new ArrayList<>();
        if (lastSearchNode == null)
        {
            return suggestions;
        } 


        StringBuilder prefix = new StringBuilder();
        collectSuggestions(lastSearchNode, lastSearchNode, prefix, suggestions, 5);
        return suggestions;
    }

    private void collectSuggestions(DLBNode start, DLBNode node, StringBuilder prefix, ArrayList<String> suggestions, int limit) 
    {

        if (node == null || (suggestions.size() >= limit && limit != -1))
        {

            return;
        }
        if (node.getLet() == '^') 
        {
            suggestions.add(prefix.toString());
        } 
        else
        {
            prefix.append(node.getLet());
            collectSuggestions(start, node.getDown(), prefix, suggestions, limit);  // Go down
            prefix.deleteCharAt(prefix.length() - 1); 
        }
        if(start != node)
        {
            collectSuggestions(start, node.getRight(), prefix, suggestions, limit);
        }
    }

    @Override
    public ArrayList<String> traverse() 
    {
        ArrayList<String> traversal = new ArrayList<>();
        StringBuilder prefix = new StringBuilder();
        collectSuggestions(root, root.getRight(), prefix, traversal, -1);
        return traversal;
    }


    @Override
    /**
     * Count the number of words in the dictionary
     *
     * @return int, the number of (distinct) words in the dictionary
     */
    public int count()
    {
        return count_rec(root);
    }
    public int count_rec(DLBNode curr)
    {
        
        if(curr == null)
        {
            return 0;
        }
        int count = 0;
        if(curr.getLet() == '^')
        {
            count++;
        }
        
        count += count_rec(curr.getDown());
        count += count_rec(curr.getRight());

        
        return count;
    }
}