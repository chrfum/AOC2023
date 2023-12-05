import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day05 extends AOCDay {
    public void solve(File in) {
        inputAsList(in);
        boolean first = true;
        boolean mapping = false;
        List<Boolean> mapped = new ArrayList<>();
        List<List<Long>> seeds = new ArrayList<>();
        List<List<Long>> newSeeds = new ArrayList<>();

        for (String line : input) {
            Scanner sc = new Scanner(line);

            if (first) {
                sc.next();
                while(sc.hasNextLong()) {
                    long start = sc.nextLong();
                    long dist = sc.nextLong();
                    newSeeds.add(new ArrayList<>(List.of(start, start+dist-1)));
                    mapped.add(true);
                }
                first = false;
                
                continue;
            }

            if (!sc.hasNextLong()) {
                if (!sc.hasNext()) {
                    mapping = false;
                } else {
                    for (int i = 0; i < mapped.size(); i++) {
                        if (!mapped.get(i) && !(newSeeds.get(i).get(0) == 0)) newSeeds.add(seeds.get(i));
                    }
                    seeds = new ArrayList<>(newSeeds);
                    mapped = new ArrayList<>();
                    for (int i = 0; i < seeds.size(); i++) mapped.add(false);
                    newSeeds = new ArrayList<>();
                    mapping = false;
                }
            } else {
                mapping = true;
            }

            if (mapping) {
                long dst = sc.nextLong();
                long src = sc.nextLong();
                long dist = sc.nextLong();
                long diff = src - dst;
                long mapEnd = src + dist - 1;

                for (int i = 0; i < seeds.size(); i++) {
                    long start = seeds.get(i).get(0);
                    long end = seeds.get(i).get(1);
                    if (isIn(src, start, end)) {
                        mapped.set(i, true);
                        if (end <= mapEnd) {
                            newSeeds.add(List.of(dst, end - diff));
                            seeds.set(i, List.of(start, src - 1));
                            mapped.set(i, false);
                        } else {
                            newSeeds.add(List.of(dst, dst + dist - 1));
                            seeds.add(List.of(mapEnd + 1, end));
                            mapped.add(false);
                        }
                    } else if (isIn(start, src, mapEnd)) {
                        mapped.set(i, true);
                        if (end <= src + dist) newSeeds.add(List.of(dst + (start - src), dst + (end - src)));
                        else {
                            newSeeds.add(List.of(dst + (start - src), mapEnd - diff));
                            seeds.add(List.of(src + dist, end));
                            mapped.add(false);
                        }
                    }
                }
            }
            sc.close(); 
        }
        for (int i = 0; i < mapped.size(); i++) {
            if (!mapped.get(i)) newSeeds.add(seeds.get(i));
        }
        System.out.println(min(newSeeds));
    }

    private long min(List<List<Long>> lst) {
        long min = lst.get(0).get(0);
        for (int i = 1; i < lst.size(); i++) {
            if (lst.get(i).get(0) == 0) continue;
            if (lst.get(i).get(0) < min) min = lst.get(i).get(0);
        }
        return min;
    }

    private boolean isIn(long src, long s, long d) {
        if (src >= s && src <= d) return true;
        return false;
    }
}
