package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // Junit에게 스프링 관련 테스트임을 알림
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional // 엔티티 매니저를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이뤄져야 한다. & 테스트에서 트랜잭션 붙으면 테스트 후 롤백
    @Rollback(false) // rollback 되지 않고 db에 남도록 하는 어노테이션
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);

        System.out.println("findMember == member: " + (findMember == member)); //같은 트랜잭션 안에서 저장하고 조회하면 영속성컨텍스트가 같고 같은 영속성컨텍스트 안에서는 식별자가 같으면 같은 엔티티로 식별한다.

    }
}