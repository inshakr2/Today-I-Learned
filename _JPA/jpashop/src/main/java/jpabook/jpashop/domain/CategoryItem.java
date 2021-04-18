package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class CategoryItem {

    @Id @GeneratedValue
    @Column(name = "CATEGGOEYITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGOEY_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

}
