package jpabook.jpashop.domain;

import jpabook.jpashop.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"), //중간테이블에서 쓰는 값
            inverseJoinColumns = @JoinColumn(name = "item_id") // 이 테이블의 아이템//
    ) // 중간 테이블 필요
    private List<Item> items = new ArrayList<>();

    //같은 엔티티에 대해 연관관계
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    // 같은 엔티티로 양방향
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
