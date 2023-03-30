package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 여기선 조회 메소드가 더 많기 때문에 Transactional(readOnly = true)를 전체 퍼블릭 메소드에 먹일 수 있도록 밖에 빼줌
    @Transactional(readOnly = true) // 조회하는 메소드 -> JPA가 조회 시 성능 최적화할 수 있음(영속성 컨텍스트를 flush하지 않거나 db 리소스를 많이 쓰지 않는 모드 등...)
//@AllArgsConstructor // 생성자 주입 - 모든 필드를 가지고 생성자를 만들어줌
    @RequiredArgsConstructor // 생성자 주입 - final이 있는 필드만 가지고 생성자를 만들어줌
    public class MemberService {

        //@Autowired // 필드 인젝션 : 단점1) 다른 데(테스트 코드 등)서 access할 수 없다.
        private final MemberRepository memberRepository; // 생성자 주입에서 final 쓰는 것 권장 -> final을 해두면 컴파일 시점에 의존관계 주입이 잘 되었는지 확인 가능

        //세터 인젝션 : 장점) 테스트 코드 작성 시 목주입 편하다.
        //단점) 애플리케이션 로딩 시점에 조립이 다 끝나기 때문에 조립 이후 바꿀 일이 잘 없다.
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

        // @Autowired // 생성자 주입 : 장점) 중간에 바뀔 일이 없다, 생성 시점에 의존관계 주입을 안 놓치고 잘 할 수 있다. => Autowire 안 써도 생성자 하나만 있으면 자동으로 인젠션해준다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

        /**
         * 회원가입
         */
        @Transactional // 스프링에서 제공하는 transactional이 옵션이 더 많으므로 권장
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복회원검증
        memberRepository.save(member); // em.persist할 때 db의 pk값을 key로 넣는 아직 db에 들어간 시점이 아니더라도 채워준다 -> 늘 값이 있음이 보장됨
        return member.getId();
    }

    // 실무에서는 멀티스레드, 동시에 요청 들어오는 상황을 고려해서 member name을 unique 제약조건으로 거는 게 낫다.
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
