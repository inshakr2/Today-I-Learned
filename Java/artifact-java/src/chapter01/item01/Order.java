package chapter01.item01;

public class Order {
    private boolean prime;
    private boolean urgent;
    private Product product;

    /**
     * 만들어주는 객체의 특징을 Factory Method의 이름으로 더 잘 나타내 줄 수 있다.
     */
    public static Order primeOrder(Product product) {
        Order order = new Order();
        order.prime = true;
        order.product = product;
        return order;
    }

    public static Order urgentOrder(Product product) {
        Order order = new Order();
        order.urgent = true;
        order.product = product;
        return order;
    }

    /**
     * 동일한 시그니처의 생성자는 생성할 수 없거니와 생성자만으로 어떠한 형태로 객체를 생성하는지
     * 표현할 수 없음.
     */
//    public Order(boolean prime, Product product) {
//        this.prime = prime;
//        this.product = product;
//    }
//
//    public Order(boolean urgent, Product product) {
//        this.urgent = urgent;
//        this.product = product;
//    }
}
