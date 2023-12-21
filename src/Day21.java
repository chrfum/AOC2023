import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 extends AOCDay {
    
    public void solve(File in) {
        inputAsMatrix(in);

        int startI = 0;
        int startJ = 0;

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("S")) {
                    startI = i;
                    startJ = j;
                }
            }
        }

        ArrayDeque<List<Integer>> c = new ArrayDeque<>();
        c.add(List.of(startI, startJ, 0));
        int res = 0;
        Map<List<Integer>, Boolean> visited = new HashMap<>();

        while (!c.isEmpty()) {
            List<Integer> current = c.removeFirst();
            if (visited.containsKey(current)) continue;
            else visited.put(current, true);
            
            int i = current.get(0);
            int j = current.get(1);
            int distance = current.get(2);

            if (distance == 65) break;
            if (distance == 64) res++;
            else {
                if (isExpandible(matrix, i+1, j)) c.add(List.of(i+1, j, distance + 1));
                if (isExpandible(matrix, i-1, j)) c.add(List.of(i-1, j, distance + 1));
                if (isExpandible(matrix, i, j+1)) c.add(List.of(i, j+1, distance + 1));
                if (isExpandible(matrix, i, j-1)) c.add(List.of(i, j-1, distance + 1));
            }

        
        }
        System.out.println(res);
    }

    private boolean isExpandible(List<List<String>> m, int i, int j) {
        if (i >= 0 && i < matrix.size() && j >= 0 && j < matrix.get(i).size() && !m.get(i).get(j).equals("#")) return true;
        return false;
    }
}
