package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    private DelivertyStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
