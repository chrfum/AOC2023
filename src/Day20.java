import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day20 extends AOCDay {

    private interface Module {

        void addModule(String m);

        void sendPulse(ArrayDeque<List<String>> c, boolean isHigh, String from);

        List<String> nexts();
    }

    private class Broadcaster implements Module {
        List<String> next = new ArrayList<>();
        String name = "broadcaster";

        @Override
        public void addModule(String m) {
            next.add(m);
        }

        @Override
        public void sendPulse(ArrayDeque<List<String>> c, boolean isHigh, String from) {
            for (int i = 0; i < next.size(); i++) {
                c.add(List.of(next.get(i).toString(), name, (isHigh) ? "high" : "low"));
            }
        }

        @Override
        public List<String> nexts() {
            return next;
        }

        @Override
        public String toString() {
            return name + " " + next.toString();
        }
    }

    private class FlipFlop implements Module {
        List<String> next;
        boolean isOn;
        String name;

        private FlipFlop(String name) {
            isOn = false;
            this.name = name;
            next = new ArrayList<>();
        }

        @Override
        public void addModule(String m) {
            next.add(m);
        }

        @Override
        public void sendPulse(ArrayDeque<List<String>> c, boolean isHigh, String from) {
            if (!isHigh) {
                isOn = !isOn;
                if (isOn) {
                    for (int i = 0; i < next.size(); i++) {
                        c.add(List.of(next.get(i), name, "high"));
                    }
                } else {
                    for (int i = 0; i < next.size(); i++) {
                        c.add(List.of(next.get(i), name, "low"));
                    }
                }
            }
        }

        @Override
        public List<String> nexts() {
            return next;
        }

        @Override
        public String toString() {
            return name + " " + next.toString();
        }
    }

    private class Conjunction implements Module {
        List<String> next;
        String name;
        List<String> mem;
        List<String> prec;

        private Conjunction(String name) {
            next = new ArrayList<>();
            this.name = name;
            mem = new ArrayList<>();
            prec = new ArrayList<>();
        }

        @Override
        public void addModule(String m) {
            next.add(m);
        }

        public void addPrec(String m) {
            prec.add(m);
            mem.add("low");
        }

        @Override
        public void sendPulse(ArrayDeque<List<String>> c, boolean isHigh, String from) {
            int idx = prec.indexOf(from);
            if (isHigh) mem.set(idx, "high");
            else mem.set(idx, "low");

            boolean allHigh = true;
            for (int i = 0; i < mem.size(); i++) {
                if (mem.get(i).equals("low")) allHigh = false;
            }

            
            for (int i = 0; i < next.size(); i++) {
                c.add(List.of(next.get(i), name, (allHigh) ? "low" : "high"));
            }
        }

        @Override
        public List<String> nexts() {
            return next;
        }

        @Override
        public String toString() {
            return name + " " + next.toString() + " prec: " + prec.toString() + "";
        }
    }
    
    public void solve(File in) {
        inputAsList(in);
        
        Map<String, Module> modules = new HashMap<>();
        ArrayDeque<List<String>> buttons = new ArrayDeque<>();
        Map<String, Conjunction> conjs = new HashMap<>();
        String precToRx = "";
        
        for (String line : input) {
            Scanner sc = new Scanner(line);
            String name = sc.next();

            if (name.equals("broadcaster")) {
                Broadcaster broadcaster = new Broadcaster();
                modules.put("broadcaster", broadcaster);
                sc.next();
                while (sc.hasNext()) {
                    String next = sc.next();
                    if (next.contains(",")) next = next.substring(0, next.length() - 1);
                    broadcaster.addModule(next);
                }
            }

            if (name.startsWith("%")) {
                FlipFlop ff  = new FlipFlop(name.substring(1));
                modules.put(name.substring(1), ff);
                sc.next();
                while (sc.hasNext()) {
                    String next = sc.next();
                    if (next.contains(",")) next = next.substring(0, next.length() - 1);
                    ff.addModule(next);
                }
            }

            if (name.startsWith("&")) {
                Conjunction conj = new Conjunction(name.substring(1));
                modules.put(name.substring(1), conj);
                conjs.put(name.substring(1), conj);
                sc.next();
                while (sc.hasNext()) {
                    String next = sc.next();
                    if (next.contains(",")) next = next.substring(0, next.length() - 1);
                    conj.addModule(next);
                    if (next.equals("rx")) precToRx = name.substring(1);
                }
            }

            sc.close();
        }

        Map<String, Integer> modulesNearRx = new HashMap<>();

        for (Map.Entry<String, Module> m : modules.entrySet()) {
            List<String> next = m.getValue().nexts();
            for (int i = 0; i < next.size(); i++) {
                if (conjs.containsKey(next.get(i))) {
                    conjs.get(next.get(i)).addPrec(m.getKey());
                    if (next.get(i).equals(precToRx)) modulesNearRx.put(m.getKey(), 0);
                }
            }
        }
        
        int counterHigh = 0;
        int counterLow = 0;
        int counter = 0;

        while (notSetted(modulesNearRx)) {
            counter++;
            
            buttons.add(List.of("broadcaster", "button", "low"));
            while (!buttons.isEmpty()) {
                List<String> button = buttons.removeFirst();
                if (modulesNearRx.containsKey(button.get(1)) && modulesNearRx.get(button.get(1)) == 0) {
                    if (button.get(2).equals("high")) modulesNearRx.put(button.get(1), counter);
                }
                
                if (button.get(2).equals("high")) counterHigh++;
                else counterLow++;

                if (modules.containsKey(button.get(0))) {
                    Module current = modules.get(button.get(0));
                    String from = button.get(1);
                    boolean isHigh = false;
                    if (button.get(2).equals("high")) isHigh = true;
                    current.sendPulse(buttons, isHigh, from);
                } 
            }
            if (counter == 1000) {
                System.out.println(counterHigh * counterLow);
            }
        }

        long res = 1;
        for (Integer val : modulesNearRx.values()) res *= val;
        System.out.println(res);
    }

    private boolean notSetted(Map<String, Integer> modules) {
        for (Integer value : modules.values()) {
            if (value == 0) return true;
        }
        return false;
    }
}
