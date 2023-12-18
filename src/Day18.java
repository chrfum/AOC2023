import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day18 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);

        List<List<Long>> vertices =  new ArrayList<>();
        long x = 0;
        long y = 0;

        long plus = 0;

        for (String line : input) {
            Scanner sc = new Scanner(line);
            char direction = sc.next().charAt(0);
            long n = sc.nextLong();
            
            String s = sc.next();
            sc.close();
            String hex = s.substring(2, s.length()-2);
            
            direction = s.charAt(s.length()-2);
            long decimal = Long.parseLong(hex,16); 

            n = decimal;
            plus += n;
            switch (direction) {
                case '0':
                    y += n;
                    vertices.add(List.of(x, y));
                    break;
                case '2':
                    y -= n;
                    vertices.add(List.of(x, y));
                    break;
                case '3':
                    x -= n;
                    vertices.add(List.of(x, y));
                    break;
                case '1':
                    x += n;
                    vertices.add(List.of(x, y));
                    break;
            }
        }

        long area = 0;
        for (int i = 0; i < vertices.size(); i++) {
            int j = (i + 1) % vertices.size();
            area += vertices.get(i).get(0) * vertices.get(j).get(1);
            area -= vertices.get(j).get(0) * vertices.get(i).get(1);
        }

        area = Math.abs(area) / 2;
        System.out.println(area + (plus/2) + 1);
    }
}
