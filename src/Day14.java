import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends AOCDay {
    
    public void solve(File in) {
        inputAsMatrix(in);

        List<Integer> loads = new ArrayList<>();
        Map<String, Integer> cicli = new HashMap<>();

        for (int i = 0; i < 1000000000; i++) {
            moveNorth(matrix);
            if (i == 0) System.out.println(calculateLoad(matrix));
            moveWest(matrix);
            moveSouth(matrix);
            moveEast(matrix);
            if (loads.contains(calculateLoad(matrix))) {
                if (cicli.containsKey(matrix.toString())) {
                    int prec = cicli.get(matrix.toString());
                    int skip = (1000000000 - i) / (i - prec);
                    i += (i - prec) * skip;
                }
                cicli.put(matrix.toString(), i);
            } else loads.add(calculateLoad(matrix));
        }
       
        System.out.println(calculateLoad(matrix));
    }

    private int calculateLoad(List<List<String>> matrix) {
        int res = 0;
        for (int i = 0; i < matrix.size(); i++) {
            int n = count(matrix.get(i));
            res += (n * (matrix.size() - i));
        }
        return res;
    }

    private int count(List<String> lst) {
        int counter = 0;
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).equals("O")) counter++;
        }
        return counter;
    }

    private void moveNorth(List<List<String>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("O")) {
                    int k = i-1;
                    while (k >= 0 && matrix.get(k).get(j).equals(".")) {
                        matrix.get(k+1).set(j, ".");
                        matrix.get(k).set(j, "O");
                        k--;
                    }
                } 
            }
        }
    }

    private void moveWest(List<List<String>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("O")) {
                    int k = j-1;
                    while (k >= 0 && matrix.get(i).get(k).equals(".")) {
                        matrix.get(i).set(k+1, ".");
                        matrix.get(i).set(k, "O");
                        k--;
                    }
                }
            }
        }
    }

    private void moveSouth(List<List<String>> matrix) {
        for (int i = matrix.size() - 1; i >= 0; i--) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("O")) {
                    int k = i+1;
                    while (k < matrix.size() && matrix.get(k).get(j).equals(".")) {
                        matrix.get(k-1).set(j, ".");
                        matrix.get(k).set(j, "O");
                        k++;
                    }
                }
            }
        }
    }

    private void moveEast(List<List<String>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = matrix.get(i).size() - 1; j >= 0; j--) {
                if (matrix.get(i).get(j).equals("O")) {
                    int k = j+1;
                    while (k < matrix.get(i).size() && matrix.get(i).get(k).equals(".")) {
                        matrix.get(i).set(k-1, ".");
                        matrix.get(i).set(k, "O");
                        k++;
                    }
                }
            }
        }
    }
}
