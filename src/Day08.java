import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day08 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);

        Map<String, List<String>> steps = new HashMap<>();
        List<String> starts = new ArrayList<>();
        String instructions = input.get(0);
        input.remove(0);
        input.remove(0);

        for (String line : input) {
            Scanner sc = new Scanner(line);
            String start = sc.next();
            if (start.endsWith("A")) starts.add(start);
            sc.next();
            String l = sc.next();
            String r = sc.next();
            
            String left = l.substring(1, l.length() - 1);
            String right = r.substring(0, r.length() - 1);
            steps.put(start, List.of(left, right));
            sc.close();
        }
        
        List<Long> n = new ArrayList<>();
        for (int i = 0; i < starts.size(); i++) {
            List<String> l = move(starts.get(i), steps, instructions, 0);
            while (!l.get(0).equals("ZZZ")) {
                l = move(l.get(0), steps, instructions, Integer.parseInt(l.get(1)));
            }
            n.add(Long.parseLong(l.get(1)));
        }

        System.out.println(calculateMCM(n));
    }

    private List<String> move(String start, Map<String, List<String>> steps, String instructions, int moves) {
        for (Character c : instructions.toCharArray()) {
            moves++;
            if (c == 'R') start = steps.get(start).get(1);
            else start = steps.get(start).get(0);
            if (start.endsWith("Z")) return List.of("ZZZ", String.valueOf(moves));
        }

        return List.of(start, String.valueOf(moves));
    }

    private long mcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long mcm(long a, long b) {
        return (a * b) / mcd(a, b);
    }

    public long calculateMCM(List<Long> numbers) {
        long mcm = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            mcm = mcm(mcm, numbers.get(i));
        }

        return mcm;
    }
}
