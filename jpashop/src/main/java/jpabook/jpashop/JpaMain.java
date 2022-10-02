package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //애플리케이션 로딩 시점에 하나만 만들어야 함

        EntityManager em = emf.createEntityManager(); //db커넥션을 얻어서 쿼리를 날리고 종료되는 일관적인 단위마다 em 만들어줘야 함

        EntityTransaction tx = em.getTransaction(); //트랜잭션 얻음
        tx.begin();

        try{
            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear(); //이거 있으면 영속성 컨텍스트에서 안 가져오고 db에서 가져오는 거 볼 수 있음

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers(); //양방향

            for(Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close(); //사용을 다 하고 나면 꼭 닫아줘야 함
        }

        emf.close();
    }
}