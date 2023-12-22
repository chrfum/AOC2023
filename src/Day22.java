import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 extends AOCDay {
    
    public void solve(File in) {
        inputAsList(in);
        List<List<Integer>> bricks = new ArrayList<>();
        int counter = 0;
        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;

        for (String line : input) {
            bricks.add(new ArrayList<>());
            String[] coordinates = line.split("~");
            String[] start = coordinates[0].split(",");
            String[] end = coordinates[1].split(",");
            int startX = Integer.parseInt(start[0]);
            int startY = Integer.parseInt(start[1]);
            int startZ = Integer.parseInt(start[2]);
            int endX = Integer.parseInt(end[0]);
            int endY = Integer.parseInt(end[1]);
            int endZ = Integer.parseInt(end[2]);

            if (startX > maxX) maxX = startX;
            if (startX < minX) minX = startX;
            if (startY > maxY) maxY = startY;
            if (startY < minY) minY = startY;
            if (endX > maxX) maxX = endX;
            if (endX < minX) minX = endX;
            if (endY > maxY) maxY = endY;
            if (endY < minY) minY = endY;
            bricks.get(counter).addAll(List.of(startX, startY, startZ, endX, endY, endZ));
            counter++;
        }

        sort(bricks);

        Map<List<Integer>, Integer> heightMap = new HashMap<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                heightMap.put(List.of(x, y, 0), -1);
            }
        }

        Map<Integer, List<Integer>> supports = new HashMap<>();
        Map<Integer, List<Integer>> supportedBy = new HashMap<>();
        fall(bricks, heightMap, supports);

        for (int i = 0; i < bricks.size(); i++) {
            supportedBy.put(i, new ArrayList<>());
            for (Map.Entry<Integer, List<Integer>> e : supports.entrySet()) {
                if (e.getValue().contains(i)) supportedBy.get(i).add(e.getKey());
            }
        }

        int res1 = 0;
        int res2 = 0;
        for (int i = 0; i < bricks.size(); i++) {
            boolean toAdd = true;
            List<Integer> lst = supports.get(i);
            if (lst != null) {
                for (Integer x : supports.get(i)) {
                    counter = 0;
                    for (Map.Entry<Integer, List<Integer>> e : supports.entrySet()) {
                        if (e.getValue().contains(x)) counter++;
                    }
                    if (counter < 2) toAdd = false;
                }
            }
            
            if (toAdd) res1++;
            else {
                ArrayDeque<Integer> fallingBricks = new ArrayDeque<>();
                fallingBricks.add(i);
                Map<Integer, List<Integer>> tempSupportedBy = new HashMap<>(supportedBy);

                while (!fallingBricks.isEmpty()) {
                    int fallingBrick = fallingBricks.removeFirst();

                    if (supports.get(fallingBrick) != null) {
                        for (int j = 0; j < supports.get(fallingBrick).size(); j++) {

                            int current = supports.get(fallingBrick).get(j);
                            List<Integer> tempLst = new ArrayList<>(tempSupportedBy.get(current));
                            tempSupportedBy.put(current, tempLst);
                            tempSupportedBy.get(current).remove(tempSupportedBy.get(current).indexOf(fallingBrick));

                            if (tempSupportedBy.get(current).size() == 0) {
                                fallingBricks.add(current);
                                res2++;
                            }
                        }
                    }  
                }
            }
        }

        System.out.println(res1);
        System.out.println(res2);
    }

    public void sort(List<List<Integer>> l) {
        for (int i = 0; i < l.size()-1; i++) {
            int min = Math.min(l.get(i).get(2), l.get(i).get(5));
            int idxMin = i;
            for (int j = i; j < l.size(); j++) {
                int curr = Math.min(l.get(j).get(2), l.get(j).get(5));
                if (curr < min) {
                    min = curr;
                    idxMin = j;
                }
            }
            List<Integer> temp = l.get(i);
            l.set(i, l.get(idxMin));
            l.set(idxMin, temp);
        }

        return;
    }

    private void fall(List<List<Integer>> bricks, Map<List<Integer>, Integer> heightMap, Map<Integer, List<Integer>> supports) {
    
        for (int i = 0; i < bricks.size(); i++) {
            
            int startX = bricks.get(i).get(0);
            int endX = bricks.get(i).get(3);
            int startY = bricks.get(i).get(1);
            int endY = bricks.get(i).get(4);
            int startZ = bricks.get(i).get(2);
            int endZ = bricks.get(i).get(5);
            
            while (nothingUnder(bricks.get(i), heightMap, supports, i)) {
                startZ--;
                endZ--;
                bricks.set(i, List.of(startX, startY, startZ, endX, endY, endZ));
            }

            if (xBrick(startX, endX)) {
                for (int x = startX; x <= endX; x++) {
                    heightMap.put(List.of(x, startY, startZ), i);
                }
            } else if (yBrick(startY, endY)) {
                for (int y = startY; y <= endY; y++) {
                    heightMap.put(List.of(startX, y, startZ), i);
                }
            } else {
                for (int z = startZ; z <= endZ; z++) {
                    heightMap.put(List.of(startX, startY, z), i);
                }
            }   
        }

        return;
    }

    private boolean nothingUnder(List<Integer> brick, Map<List<Integer>, Integer> heightMap, Map<Integer, List<Integer>> supports, int currentBrick) {
        boolean fall = true;

        for (int x = brick.get(0); x <= brick.get(3); x++) {
            if (heightMap.containsKey(List.of(x, brick.get(1), brick.get(2)-1))) {
                int n = heightMap.get(List.of(x, brick.get(1), brick.get(2)-1));
                if (supports.containsKey(n)) {
                    List<Integer> l = supports.get(n);
                    if (!l.contains(currentBrick)) supports.get(n).add(currentBrick);
                } else {
                    supports.put(n, new ArrayList<>());
                    supports.get(n).add(currentBrick);
                }
                fall = false;
            }
        }
        
        for (int y = brick.get(1); y <= brick.get(4); y++) {
            if (heightMap.containsKey(List.of(brick.get(0), y, brick.get(2) - 1))) {
                int n = heightMap.get(List.of(brick.get(0), y, brick.get(2) - 1));
                if (supports.containsKey(n)) {
                    List<Integer> l = supports.get(n);
                    if (!l.contains(currentBrick)) supports.get(n).add(currentBrick);
                } else {
                    supports.put(n, new ArrayList<>());
                    supports.get(n).add(currentBrick);
                }
                fall = false;
            }
        }

        return fall;
    }

    private boolean xBrick(int startX, int endX) {
        return (startX != endX);
    }

    private boolean yBrick(int startY, int endY) {
        return (startY != endY);
    }
}
