import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day02 extends AOCDay {
    public void solve(File in) {
        inputAsList(in);
        int res = 0;
        for (String line : input) {
            Map<String, Integer> cubes = new HashMap<>()
            {{
                put("red", 0);
                put("green", 0);
                put("blue", 0);
            }};
            Map<String, Integer> maxCubes = new HashMap<>()
            {{
                put("red", 0);
                put("green", 0);
                put("blue", 0);
            }};
            boolean first = true;
            for (String s : line.split(";")) {
                Scanner sc = new Scanner(s);
                if (first) {
                    first = false;
                    sc.next();
                    sc.next();
                }
                while (sc.hasNext()) {
                    Integer n = Integer.parseInt(sc.next());
                    String color = sc.next();
                    if (sc.hasNext()) color = color.substring(0, color.length() -1);
                    cubes.put(color, cubes.get(color) + n);
                }
                sc.close();
                if (cubes.get("red") > maxCubes.get("red")) maxCubes.put("red", cubes.get("red"));
                if (cubes.get("green") > maxCubes.get("green")) maxCubes.put("green", cubes.get("green"));
                if (cubes.get("blue") > maxCubes.get("blue")) maxCubes.put("blue", cubes.get("blue"));
                cubes.put("red", 0);
                cubes.put("green", 0);
                cubes.put("blue", 0);
            }
            int max = maxCubes.get("red") * maxCubes.get("green") * maxCubes.get("blue");
            res += max;
            maxCubes.put("red", 0);
            maxCubes.put("green", 0);
            maxCubes.put("blue", 0);
        }
        System.out.println(res);
    }
}