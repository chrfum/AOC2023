import java.io.File;
import java.util.Scanner;

public class Day06 extends AOCDay {

    public void solve(File in) {
        inputAsList(in);

        Scanner sc1 = new Scanner(input.get(0));
        Scanner sc2 = new Scanner(input.get(1));

        sc1.next();
        sc2.next();
        String ms = "";
        String dist = "";
        while (sc1.hasNext()) {
            ms += sc1.next();
            dist += sc2.next();
        }

        sc1.close();
        sc2.close();

        long milliseconds = Long.parseLong(ms);
        long record = Long.parseLong(dist);
        long myDistance = 0;
        long winningBoat = 0;

        for (long i = 1; i <= milliseconds; i++) {
            long velocity = i;
            myDistance = velocity * (milliseconds - i);
            if (myDistance > record) winningBoat++;
            myDistance = 0;
        }

        System.out.println(winningBoat);
    }
}
