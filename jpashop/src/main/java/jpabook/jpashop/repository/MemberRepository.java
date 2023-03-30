package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //내부에 @Component이 있어서 컴포넌트 스캔 대상이 되어 자동으로 스프링 빈으로 등록됨
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext // 스프링이 엔티티 매니저 주입해줌
    private final EntityManager em; // 스프링부트 사용하면 이것도 의존성 주입으로 사용가능하다. -> 생성자 주입

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) //(쿼리, 반환 타입)
            .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        //":name" 에 바인딩
    }
}
