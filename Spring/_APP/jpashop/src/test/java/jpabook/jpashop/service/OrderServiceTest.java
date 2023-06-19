package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품_주문() throws Exception {
        // given
        Member member = createMember("cy");
        int price = 10000;
        int stockQuantity = 10;
        Book book = createBook("How To JPA", price, stockQuantity);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order createdOrder = orderRepository.findOne(orderId);

        assertEquals(createdOrder.getStatus(), OrderStatus.ORDER, "상품 주문시 상태는 ORDER");
        assertEquals(createdOrder.getOrderItems().size(), 1, "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(createdOrder.getTotalPrice(), price * orderCount, "주문 가격은 가격 * 주문 수량이다.");
        assertEquals(book.getStockQuantity(), stockQuantity - orderCount, "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품_주문_재고수량초과() throws Exception {
        // given
        Member member = createMember("cy");
        Book book = createBook("How To JPA", 10000, 10);

        int orderCount = 11;
        // when
        orderService.order(member.getId(), book.getId(), orderCount);

        // then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문_취소() throws Exception {
        // given
        Member member = createMember("cy");
        Book book = createBook("How To JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order cancelOrder = orderRepository.findOne(orderId);

        assertEquals(cancelOrder.getStatus(), OrderStatus.CANCEL, "주문 취소시 상태는 CANCEL이다.");
        assertEquals(book.getStockQuantity(), 10, "주문 취소가 된 상품은 재고가 원복되어야 한다.");

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "강남", "123"));
        em.persist(member);
        return member;
    }
}