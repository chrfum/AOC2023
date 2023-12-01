import java.io.File;

public class Day01 extends AOCDay {
    public void part1(File in) {
        inputAsList(in);
        int res = 0;
        for (String line : input) {
            String ints = "";
            line = line.replaceAll("one", "o1e");
            line = line.replaceAll("two", "t2");
            line = line.replaceAll("three", "t3");
            line = line.replaceAll("four", "4");
            line = line.replaceAll("five", "5e");
            line = line.replaceAll("six", "6");
            line = line.replaceAll("seven", "7n");
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
