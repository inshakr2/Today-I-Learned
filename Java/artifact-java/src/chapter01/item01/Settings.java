package chapter01.item01;

public class Settings {

    private boolean useAutoSteering;
    private boolean useABS;
    private Difficulty difficulty;

    /**
     * 정적 Factory Method를 사용하면, 인스턴스의 생성을 제어할 수 있게 된다.
     * 일반적인 생성자의 경우에는 아무곳에서나 객체 생성을 얼마든지 할 수 있기 때문에 이러한 문제를 막을 수 있다.
     */
    private static final Settings SETTINGS = new Settings();

    public static Settings newInstance() {

        return SETTINGS;
    }

}
