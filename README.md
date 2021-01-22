#querydsl-study-project

## 실전! Querydsl

https://www.inflearn.com/course/Querydsl-%EC%8B%A4%EC%A0%84#


1. JPA JPQL 서브쿼리의 한계점으로 from 절의 서브쿼리(인라인 뷰)는 지원하지 않는다.
    - 서브쿼리를 join으로 변경한다. (가능한 상황도 있고, 불가능한 상황도 있다.)
    - 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
    - nativeSQL을 사용한다.
2. 튜플이 querydsl에 종속적이기 때문에 프로젝션을 튜플로 사용하여 튜플을 서비스 등으로 전달하기보다 dto를 넘겨주는게 좋음
3. @QueryProjection을 사용하면 DTO가 Querydsl에 의존하게 되면서 하부기술 변경같은 경우에 문제가 될수있다.