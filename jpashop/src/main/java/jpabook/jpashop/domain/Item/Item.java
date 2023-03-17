package jpabook.jpashop.domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속관계매핑의 전략을 부모클래스에 잡는다.
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 추상 클래스 -> 구현체 사용, 상속관계

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
