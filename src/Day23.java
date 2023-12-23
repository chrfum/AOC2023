import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 extends AOCDay {
    
    public void solve(File in) {
        inputAsMatrix(in);

        int startI = 0, startJ = 0, endI = 0, endJ = 0;
    
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (i == 0 && matrix.get(i).get(j).equals(".")) {
                    startI = i;
                    startJ = j;
                } else if (i == matrix.size() - 1 && matrix.get(i).get(j).equals(".")) {
                    endI = i;
                    endJ = j;
                }
            }
        }

        Map<List<Integer>, List<List<Integer>>> nexts = new HashMap<>();
        nexts.put(List.of(startI, startJ), new ArrayList<>());
        ArrayDeque<List<Integer>> crossings = new ArrayDeque<>();
        crossings.add(List.of(startI, startJ));
        Map<List<Integer>, Boolean> visitedCrossings = new HashMap<>();
        while (!crossings.isEmpty()) {
            List<Integer> cross = crossings.removeFirst();
            int sI = cross.get(0);
            int sJ = cross.get(1);
            
            nexts.put(List.of(sI, sJ), new ArrayList<>());
            visitedCrossings.put(List.of(sI, sJ),  true);
            
            nextCross(matrix, sI, sJ, sI, sJ, 0, nexts, new HashMap<List<Integer>, Boolean>());
            List<List<Integer>> n = nexts.get(List.of(sI, sJ));

            for (int i = 0; i < n.size(); i++) {
                if (visitedCrossings.containsKey(List.of(n.get(i).get(0), n.get(i).get(1)))) continue;
                crossings.add(List.of(n.get(i).get(0), n.get(i).get(1)));
            }
        }
        
        ArrayDeque<List<Integer>> c = new ArrayDeque<>();
        ArrayDeque<Map<List<Integer>, Boolean>> visitedQueue = new ArrayDeque<>();
        c.add(List.of(startI, startJ, 0, 1));
        visitedQueue.add(new HashMap<>());

        int maxDistance = 0;

        while (!c.isEmpty()) {
            List<Integer> position = c.removeLast();
            Map<List<Integer>, Boolean> visited = new HashMap<>(visitedQueue.removeLast());

            int i = position.get(0);
            int j = position.get(1);
            int distance = position.get(2);

            if (visited.containsKey(List.of(i, j))) continue;
            else visited.put(List.of(i, j), true);

            
            if (i == endI && j == endJ) if (distance > maxDistance) maxDistance = distance;
            
            List<List<Integer>> nextCrosses = nexts.get(List.of(i, j));
            for (int k = 0; k < nextCrosses.size(); k++) {
                List<Integer> nextCross = nextCrosses.get(k);
                c.add(List.of(nextCross.get(0), nextCross.get(1), distance + nextCross.get(2)));
                visitedQueue.add(visited);
            }
        }

        System.out.println(maxDistance);
    }

    private String get(List<List<String>> matrix, int i, int j) {
        if (i >= 0 && j >= 0 && i < matrix.size() && j < matrix.get(i).size()) return matrix.get(i).get(j);
        return "#";
    }

    private void nextCross(List<List<String>> matrix, int i, int j, int startI, int startJ, int distance, Map<List<Integer>, List<List<Integer>>> nexts, Map<List<Integer>, Boolean> visited) {
        visited.put(List.of(i, j), true);
        
        int counter = 0;
        if (!get(matrix, i-1, j).equals("#")) counter++;
        if (!get(matrix, i+1, j).equals("#")) counter++;    
        if (!get(matrix, i, j-1).equals("#")) counter++;        
        if (!get(matrix, i, j+1).equals("#")) counter++;      

        List<List<Integer>> l = nexts.get(List.of(startI, startJ));
        if ((counter >= 3 || counter == 1) && (i != startI || j != startJ)) {
            l.add(List.of(i, j, distance));
        } else {
            if (!get(matrix, i-1, j).equals("#") && !visited.containsKey(List.of(i-1, j))) {
                nextCross(matrix, i-1, j, startI, startJ, distance+1, nexts, visited);
            } 
            if (!get(matrix, i+1, j).equals("#") && !visited.containsKey(List.of(i+1, j))) {
                nextCross(matrix, i+1, j, startI, startJ, distance+1, nexts, visited);   
            }
            if (!get(matrix, i, j-1).equals("#") && !visited.containsKey(List.of(i, j-1))) {
                nextCross(matrix, i, j-1, startI, startJ, distance+1, nexts, visited);       
            }
            if (!get(matrix, i, j+1).equals("#") && !visited.containsKey(List.of(i, j+1))) {
                nextCross(matrix, i, j+1, startI, startJ, distance+1, nexts, visited);     
            }
        }
    }
}
