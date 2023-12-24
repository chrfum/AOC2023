import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day24 extends AOCDay {
    
    private class Hailstone {
        long x, y, z, vx, vy, vz;
        double a, b, c;
    
        private Hailstone(String posVel) {
            Scanner sc =  new Scanner(posVel.replaceAll("[,@]", " "));
            x = sc.nextLong();
            y = sc.nextLong();
            z = sc.nextLong();
            vx = sc.nextLong();
            vy = sc.nextLong();
            vz = sc.nextLong();

            a = vy;
            b = -vx;
            c = vy * x - vx * y;
        }
    }

    public void solve(File in) {
        inputAsList(in);

        List<Hailstone> hailstones = new ArrayList<>();
        int res = 0;

        for (String line : input) hailstones.add(new Hailstone(line));
            
        
        for (int i = 0; i < hailstones.size() - 1; i++) {
            for (int j = i+1; j < hailstones.size(); j++) {
                Hailstone a = hailstones.get(i);
                Hailstone b = hailstones.get(j);

                if (a.a * b.b == a.b * b.a) continue;

                double x = (a.c * b.b - b.c * a.b) / (a.a * b.b - b.a * a.b);
                double y = (b.c * a.a - a.c * b.a) / (a.a * b.b - b.a * a.b);

                if (x >= 200000000000000L && x <= 400000000000000L && y >= 200000000000000L && y <= 400000000000000L) {
                    if ((x - a.x) * a.vx >= 0 && (y - a.y) * a.vy >= 0) {
                        if ((x - b.x) * b.vx >= 0 && (y - b.y) * b.vy >= 0) res++;
                    }
                } 
            }
        }

        System.out.println(res);
    }
}
