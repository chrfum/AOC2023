import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

public class Day12 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);
        long res1 = 0;
        long res2 = 0;

        for (String line : input) {
            Scanner sc = new Scanner(line);
            String s = sc.next();
            String n = sc.next();
            
            
            StringJoiner sj1 = new StringJoiner("?");
            StringJoiner sj2 = new StringJoiner(",");
            for (int i = 0; i < 5; i++) {
                sj1.add(s);
                sj2.add(n);
            }
            String s2 = sj1.toString();
            String n2 = sj2.toString();
            
            
            String[] d = n.split(",");
            int[] damaged = new int[d.length];
            for (int i = 0; i < d.length; i++) damaged[i] = Integer.parseInt(d[i]);

            Map<String, Long> memo = new HashMap<>();
            res1 += calculate(s, damaged, 0, 0, memo);

            String[] d2 = n2.split(",");
            int[] damaged2 = new int[d2.length];
            for (int i = 0; i < d2.length; i++) damaged2[i] = Integer.parseInt(d2[i]);
            res2 += calculate(s2, damaged2, 0, 0, memo);

            sc.close();
            
        }

        System.out.println(res1);
        System.out.println(res2);
    }

    private long calculate(String s, int[] damaged, int len, int i, Map<String, Long> memo) {
        if (s.length() == 0) {
            if (len == 0) return (damaged.length == i) ? 1 : 0;
            else return (damaged.length == i + 1 && len == damaged[i]) ? 1 : 0;
        }
        int length = s.length();
        if (memo.containsKey(length+"-"+i+"-"+len)) return memo.get(length+"-"+i+"-"+len);

        long res = 0;
        List<Character> options = new ArrayList<>();
        if (s.charAt(0) == '?') options.addAll(List.of('#', '.'));
        else options.add(s.charAt(0));
        
        for (Character c : options) {
            if (c == '#') {
                res += calculate(s.substring(1), damaged, len+1, i, memo);
            } else {
                if (len > 0) {
                    if (i < damaged.length && damaged[i] == len) res += calculate(s.substring(1), damaged, 0, i+1, memo);
                } else {
                    res += calculate(s.substring(1), damaged, 0, i, memo);
                }
            }
        }

        
        memo.put(length+"-"+i+"-"+len, res);
        return res;
    }
}
