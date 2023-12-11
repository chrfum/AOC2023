import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 extends AOCDay {
    
    public void solve(File in) {
        inputAsMatrix(in);
        int dist = 0;
        Map<String, Boolean> visited = new HashMap<>();
        boolean toCount = false;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("S")) {
                    visited.put(i + "-" + j, true);
                    dist++;
                    String next = "";
                    if (get(matrix, i-1, j).equals("|") || get(matrix, i-1, j).equals("L") || get(matrix, i-1, j).equals("J")) {
                        next = dfs(i-1, j, matrix, visited);
                        toCount = true;
                    } else if (get(matrix, i+1, j).equals("|") || get(matrix, i+1, j).equals("F") || get(matrix, i+1, j).equals("7")) {
                        next = dfs(i+1, j, matrix, visited);
                    } else if (get(matrix, i, j+1).equals("-") || get(matrix, i, j+1).equals("J") || get(matrix, i, j+1).equals("7")) {
                        next = dfs(i, j+1, matrix, visited);
                    }
                    
                    dist++;
                    String[] idxs = next.split("-");
                    int nextI = Integer.parseInt(idxs[0]);
                    int nextJ = Integer.parseInt(idxs[1]);
                    while (!visited.containsKey(nextI+"-"+nextJ)) {
                        next = dfs(nextI, nextJ, matrix, visited);
                        idxs = next.split("-");
                        nextI = Integer.parseInt(idxs[0]);
                        nextJ = Integer.parseInt(idxs[1]);
                        dist++;
                    }
                }
            }
        }

        dist /= 2;
        System.out.println(dist);

        int res = 0;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (!visited.containsKey(i+"-"+j)) {
                    int counter = 0;
                    for (int k = j; k >= 0; k--) {
                        if (visited.containsKey(i+"-"+k)) {
                            if (get(matrix, i, k).equals("|")
                                || get(matrix, i, k).equals("L") 
                                || get(matrix, i, k).equals("J")) counter++;
                            else if (toCount && get(matrix, i, k).equals("S")) counter++;
                        } 
                    }
                    if (counter % 2 == 1) res++;
                }
            }
        }
        System.out.println(res);
    }

    private String dfs(int i, int j, List<List<String>> matrix, Map<String, Boolean> visited) {
        visited.put(i + "-" + j, true);

        switch (get(matrix, i, j)) {
            case "|":
                if (visited.containsKey((i+1)+"-"+j)) return (i-1)+"-"+j;
                else return (i+1)+"-"+j;
            case "-":
                if (visited.containsKey(i+"-"+(j+1))) return i+"-"+(j-1);
                else return i+"-"+(j+1);
            case "J":
                if (visited.containsKey((i-1)+"-"+j)) return i+"-"+(j-1);
                else return (i-1)+"-"+j;
            case "7":
                if (visited.containsKey((i+1)+"-"+j)) return i+"-"+(j-1);
                else return (i+1)+"-"+j;
            case "F":
                if (visited.containsKey((i+1)+"-"+j)) return i+"-"+(j+1);
                else return (i+1)+"-"+j;
            case "L":
                if (visited.containsKey((i-1)+"-"+j)) return i+"-"+(j+1);
                else return (i-1)+"-"+j;
            default:
                break;
        }

        return "";
    }

    private String get(List<List<String>> matrix, int i, int j) {
        if (i >= 0 && i < matrix.size() && j >= 0 && j < matrix.get(i).size()) return matrix.get(i).get(j);
        return ".";
    }


}
