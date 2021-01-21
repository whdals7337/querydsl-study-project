#querydsl-study-project

## 실전! Querydsl

https://www.inflearn.com/course/Querydsl-%EC%8B%A4%EC%A0%84#


1. JPA JPQL 서브쿼리의 한계점으로 from 절의 서브쿼리(인라인 뷰)는 지원하지 않는다.
    - 서브쿼리를 join으로 변경한다. (가능한 상황도 있고, 불가능한 상황도 있다.)
    - 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
    - nativeSQL을 사용한다.