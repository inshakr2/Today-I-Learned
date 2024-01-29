package chapter01.item01;

public class Settings {

    /**
     * private = 상속을 받을 수 없다.
     *  Delegate pattern 을 사용하면 우회할 수 있고, 어떠한 관점에서 본다면 장점으로 볼 수 있다.
     */
    private Settings() {
    }

    private boolean useAutoSteering;
    private boolean useABS;
    private Difficulty difficulty;

    /**
     * 정적 Factory Method를 사용하면, 인스턴스의 생성을 제어할 수 있게 된다.
     * 일반적인 생성자의 경우에는 아무곳에서나 객체 생성을 얼마든지 할 수 있기 때문에 이러한 문제를 막을 수 있다.
     */
    private static final Settings SETTINGS = new Settings();

    /**
     * 정적 팩토리 메서드에 흔히 사용하는 명명 방식들
     *  from
     *  of
     *  valueOf
     *  getInstance
     *  newInstance
     *  getType
     *  ...
     */
    public static Settings getInstance() {

        return SETTINGS;
    }

}
