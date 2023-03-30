package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


// spring과 통합 테스트 - 강의에선 JPA 동작 등 보기 위해
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 롤백 되도록
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId)); // 같은 트랜잭션 내(@Transactional)에서 같은 엔티티 (pk가 같으면) 면 같은 영속성 컨텍스트에서 같은 애로 관리됨
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given

        //when

        //then
    }
}
