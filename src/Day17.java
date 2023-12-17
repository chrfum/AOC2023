import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Day17 extends AOCDay {
    
    static class State implements Comparable<State> {
        int hl, r, c, dr, dc, n;

        State(int hl, int r, int c, int dr, int dc, int n) {
            this.hl = hl;
            this.r = r;
            this.c = c;
            this.dr = dr;
            this.dc = dc;
            this.n = n;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.hl, other.hl);
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            return r == state.r && c == state.c && dr == state.dr && dc == state.dc && n == state.n;
        }

        @Override
        public int hashCode() {
            int result = r;
            result = 31 * result + c;
            result = 31 * result + dr;
            result = 31 * result + dc;
            result = 31 * result + n;
            return result;
        }
    }

    public void solve(File in) {
        inputAsIntMatrix(in);

        Map<State, Boolean> seen = new HashMap<>();
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(0, 0, 0, 0, 0, 0));
        
        while (!pq.isEmpty()) {
            State state = pq.poll();
            int hl = state.hl, r = state.r, c = state.c, dr = state.dr, dc = state.dc, n = state.n;

            if (r == intMatrix.size() - 1 && c == intMatrix.get(0).size() - 1 && n >= 4) {
                System.out.println(hl);
                break;
            }

            if (seen.containsKey(state)) continue;
            seen.put(state, true);

            if (n < 10 && (dr != 0 || dc != 0)) {
                int nr = r + dr;
                int nc = c + dc;
                if (0 <= nr && nr < intMatrix.size() && 0 <= nc && nc < intMatrix.get(0).size()) {
                    pq.add(new State(hl + intMatrix.get(nr).get(nc), nr, nc, dr, dc, n + 1));
                }
            }

            if (n >= 4 || (dr == 0 && dc == 0)) {
                int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
                for (int[] direction : directions) {
                    int ndr = direction[0];
                    int ndc = direction[1];
                    if ((ndr != dr || ndc != dc) && (ndr != -dr || ndc != -dc)) {
                        int nr = r + ndr;
                        int nc = c + ndc;
                        if (0 <= nr && nr < intMatrix.size() && 0 <= nc && nc < intMatrix.get(0).size()) {
                            pq.add(new State(hl + intMatrix.get(nr).get(nc), nr, nc, ndr, ndc, 1));
                        }
                    }
                }
            }
        }
    }
}
