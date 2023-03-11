package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext //엔티티 매니저를 주입해줌
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId(); // 커맨드와 쿼리를 분리하기 위해 저장된 후에는 객체를 리턴하지 않고 아이디정도만 리턴
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
