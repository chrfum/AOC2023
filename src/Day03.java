import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Day03 extends AOCDay {

    public void solve(File in) {
        inputAsMatrix(in);
        int res1 = 0;
        int res2 = 0;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(j).equals("*")) {
                    List<Integer> lst = searchInts(i, j, matrix);
                    lst.addAll(searchInts(i+1, j, matrix));
                    lst.addAll(searchInts(i-1, j, matrix));
                    lst = clean(lst);
                    if (lst.size() == 2) res2 += lst.get(0) * lst.get(1);
                }
                if (isChar(matrix.get(i).get(j))) {
                    List<Integer> lst = searchInts(i, j, matrix);
                    lst.addAll(searchInts(i+1, j, matrix));
                    lst.addAll(searchInts(i-1, j, matrix));
                    res1 += sum(lst);
                }
            }
        }
        System.out.println(res1);
        System.out.println(res2);
    }

    private int sum(List<Integer> lst) {
        int res = 0;
        for (Integer i : lst) res += i;
        return res;
    }

    private List<Integer> clean(List<Integer> lst) {
        List<Integer> res = new ArrayList<>();
        for (Integer i: lst) {
            if (i != 0) res.add(i);
        }
        return res;
    }

    private boolean isChar(String s) {
        if ("0123456789.".contains(s)) return false;
        return true; 
    }

    private boolean isInt(String s) {
        if ("0123456789".contains(s)) return true;
        return false; 
    }

    private String get(int i, int j, List<List<String>> matrix) {
        if (i > -1 && i < matrix.size() && j > -1 && j <matrix.size()) return matrix.get(i).get(j);
        return ".";
    }

    private Integer searchInt(int i, int j, List<List<String>> matrix) {
        String s = "";
        if (isInt(get(i, j, matrix))) {
            s = get(i, j, matrix);
            int x = j;
            while (isInt(get(i, x-1, matrix))) {
                s = matrix.get(i).get(x-1) + s;
                x--; 
            }
            x = j;
            while (isInt(get(i, x+1, matrix))) {
                s = s + matrix.get(i).get(x+1);
                x++;
            }
            return Integer.parseInt(s);
        }
        return 0;
    }

    private List<Integer> searchInts(int i, int j, List<List<String>> matrix) {
        List<Integer> res = new ArrayList<>();
        Integer x = searchInt(i, j, matrix);
        if (x == 0) {
            res.add(searchInt(i, j-1, matrix));
            res.add(searchInt(i, j+1, matrix));
        } else {
            res.add(x);
        }
        return res;
    }
}
