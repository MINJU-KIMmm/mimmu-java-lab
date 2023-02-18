package jpabook.jpashop.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Delivery  extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String city;
    private String street;
    private String zipcode;
    private DelivertyStatus status;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    public Delivery() {
    }
}
