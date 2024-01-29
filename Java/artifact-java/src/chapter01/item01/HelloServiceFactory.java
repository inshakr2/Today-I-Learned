package chapter01.item01;

public class HelloServiceFactory {

    /**
     * 매개변수에 따라서 각기 다른 인스턴스를 반환할 수 있다.
     * 인터페이스에 대한 구현체를 분리하여 반환해 줄 수 있다.
     * 이에 따라서 해당 Method를 통해 반환받는 인스턴스 타입은 인터페이스이기 때문에
     * 인터페이스에 기반한 FrameWork를 강제시킬 수 있다.
     * 추가적으로 클라이언트로 부터 구체적인 타입을 숨길 수 있다.
     *  ===> java 8부터는 이러한 정적 factory method를 인터페이스에 선언할 수 있게 되었다.
     *  ### ServiceLoader를 사용하면, 구현체가 없이도 호출이 가능하다.
     */
//    public static HelloService of(String lang) {
//        if (lang.equalsIgnoreCase("ko")) {
//            return new KoreanHelloService();
//        } else {
//            return new EnglishHelloService();
//        }
//    }
    public static void main(String[] args) {
        HelloService ko = HelloService.of("ko");
        System.out.println(ko.hello());
    }
}
