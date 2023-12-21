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
        Map<List<Integer>, Boolean> visited = new HashMap<>();
        Map<Integer, Integer> distances = new HashMap<>();
    
        while (!c.isEmpty()) {
            List<Integer> current = c.removeFirst();
            
            int i = current.get(0);
            int j = current.get(1);
            int distance = current.get(2);
            if (visited.containsKey(List.of(i, j))) continue;
            else visited.put(List.of(i, j), true);

            //if (i == startI && j == startJ) System.out.println(distance);
            if (distance != 0 && distance != 1) {
                if (distances.containsKey(distance)) distances.put(distance, distances.get(distance) + 1);
                else distances.put(distance, distances.get(distance - 2) + 1);
            } else {
                if (distances.containsKey(distance)) distances.put(distance, distances.get(distance) + 1);
                else distances.put(distance, 1);
            }
            

            if (distance == 65+262+262+1) break;

            else {
                if (isExpandible(matrix, i+1, j)) c.add(List.of(i+1, j, distance + 1));
                if (isExpandible(matrix, i-1, j)) c.add(List.of(i-1, j, distance + 1));
                if (isExpandible(matrix, i, j+1)) c.add(List.of(i, j+1, distance + 1));
                if (isExpandible(matrix, i, j-1)) c.add(List.of(i, j-1, distance + 1));
            }
        }

        long res = distances.get(65+262+262);
        long precRes = distances.get(65+262);
        long diff = (res - precRes) - (distances.get(65+262) - distances.get(65));
        for (int i = 0; i < 101148; i++) {
            long prec = res;
            res = res + (res - precRes) + diff;
            precRes = prec;
        }
        System.out.println(distances.get(64));
        System.out.println(res);
    }

    private boolean isExpandible(List<List<String>> m, int i, int j) {
        if (!m.get(newI(i, matrix)).get(newJ(j, matrix)).equals("#")) return true;
        return false;
    }

    private int newI(int i, List<List<String>> m) {
        if (i < 0) i = i % m.size() + m.size();

        return i % m.size();
    }

    private int newJ(int j, List<List<String>> m) {
        if (j < 0) j = j % m.get(0).size() + m.get(0).size();

        return j % m.get(0).size();
    }
}
