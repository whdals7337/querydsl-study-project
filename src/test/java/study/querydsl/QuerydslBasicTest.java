package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
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
    }

    @Test
    public void startJPQL() throws Exception {
        // given

        // when
        Member findMember = em.createQuery("select m from Member m where m.username= :username",Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() throws Exception {
        // given

        // when
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search() throws Exception {
        // given

        // when
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(
                        QMember.member.username.eq("member1"),
                        QMember.member.age.between(10, 30)
                ).fetchOne();
        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    public void queryKinds() throws Exception {
        // given

        // when
        Member fetchOne = queryFactory.selectFrom(member).where(member.username.eq("member1")).fetchOne();

        List<Member> fetchList = queryFactory.selectFrom(member).fetch();

        Member memberFirst = queryFactory.selectFrom(QMember.member).fetchFirst();

        QueryResults<Member> results = queryFactory.selectFrom(member).fetchResults();
        List<Member> content = results.getResults();

        long l = queryFactory.selectFrom(member).fetchCount();
        System.out.println(l);

        // then

    }

    @Test
    public void sort() throws Exception {
        // given
        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

        // when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        // then
        for (Member member1 : result) {
            System.out.println(member1);
        }
    }
}
