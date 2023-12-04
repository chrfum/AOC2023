import java.io.File;

public class Day01 extends AOCDay {
    public void solve(File in) {
        inputAsList(in);
        int res = 0;
        for (String line : input) {
            String ints = "";
            line = line.replaceAll("one", "o1e");
            line = line.replaceAll("two", "t2o");
            line = line.replaceAll("three", "t3e");
            line = line.replaceAll("four", "f4r");
            line = line.replaceAll("five", "f5e");
            line = line.replaceAll("six", "s6x");
            line = line.replaceAll("seven", "s7n");
            line = line.replaceAll("eight", "e8t");
            line = line.replaceAll("nine", "n9e");
            for (Character c : line.toCharArray()) {
                String current = Character.toString(c);
                if ("0123456789".contains(current)) ints += current;
            }
            res += (ints.charAt(0) - '0') * 10 + (ints.charAt(ints.length() - 1) - '0');
        }
        System.out.println(res);
    }
}
