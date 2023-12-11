import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends AOCDay {
    
    public void solve (File in) {
        inputAsMatrix(in);

        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            boolean toAdd = true;
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("#")) toAdd = false;
            }
            if (toAdd) rows.add(i);
        }

        int counter = 1;
        for (int j = 0; j < matrix.get(0).size(); j++) {
            boolean toAdd = true;
            for (int i = 0; i < matrix.size(); i++) {
                if (matrix.get(i).get(j).equals("#")) {
                    matrix.get(i).set(j, String.valueOf(counter));
                    counter++;
                    toAdd = false;
                }
            }
            if (toAdd) cols.add(j);
        }

        Map<String, List<Integer>> m = new HashMap<>();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (!matrix.get(i).get(j).equals(".")) {
                    m.put(matrix.get(i).get(j), List.of(i, j));
                }
            }
        }

        long distance = 0;
        for (int i = 1; i < counter - 1; i++) {
            List<Integer> coordinatesA = m.get(String.valueOf(i));
            for (int j = i+1; j < counter; j++) {
                List<Integer> coordinatesB = m.get(String.valueOf(j));
                if (coordinatesB.get(0) > coordinatesA.get(0)) {
                    distance += (coordinatesB.get(0) - coordinatesA.get(0));
                } else {
                    distance += (coordinatesA.get(0) - coordinatesB.get(0));
                }

                if (coordinatesB.get(1) > coordinatesA.get(1)) {
                    distance += (coordinatesB.get(1) - coordinatesA.get(1));
                } else {
                    distance += (coordinatesA.get(1) - coordinatesB.get(1));
                }

                for (int k = 0; k < rows.size(); k++) {
                    if (isIn(rows.get(k), coordinatesA.get(0), coordinatesB.get(0))) distance += 999999;
                }

                for (int k = 0; k < cols.size(); k++) {
                    if (isIn(cols.get(k), coordinatesA.get(1), coordinatesB.get(1))) distance += 999999;
                }
            }
        }

        System.out.println(distance);
    }

    public boolean isIn(int x, int y, int z) {
        if (x > y && x < z) return true;
        if (x > z && x < y) return true;

        return false;
    }
}
