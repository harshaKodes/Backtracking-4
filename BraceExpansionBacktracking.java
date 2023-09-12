import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class BraceExpansionBacktracking {

        // Backtracking - Time O(k^(l/k)) and Space O(l)

        List<String> result;

        public String[] expand(String s) {

            // list of strings later converted to array of strings
            this.result = new ArrayList<>();

            // individual blocks consisting of characters inside a pair of braces
            List<List<Character>> blocks = new ArrayList<>();

            int i = 0;

            // iterate over string
            while(i < s.length()) {

                char c = s.charAt(i);

                // new block possible at each new character
                List<Character> block = new ArrayList<>();

                // if open braces
                if(c == '{') {

                    // move further
                    i++;

                    // until close braces
                    while( i < s.length() && s.charAt(i) != '}') {

                        char letter = s.charAt(i);

                        // add actual letters to a block
                        if(letter != ',') {
                            //
                            block.add(letter);

                        }
                        // move further
                        i++;
                    }
                    // sort a block
                    Collections.sort(block);
                }
                // if single characters outside braces
                else {

                    // individual block with single letter
                    block.add(s.charAt(i));
                }
                // move further
                i++;

                // add individual blocks
                blocks.add(block);
            }

            // recursion
            backtrack(blocks, 0, new StringBuilder());

            // list to array
            int words = result.size();
            String[] expansion = new String[words];

            for(int k = 0; k < words; k++) {

                expansion[k] = result.get(k);
            }
            // output
            return expansion;

        }

        public void backtrack(List<List<Character>> blocks, int idx, StringBuilder path) {

            // base
            // terminate if last block is crossed
            if(idx == blocks.size()) {

                // add path word to result
                result.add(path.toString());
                return;
            }

            //logic
            // block at index
            List<Character> block = blocks.get(idx);

            // iterate over a block
            for(int i = 0; i < block.size(); i++) {

                // pre - action
                char c = block.get(i);

                // action
                path.append(c);

                // recurse
                // recursion goes to next block which is at next index
                backtrack(blocks, idx+1, path);

                // backtrack
                // remove last letter in path word
                path.setLength(path.length() - 1);
            }
        }

        public static void main(String[] args) {

            BraceExpansionBacktracking obj = new BraceExpansionBacktracking();

            Scanner scanner = new Scanner(System.in);

            System.out.println("string ");
            String s = scanner.nextLine();

            String[] answer = obj.expand(s);

            for(String word: answer) {

                System.out.println(word);
            }
        }

}

/*
Time complexity - O(k^(l/k))
n = length of string s
k = average length of each block
n/k = number of blocks

In backtracking for l/k blocks each of them have k options to select from
Space complexity - O(l) - blocks are stored as list of lists
*/