package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;


@SpringBootTest
@Transactional
class MemberTestRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberTestRepository memberTestRepository;

    @Test
    public void searchTest1() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<Member> result = memberTestRepository.applyPagination(condition, pageRequest);

        System.out.println(result.getSize());
        System.out.println(result.getTotalElements());
        System.out.println(result.getTotalPages());
        for (Member member : result) {
            System.out.println(member);
        }
    }
}