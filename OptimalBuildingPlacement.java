import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class OptimalBuildingPlacement {

    // Backtracking - Combination of N-Queens and 01 matrix problems - Time O(h*w)*O(h*wCn) and O(h*w)

    int h;
    int w;
    int minDist = Integer.MAX_VALUE;
    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int findMinDist(int h, int w, int n) {
        this.h = h;
        this.w = w;
        int[][] grid = new int[h][w];
        backtrack(grid, 0, n);
        return minDist;
    }

    public void backtrack(int[][] grid, int idx, int n) {

        // base
        // terminate if remaining buildings to be places are none
        if (n == 0) {
            // Calculate the distance for this placement using bfs and update minDist if needed
            bfs(grid);
        }

        // Iterate over all possible positions to place a building.
        for (int i = idx; i < h*w; i++) {

                // row and column indices
                int r = i / w;
                int c = i % w;

                // Action - Place a building
                grid[r][c] = -1;

                // Recurse with one less building to place
                backtrack(grid, i + 1, n - 1);

                // Backtrack (remove the building)
                grid[r][c] = 0;

        }
    }

    public void bfs(int[][] grid) {

        // queue of positions
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[h][w];

        // Initialize the queue with the positions of the existing buildings
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {

                if (grid[i][j] == -1) {
                    q.add(new int[]{i, j});
                    // existing buildings are visited
                    visited[i][j] = true;
                }
            }
        }

        int dist = 0;

        // bfs starts
        while (!q.isEmpty()) {

            int sizeQ = q.size();
            // iterate over levels
            for (int k = 0; k < sizeQ; k++) {

                int[] curr = q.poll();
                // avoid null pointer exception
                if(curr != null) {

                    for (int[] dir : dirs) {
                        int nr = curr[0] + dir[0];
                        int nc = curr[1] + dir[1];

                        // make new position visited, if not already and add to queue
                        if (nr >= 0 && nc >= 0 && nr < h && nc < w && !visited[nr][nc]) {
                            visited[nr][nc] = true;
                            q.add(new int[]{nr, nc});

                        }
                    }
                }
            }
            // distance increments after each level
            dist++;
        }
        // last increment before bfs ends is not required
        minDist = Math.min(minDist, dist-1);
    }

    public static void main(String[] args) {
        OptimalBuildingPlacement obj = new OptimalBuildingPlacement();
        Scanner scanner = new Scanner(System.in);

        System.out.println("height");
        int h = scanner.nextInt();

        System.out.println("width");
        int w = scanner.nextInt();

        System.out.println("buildings");
        int n = scanner.nextInt();

        int answer = obj.findMinDist(h, w, n);

        System.out.println("the distance of the most distant empty lot from a building is " + answer);


    }
}
/*

Time complexity = O(h*w)*O(h*wCn)

h*w is for bfs and combinations of choosing place for n buildings is h*wCn.

Space complexity - O(h*w) - bfs queue
 */
