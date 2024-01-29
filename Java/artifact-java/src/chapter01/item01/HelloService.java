package chapter01.item01;

public interface HelloService {

    String hello();

    /**
     * java 8부터는 이러한 정적 factory method를 인터페이스에 선언할 수 있게 되었다.
     */
    static HelloService of(String lang) {
        if (lang.equalsIgnoreCase("ko")) {
            return new KoreanHelloService();
        } else {
            return new EnglishHelloService();
        }
    }
}
