package jpabook.jpashop.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class CategoryItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "CATEGGOEYITEM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CATEGOEY_ID")
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

}
