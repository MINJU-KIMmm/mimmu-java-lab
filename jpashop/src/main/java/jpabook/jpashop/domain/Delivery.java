package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // fk 아무쪽에나 둬도 되는데, Order를 가지고 Delivery를 찾는 경우가 많아서 fk를 order에 둔다. -> 연관관계 주인 order
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 기본 Ordinal인데 꼭 STRING으로 써야한다. (중간에 들어가면 장애)
    private DeliveryStatus status; //READY, COMP
}
