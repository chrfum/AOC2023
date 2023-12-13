import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Day13 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);
        boolean newBlock = true;
        List<String> block = new ArrayList<>();
        int res1 = 0;
        int res2 = 0;

        for (String line : input) {
            if (line.isEmpty()) {
                res1 += findReflection1(block, 100);
                res2 += findReflection2(block, 100);
                
                block = new ArrayList<>();
                newBlock = true;
                continue;
            }

            if (newBlock) block.add(line);
        }

        res1 += findReflection1(block, 100);
        res2 += findReflection2(block, 100);
        System.out.println(res1);
        System.out.println(res2);
    }

    private int findReflection1(List<String> block, int x) {
        for (int i = 1; i < block.size(); i++) {
            int j = 0;
            boolean isReflection = true;
            while (i + j < block.size() && i - j > 0) {
                if (!block.get(i + j).equals(block.get(i-1-j))) isReflection = false;
                j++;
            }
            if (isReflection) return (i * x);
        }

        List<String> newBlock = new ArrayList<>();
        for (int j = 0; j < block.get(0).length(); j++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < block.size(); i++) sb.append(block.get(i).charAt(j));
            newBlock.add(sb.toString());
        }

        return findReflection1(newBlock, 1);
    }

    private int findReflection2(List<String> block, int x) {
        for (int i = 1; i < block.size(); i++) {
            int j = 0;
            int counter = 0;
            while (i + j < block.size() && i - j > 0) {
                counter += compare(block.get(i+j), block.get(i-1-j));
                j++;
            }
            if (counter == 1) return (i * x);
        }

        List<String> newBlock = new ArrayList<>();
        for (int j = 0; j < block.get(0).length(); j++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < block.size(); i++) sb.append(block.get(i).charAt(j));
            newBlock.add(sb.toString());
        }

        return findReflection2(newBlock, 1);
    }

    private int compare (String s0, String s1) {
        int counter = 0;
        for (int i = 0; i < s0.length(); i++) {
            if (s0.charAt(i) != s1.charAt(i)) counter++;
        }
        return counter;
    }
}
