import java.io.File;
import java.util.Scanner;
import java.util.StringJoiner;

public class Day12 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);
        long res = 0;
        
        for (String line : input) {
            Scanner sc = new Scanner(line);
            String s = sc.next();
            String n = sc.next();
          
            long x = permute(s, n, 0);
            
            res += x;
            
            sc.close();
        }

        System.out.println(res);
    }

    private long permute(String s, String n, long counter) {
        if (!s.contains("?")) {
            if (isCorrect(s, n)) return counter+1;
            return counter;
        } 

        String s0 = s.replaceFirst("\\?", "#");
        String s1 = s.replaceFirst("\\?", ".");
        if (isNowCorrect(s0, n)) counter = permute(s0, n, counter);
        if (isNowCorrect(s1, n)) counter = permute(s1, n, counter);

        return counter;
    }

    private boolean isNowCorrect(String s, String n) {
        String[] numbers = n.split(",");
        int idx = 0;
        int counter = 0;
        for (Character c : s.toCharArray()) {
            if (c == '?') return true;
            if (c == '#') counter++;
            else {
                if (counter == 0) continue;
                if (String.valueOf(counter).equals(numbers[idx])) idx++;
                else return false;

                if (idx == numbers.length) return true;
                counter = 0;
            } 
        }
        if (String.valueOf(counter).equals(numbers[idx])) return true;
        return false;
    }

    private boolean isCorrect(String s, String n) {
        int counter = 0;
        StringJoiner sj = new StringJoiner(",");
        for (Character c : s.toCharArray()) {
            if (c == '.') {
                if (counter != 0) sj.add(String.valueOf(counter));
                counter = 0;
            } else counter++;
        }
        if (counter > 0) sj.add(String.valueOf(counter));
        if (sj.toString().equals(n)) return true;
        return false;
    }
}
