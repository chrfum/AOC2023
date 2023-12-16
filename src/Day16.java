import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 extends AOCDay {
    
    public void solve(File in) {
        inputAsMatrix(in);
        
        int max = bfs(matrix, 'e', 0, 0);
        System.out.println(max);

        for (int i = 0; i < matrix.size(); i++) {
            int left = bfs(matrix, 'e', i, 0);
            if (left > max) max = left;
            int right = bfs(matrix, 'w', i, matrix.get(i).size() - 1);
            if (right > max) max = right;
        }

        for (int j = 0; j < matrix.get(0).size(); j++) {
            int down = bfs(matrix, 's', 0, j);
            if (down > max) max = down;
            int up = bfs(matrix, 'n', matrix.size() - 1, j);
            if (up > max) max = up;
        }

        System.out.println(max);
    }

    private char get(List<List<String>> matrix, int i, int j) {
        if (i >= 0 && i < matrix.size() && j >= 0 && j < matrix.get(i).size()) return matrix.get(i).get(j).charAt(0);
        return 'x';
    }

    private int bfs(List<List<String>> matrix, char startDirection, int startI, int startJ) {
        Map<String, Boolean> visited = new HashMap<>();
        ArrayDeque<String> c = new ArrayDeque<>();
        Map<String, Boolean> stoppers = new HashMap<>();
        c.add(startI+"v"+startJ+"v"+startDirection);
        int res = 0;

        while (!c.isEmpty()) {
            String[] pos = c.removeFirst().split("v");
            
            int i = Integer.parseInt(pos[0]);
            int j = Integer.parseInt(pos[1]);
            char direction = pos[2].charAt(0);

            if (!visited.containsKey(i+"v"+j) && get(matrix, i, j) != 'x') {
                visited.put(i+"v"+j, true);
                res++;
            }
                
            if (get(matrix, i, j) == 'x') continue;
            if (stoppers.containsKey(i+"v"+j+"v"+direction)) continue;
            else stoppers.put(i+"v"+j+"v"+direction, true);

            switch (direction) {
                case 'e':
                    if (get(matrix, i, j) == '.' || get(matrix, i, j) == '-') c.add(i+"v"+(j+1)+"v"+'e');
                    if (get(matrix, i, j) == '|') {
                        c.add((i+1)+"v"+j+"v"+'s');
                        c.add((i-1)+"v"+j+"v"+'n');
                    }
                    if (get(matrix, i, j) == '/') c.add((i-1)+"v"+j+"v"+'n');
                    if (get(matrix, i, j) == '\\') c.add((i+1)+"v"+j+"v"+'s');
                    break;
                case 's':
                    if (get(matrix, i, j) == '.' || get(matrix, i, j) == '|') c.add((i+1)+"v"+j+"v"+'s');
                    if (get(matrix, i, j) == '-') {
                        c.add(i+"v"+(j-1)+"v"+'w');
                        c.add(i+"v"+(j+1)+"v"+'e');
                    }
                    if (get(matrix, i, j) == '/') c.add(i+"v"+(j-1)+"v"+'w');
                    if (get(matrix, i, j) == '\\') c.add(i+"v"+(j+1)+"v"+'e');
                    break;
                case 'w':
                    if (get(matrix, i, j) == '.' || get(matrix, i, j) == '-') c.add(i+"v"+(j-1)+"v"+'w');
                    if (get(matrix, i, j) == '|') {
                        c.add((i+1)+"v"+j+"v"+'s');
                        c.add((i-1)+"v"+j+"v"+'n');
                    }
                    if (get(matrix, i, j) == '/') c.add((i+1)+"v"+j+"v"+'s');
                    if (get(matrix, i, j) == '\\') c.add((i-1)+"v"+j+"v"+'n');
                    break;
                case 'n':
                    if (get(matrix, i, j) == '.' || get(matrix, i, j) == '|') c.add((i-1)+"v"+j+"v"+'n');
                    if (get(matrix, i, j) == '-') {
                        c.add(i+"v"+(j-1)+"v"+'w');
                        c.add(i+"v"+(j+1)+"v"+'e');
                    }
                    if (get(matrix, i, j) == '/') c.add(i+"v"+(j+1)+"v"+'e');
                    if (get(matrix, i, j) == '\\') c.add(i+"v"+(j-1)+"v"+'w');
                    break;
            }
        }

        return res;
    }
}
