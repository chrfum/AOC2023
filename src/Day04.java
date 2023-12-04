import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day04 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);
        
        int res1 = 0;
        int res2 = 0;
        Map<Integer, Integer> cardsWon = new HashMap<>();
        for (String line : input) {
            Map<Integer, Boolean> cards = new HashMap<>();
            Scanner sc = new Scanner(line);
            sc.next();
            
            String s = sc.next();
            int cardNumber = Integer.parseInt(s.substring(0, s.length() - 1));
            if (!cardsWon.containsKey(cardNumber)) cardsWon.put(cardNumber, 1);
            else cardsWon.put(cardNumber, cardsWon.get(cardNumber) + 1);
            while (sc.hasNextInt()) cards.put(sc.nextInt(), true);
            sc.next();

            int first = 1;
            boolean firstWin = true;
            int winningCards = 0;
            while (sc.hasNextInt()) {
                if (cards.containsKey(sc.nextInt())) {
                    winningCards++;
                    res1 += first;
                    if (firstWin) firstWin = false;
                    else first *= 2;
                }
            }
            sc.close();

            for (int i = 1; i <= winningCards; i++) {
                int currentCardsWon = cardsWon.get(cardNumber);
                if (cardsWon.containsKey(cardNumber+i)) cardsWon.put(cardNumber+i, cardsWon.get(cardNumber+i) + currentCardsWon);
                else cardsWon.put(cardNumber+i, currentCardsWon);
            } 
        }
        System.out.println(res1);
        for (Map.Entry<Integer, Integer> e : cardsWon.entrySet()) res2 += e.getValue();
        System.out.println(res2);
    }
}