import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day09 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);
        int res1 = 0;
        int res2 = 0;

        for (String line : input) {
            Scanner sc = new Scanner(line);
            
            List<List<Integer>> seq = new ArrayList<>();
            seq.add(new ArrayList<>());
            while (sc.hasNextInt()) {
                seq.get(0).add(sc.nextInt());
            }
            sc.close();

            boolean increased = true;
            int counter = 0;
            while (increased) {
                seq.add(new ArrayList<>());
                increased = false;
                for (int i = 1; i < seq.get(counter).size(); i++) {
                    int prec = seq.get(counter).get(i-1);
                    int curr = seq.get(counter).get(i);
                    if (prec != curr) increased = true;
                    seq.get(counter+1).add(curr - prec);
                }
                counter++;
            }
            
            int last = 0;
            int first = 0;
            for (int i = seq.size() - 1; i >= 0; i--) {
                int lastIdx = seq.get(i).size() - 1;
                last = seq.get(i).get(lastIdx) + last;
                if (i == 0) res1 += last;

                first = seq.get(i).get(0) - first;
                if (i == 0) res2 += first;
            }
        }
        System.out.println(res1);
        System.out.println(res2);
    }
}
