# home-app

## 프로젝트 준비

### v1.0: Gradle 도입 전

- src 파일을 별도 디렉토리로 분리
- MANIFEST.MF 파일 추가

### v2.0: Gradle 도입 후

- Gradle을 사용하여 프로젝트 디렉토리를 구성

## Standalone 애플리케이션

## v3.0: CRUD 구현

- 회원관리 CRUD

## v3.1: 클래스 활용

- 회원관리 관련 메서드 및 변수를 별도 클래스로 분리
    - MemberHandler
- 이론:
    - SOLID
        - SRP(Single Responsibility Principle)
    - GRASP(General Responsibility Assignment Software Patterns)
        - High Cohesion

## v3.2: 클래스 이동

- User 클래스를 App에서 MemberHandler로 소속을 옮긴다.

## v4.0: 메인 메뉴 도입

- 메인 메뉴 기능 추가
    - App
- 회원 관리 메뉴 기능 이동
    - MemberHandler
- 프롬프트 기능을 공통 사용 클래스로 분리
    - Prompt
    - App 클래스와 묶이면 다른 프로젝트에서 재사용하기 힘들다.
    - 그래서 프롬프트 기능을 유틸리티 역할을 수행하는 공통 클래스로 이동한다.
        - Low Coupling
- 이론:
    - GRASP의 Low Coupling

## v5.0: 게시글 CRUD 구현

- 게시글 관리 기능 추가
    - BoardHandler 

## v5.1: 리팩토링

- 메서드 이름 변경
    - MemberHandler
        - handleMemberCreate() --> handleCreate()
        - handleXxxYyyy() --<> handleYyyy()
- int 값을 입력 받는 메서드 추가
    - Prompt
        - inputInt() 

## v6.0: 기술소개 CRUD 구현

- BoardHandler 클래스 복제
    - TechBoardHandler 
- 각 메뉴를 실행할 때 제목 출력
    - BoardHandler 변경

## v6.1: 인스턴스 변수 도입

- BoardHandler 클래스
    - 변수를 인스턴스 변수로 변경
    - TechBoardHandler 삭제
        - 코드 중복을 없앤다.

## v6.2: 생성자 도입

- 인스턴스 생성할 때 특정 필드 값을 반드시 설정하도록 유도
    - BoardHandler 클래스에 생성자 추가

## v6.3: 인스턴스 메서드 도입

- 인스턴스를 다루기 쉽도록 인스턴스 주소를 this 내장 변수로 받는 메서드
    - non-static 메서드 = 인스턴스 메서드
    - BoardHandler 클래스의 메서드 변경
    - 인스턴스를 다루는 메서드는 non-static으로 정의하는 것이 유지보수에 낫다.

## v6.4: 리팩토링

- 향후 재상용 및 확장이 용이하도록 인스턴스 변수 및 메서드로 변환
    - MemberHandler 클래스 변경
- 외부에 공개하지 않을 멤버는 접근하지 못하도록 표시
    - private

