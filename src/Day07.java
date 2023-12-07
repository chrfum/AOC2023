import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day07 extends AOCDay {

    public void solve(File in) {
        inputAsList(in);
        List<List<String>> hands = new ArrayList<>(); 
        Map<Character, Integer> cardOrder = new HashMap<>(){{
            put('J', 1);
            put('2', 2);
            put('3', 3);
            put('4', 4);
            put('5', 5);
            put('6', 6);
            put('7', 7);
            put('8', 8);
            put('9', 9);
            put('T', 10);
            put('Q', 11);
            put('K', 12);
            put('A', 13);
        }};

        for (String line : input) {
            Scanner sc = new Scanner(line);
            String hand = sc.next();
            String bid = sc.next();
            boolean insert = false;

            for (int i = 0; i < hands.size(); i++) {
                String currentHand = hands.get(i).get(0);
                if (compare(hand, currentHand, cardOrder).equals(currentHand)) {
                    hands.add(i, List.of(hand, bid));
                    insert = true;
                    break;
                }
            }   
            
            if (!insert) hands.add(List.of(hand, bid));
            sc.close();
        }

        int res = 0;
        for (int i = 0; i < hands.size(); i++) {
            res += ((i+1) * Integer.parseInt(hands.get(i).get(1)));
        }
        System.out.println(res);
    }

    public String compare(String h1, String h2, Map<Character, Integer> m) {
        int value1 = evaluateFive(h1);
        int value2 = evaluateFive(h2);
        if (value1 > value2) return h1;
        else if (value1 < value2) return h2;
        
        for (int i = 0; i < 5; i++) {
            if (m.get(h1.charAt(i)) < m.get(h2.charAt(i))) return h2;
            else if (m.get(h1.charAt(i)) > m.get(h2.charAt(i))) return h1;
        }

        return "";
    }

    public int evaluateFive(String hand) {
        Map<Character, Integer> m = new HashMap<>();

        for (Character c : hand.toCharArray()) {
            if (m.containsKey(c)) m.put(c, m.get(c) + 1);
            else m.put(c, 1);
        }

        int jokers = 0;
        if (m.containsKey('J')) jokers = m.get('J');
        
        for (Map.Entry<Character, Integer> e : m.entrySet()) {
            if (e.getKey() != 'J') {
                if (e.getValue() + jokers == 5) return 7;
            } else {
                if (e.getValue() == 5) return 7;
            }
        }

        return evaluateFour(hand);
    }

    public int evaluateFour(String hand) {
        Map<Character, Integer> m = new HashMap<>();
        
        for (Character c : hand.toCharArray()) {
            if (m.containsKey(c)) m.put(c, m.get(c) + 1);
            else m.put(c, 1);
        }

        int jokers = 0;
        if (m.containsKey('J')) jokers = m.get('J');

        for (Map.Entry<Character, Integer> e : m.entrySet()) {
            if (e.getKey() != 'J') {
                if (e.getValue() + jokers == 4) return 6;
            }
        }

        return evaluateFull(hand);
    }

    public int evaluateFull(String hand) {
        Map<Character, Integer> m = new HashMap<>();
        
        for (Character c : hand.toCharArray()) {
            if (m.containsKey(c)) m.put(c, m.get(c) + 1);
            else m.put(c, 1);
        }
        
        int jokers = 0;
        if (m.containsKey('J')) jokers = m.get('J');

        int counter = 0;
        boolean three = false;
        boolean two = false;
        for (Map.Entry<Character, Integer> e : m.entrySet()) {
            if (e.getValue() == 3) three = true;
            if (e.getValue() == 2) {
                two = true;
                counter++;
            }
            if (counter == 2 && jokers == 1) return 5;
        }

        if (three && two) return 5;

        return evaluateThree(hand);
    }

    public int evaluateThree(String hand) {
        Map<Character, Integer> m = new HashMap<>();
    
        for (Character c : hand.toCharArray()) {
            if (m.containsKey(c)) m.put(c, m.get(c) + 1);
            else m.put(c, 1);
        }

        int jokers = 0;
        if (m.containsKey('J')) jokers = m.get('J');

        for (Map.Entry<Character, Integer> e : m.entrySet()) {
            if (e.getKey() != 'J') {
                if (e.getValue() + jokers == 3) return 4;
            }
        }

        return evaluate2Pair(hand);
    }

    public int evaluate2Pair(String hand) {
        Map<Character, Integer> m = new HashMap<>();
        
        for (Character c : hand.toCharArray()) {
            if (m.containsKey(c)) m.put(c, m.get(c) + 1);
            else m.put(c, 1);
        }

        int jokers = 0;
        if (m.containsKey('J')) jokers = m.get('J');

        int counter = 0;
        for (Map.Entry<Character, Integer> e : m.entrySet()) {
            if (e.getValue() == 2) counter++;
            if (counter == 1 && jokers == 1) return 3;
            if (counter == 2) return 3;
        }

        return evaluatePair(hand);
    }

    public int evaluatePair(String hand) {
        Map<Character, Integer> m = new HashMap<>();
        
        for (Character c : hand.toCharArray()) {
            if (m.containsKey(c)) m.put(c, m.get(c) + 1);
            else m.put(c, 1);
        }

        int jokers = 0;
        if (m.containsKey('J')) jokers = m.get('J');

        for (Map.Entry<Character, Integer> e : m.entrySet()) {
            if (e.getKey() != 'J') {
                if (e.getValue() + jokers == 2) return 2;
            }
        }
        
        return 1;
    }
}
