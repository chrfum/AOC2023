import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day19 extends AOCDay {

    public void solve(File in) {
        inputAsList(in);
        int res = 0;

        Map<String, List<String>> workflows = new HashMap<>();
        boolean mappingWorkflows = true;

        for (String line : input) {
            if (line.isEmpty()) {
                mappingWorkflows = false;
                continue;
            }

            if (mappingWorkflows) {
                line = line.replaceAll("[\\\\[\\\\](){}]", " ");
                String[] s = line.split(" ");
                workflows.put(s[0], Arrays.asList(s[1].split(",")));
            } else {
                Map<String, Integer> values = new HashMap<>();
                line = line.replaceAll("[,{}]", " ");
                Scanner sc = new Scanner(line);
                while (sc.hasNext()) {
                    String[] s = sc.next().split("=");
                    values.put(s[0], Integer.parseInt(s[1]));
                }
                sc.close();

                String state = "in";
                boolean stop = false;
                while (!stop) {
                    List<String> l = workflows.get(state);
                    boolean changed = false;
                    for (int i = 0; i < l.size(); i++) {
                        String s = l.get(i);
                        if (!s.contains(":")) state = s;
                        else {
                            String[] temp = s.split(":");
                            String v = Character.toString(temp[0].charAt(0));
                            int x = Integer.parseInt(temp[0].substring(2));
                            switch (s.charAt(1)) {
                                case '<':
                                    if (values.get(v) < x) {
                                        state = temp[1];
                                        changed = true;
                                    } 
                                    break;
                                case '>':
                                    if (values.get(v) > x) {
                                        state = temp[1];
                                        changed = true;
                                    }
                                    break;
                            }
                        }
                        
                        if (changed) break;
                    }

                    if (state.equals("A") || state.equals("R")) stop = true;
                    if (state.equals("A")) res += sum(values); 
                }   
            }
        }

        System.out.println(res);
        Map<String, Integer> xmas = new HashMap<>() {{
            put("x", 0);
            put("m", 1);
            put("a", 2);
            put("s", 3);
        }};

        List<List<Integer>> intervals = new ArrayList<>(List.of(
            List.of(1, 4000),
            List.of(1, 4000),
            List.of(1, 4000),
            List.of(1, 4000)
        ));
       
        System.out.println(dfs(workflows, intervals, "in", xmas));
    }

    private int sum(Map<String, Integer> m) {
        int res = 0;
        for (Integer v : m.values()) {
            res += v;
        }
        return res;
    }

    private long dfs(Map<String, List<String>> m, List<List<Integer>> intervals, String node, Map<String, Integer> xmas) {
        List<String> nexts = m.get(node);
        long res = 0;

        if (node.equals("A")) return numberOfComb(intervals);
        else if (node.equals("R")) return 0;

        for (String next : nexts) {
            List<List<Integer>> tempLst = new ArrayList<>(intervals);

            if (!next.contains(":")) {
                res += dfs(m, tempLst, next, xmas);
            } else {
                String[] temp = next.split(":");
                String dest = temp[1];
                String item = Character.toString(temp[0].charAt(0));
                String operation = Character.toString(temp[0].charAt(1));
                int val = Integer.parseInt(temp[0].substring(2));
                int idx = xmas.get(item);
                List<Integer> interval = tempLst.get(idx);

                switch (operation) {
                    case "<":
                        if (val > interval.get(0) && val < interval.get(1)) {
                            List<Integer> prima = List.of(interval.get(0), val -1);
                            List<Integer> dopo = List.of(val, interval.get(1));
                            tempLst.set(idx, prima);
                            res += dfs(m, tempLst, dest, xmas);
                            intervals.set(idx, dopo);
                        }
                        break;
                    case ">":
                        if (val > interval.get(0) && val < interval.get(1)) {
                            List<Integer> prima = List.of(val+1, interval.get(1));
                            List<Integer> dopo = List.of(interval.get(0), val);
                            tempLst.set(idx, prima);
                            res += dfs(m, tempLst, dest, xmas);
                            intervals.set(idx, dopo);
                        }
                        break;
                }
            }
        }

        return res;
    }

    private long numberOfComb(List<List<Integer>> intervals) {
        long res = 1;
        for (List<Integer> interval : intervals) {
            res *= (interval.get(1) - interval.get(0) + 1);
        }
        return res;
    }
}
