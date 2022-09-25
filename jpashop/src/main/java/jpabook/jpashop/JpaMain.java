package jpabook.jpashop;

import jpabook.jpashop.domain.Member;

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