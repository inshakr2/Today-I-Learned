package boj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NOL_1 {
    static class Player {
        String name;
        int score;
        int time;

        Player(String name, int score, int time) {
            this.name = name;
            this.score = score;
            this.time = time;
        }
    }

    public int solution(int K, String[] user_scores) {
        // Map<String, Player> map = new HashMap<>();
        List<Player> players = new ArrayList<>();
        String prev = null;
        int answer = 0;

        for (int t = 0; t < user_scores.length; t++) {
            String record = user_scores[t];


            String name = record.substring(0, record.indexOf(' '));
            int score = Integer.parseInt(record.substring(record.indexOf(' ') + 1));

            int idx = -1;
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).name.equals(name)) {
                    idx = i;
                    break;
                }
            }

            boolean updated = false;

            if (idx == -1) {
                players.add(new Player(name, score, t));
                updated = true;
            } else {
                Player p = players.get(idx);
                if (score > p.score) {
                    p.score = score;
                    p.time = t;
                    updated = true;
                } else {
                    updated = false;
                }
            }

            if (updated) {
                players.sort(new Comparator<Player>() {
                    @Override
                    public int compare(Player a, Player b) {
                        if (a.score != b.score) {
                            return Integer.compare(b.score, a.score); // 점수 내림차순
                        }
                        return Integer.compare(a.time, b.time); // 먼저 달성한 사람이 위
                    }
                });

                StringBuilder sb = new StringBuilder();
                int limit = Math.min(K, players.size());
                for (int i = 0; i < limit; i++) {
                    if (i > 0) sb.append('#');
                    sb.append(players.get(i).name);
                }
                String curPage = sb.toString();

                // 이전 페이지와 비교
                if (prev == null || !prev.equals(curPage)) {
                    answer++;
                    prev = curPage;
                }
            }
        }
        return answer;
    }
}
