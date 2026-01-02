public class avcd {
    /**
     * 다음 코딩문제야.
     * 완전 탐색 알고리즘이 필요한거 같아.
     * 아래와 같이 W*H 형태의 격자로 이루어진 그래프가 주어져. (W*H 는 500이하의 자연수, 서로 같거나 다름)
     * ```격자
     * [[3,9,2,7],[10,6,8,4],[1,4,9,10],[5,7,8,4]]
     * ```
     * 이 때 어떤 위치에 두었을 때 가장 많이 움직일 수 있는지 찾아야해.
     * 움직이는 규칙은 동/서/남/북 으로 움직일 수 있고, 마지막으로 도달한 좌표의 숫자보다 큰 곳으로 만 움직일 수 있어.
     * 위 예시의 경우는 3행 1열에 놓이면 1,4,7,8,9,10 으로 움직여서 6번을 움직일 수 있는 것이 최댓값이야.
     */

    private static final int[] move_x = {0, 0, 1, -1};
    private static final int[] move_y = {1, -1, 0, 0};

    private static int[][] visited;
    private static int[][] graph;

    private static int W;
    private static int H;

    public int solution(int [][]R) {

        graph = R;
        W = graph[0].length;
        H = graph.length;
        visited = new int[H][W];

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                visited[i][j] = -1;
            }
        }

        int answer = 0;
        for (int x = 0; x < H; x++) {
            for (int y = 0; y < W; y++) {
                answer = Math.max(answer, dfs(x, y, W, H));
            }
        }

        return answer;
    }

    private static int dfs(int x, int y, int W, int H) {
        if (visited[x][y] != -1) {
            return visited[x][y];
        }

        int maxCount = 0;

        for (int i = 0; i < 4; i++) {
            int mx = x + move_x[i];
            int my = y + move_y[i];

            if (mx >= 0 && mx < H && my >= 0 && my < W && graph[mx][my] > graph[x][y]) {
                maxCount = Math.max(maxCount, dfs(mx, my, W, H));
            }
        }

        visited[x][y] = maxCount + 1;
        return visited[x][y];
    }


    public static void main(String[] args) {

    }
}
