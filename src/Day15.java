import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day15 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);

        int res1 = 0;
        String line = input.get(0).replaceAll(",", " ");
        Scanner sc = new Scanner(line);

        while (sc.hasNext()) {
            String token = sc.next();
            int curr = 0;
            for (Character c : token.toCharArray()) {
                curr += c;
                curr *= 17;
                curr %= 256;
            }
            res1 += curr;
        }

        sc.close();
        System.out.println(res1);

        Scanner sc2 = new Scanner(line);
        Map<Integer, List<String>> boxes = new HashMap<>();
        Map<String, Integer> focalLengths = new HashMap<>();

        while (sc2.hasNext()) {
            String token = sc2.next();
            int boxNumber = 0;
            String lens = ""; 
            List<String> allLens = new LinkedList<>();
            
            for (Character c : token.toCharArray()) {
                if (c == '-') {
                    if (boxes.containsKey(boxNumber)) {
                        allLens = boxes.get(boxNumber);
                        allLens.remove(lens);
                    }
                } else if (c == '=') {
                    if (boxes.containsKey(boxNumber)) {
                        allLens = boxes.get(boxNumber);
                        if (!allLens.contains(lens)) allLens.add(lens);
                        focalLengths.put(lens, Integer.parseInt(token.substring(token.length() - 1)));
                    } else {
                        allLens.add(lens);
                        boxes.put(boxNumber, allLens);
                        focalLengths.put(lens, Integer.parseInt(token.substring(token.length() - 1)));
                    }
                    break;
                } else {
                    lens += c;
                    boxNumber += c;
                    boxNumber *= 17;
                    boxNumber %= 256;
                }
            }
        }

        sc2.close();
        int res2 = 0;

        for (Map.Entry<Integer, List<String>> e : boxes.entrySet()) {
            int boxNumber = e.getKey() + 1;
            int slotNumber = 1;
            for (String s : e.getValue()) {
                res2 += boxNumber * slotNumber * focalLengths.get(s);
                slotNumber++;
            }
        }

        System.out.println(res2);
    }
}
