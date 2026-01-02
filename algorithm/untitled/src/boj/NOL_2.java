package boj;

import java.util.Arrays;

class NOL_2 {
    static class Product {
        int min;
        int max;
        int rate;

        Product(int min, int max, int rate) {
            this.min = min;
            this.max = max;
            this.rate = rate;
        }
    }

    public int solution(int capital, int[][] products) {
        int n = products.length;
        // 부분 집합 생성 후 처리
        Product[] items = new Product[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Product(products[i][0], products[i][1], products[i][2]);
        }
        Arrays.sort(items,
                (a, b) -> Integer.compare(b.rate, a.rate));

        // 각 케이스 별 금액 저장
        int[] minArr = new int[n]; // 최소 금액 = 이건 무조건 투자
        int[] extraArr = new int[n]; // max-min 금액 = 남은 금액에서 rate 비율 높은 순으로 해당 금액 추가 분배할 예정
        int[] rateArr = new int[n]; // 이율

        for (int i = 0; i < n; i++) {
            minArr[i] = items[i].min;
            extraArr[i] = items[i].max - items[i].min;
            rateArr[i] = items[i].rate;
        }

        int totalCase = (int) Math.pow(2, n);
        int[] sumMin = new int[totalCase]; // 조합에서 선택된 상품들의 최소 금액 합
        int[] sumExtra = new int[totalCase]; // 조합에서 선택된 상품들의 extra 금액 합 = 추후 추가 투자 가능 금액을 여기서 차감하며 rate 순으로 계산할 것임
        long[] baseProfit = new long[totalCase]; // 최소 투자만 했을 때 이윤 합

        // 부분 집합에 대해 기본 계산 진행
        for (int c = 0; c < totalCase; c++) {
            int sMin = 0;
            int sExtra = 0;
            long bProfit = 0L;

            int tmp = c; // 모든 케이스를 비트마스크로 조합 진행 (ex 5 => 101 )
            for (int i = 0; i < n; i++) {
                if (tmp % 2 == 1) { // 1 -> 선택 / 0 -> 미선택
                    // 누적합을 구해서
                    sMin += minArr[i];
                    sExtra += extraArr[i];
                    bProfit += (long) minArr[i] * rateArr[i];
                }
                tmp /= 2; // 비트 마스크 연산
            }

            // 해당 case에 대한 값 저장
            sumMin[c] = sMin;
            sumExtra[c] = sExtra;
            baseProfit[c] = bProfit;
        }

        long base = 0L;

        // 모든 부분집합 최대 이윤 계산
        for (int c = 0; c < totalCase; c++) {
            // 최소 투자금액 값
            int baseInvest = sumMin[c];
            if (baseInvest > capital) {
                continue;
            }

            int extraCapacity = capital - baseInvest; // 최소 투자 이후 남은 자본
            int maxExtra = sumExtra[c]; // 현재 조합에서 추가로 넣을 수 있는 extra 금액 총합

            long profit = baseProfit[c]; // 현재 최소 투자금액 합 (1차 확정 이윤 금액)

            if (extraCapacity > 0 && maxExtra > 0) {
                int remaining = Math.min(extraCapacity, maxExtra); // 두 금액 중 더 작은 값이 실제로 투자 가능한 나머지 금액
                long extraProfit = 0L;

                // 연 수익률이 높은 순
                for (int i = 0; i < n && remaining > 0; i++) {
                    // 특정 케이스 c에 대해서 해당 조합의 i번째 상품을 선택했냐 안했냐에 대한 구분
                    // n=3) c가 5인 경우, 비트는 101이므로 0번, 2번 상품 선택에 대한 조합을 골라야 함. 즉 아래 if문은 각 순서로 1인 케이스를 선택하는 로직임
                    // 0,1,2 순으로 비트 연산 진행. 비트 & 비트는 and 연산으로 둘 다 1일 경우에만 1로 반환
                    // 1 << 0 = 1, 비트 001 을 나타냄 => 101 & 001 => 001 을 나타내므로 0번째 상품 선택
                    // 1 << 1 = 2, 비트 010 을 나타냄 => 101 & 010 => 000 울 나타내므로 1번째 상품은 미선택
                    // 1 << 2 = 4, 비트 100 을 나타냄 => 101 & 100 => 100 을 나타내므로 2번째 상품 선택
                    if ((c & (1 << i)) == 0) { // 1 * 2^i
                        continue;
                    }
                    // 위 조합에서 선택된 케이스라면 현재 투자 가능한 금액과 현재 조합에서 추가로 투자 가능한 금액에 대한 합 중에 더 작은 금액 계산
                    // 현재 가지고 있는 금액(수요)과 이번 조합에서 추가로 투자 가능한 금액(공급) 중에 더 작은 쪽이 실제로 투자가 가능한 금액임
                    int canGive = Math.min(remaining, extraArr[i]);
                    if (canGive <= 0) {
                        continue;
                    }

                    // 투자 진행하고 현재 잔금 차감
                    extraProfit += (long) canGive * rateArr[i];
                    remaining -= canGive;
                }

                // 기존 최소금액 투자 금액과 추가 금액 투자 금액 누적합
                profit += extraProfit;
            }

            // 합산 갱신
            if (profit > base) {
                base = profit;
            }
        }

        return (int) (base / 100);
    }
}
