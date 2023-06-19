package study.datajpa.repository;

public interface NestedClosedProjections {
    String getUsername();

    // 연관관계 엔티티는 최적화 안됨됨
   String getTeam();

    interface TeamInfo {
        String getName();
    }

}
