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

## v6.5: 패키지 도입

- 클래스의 역할에 따라 여러 패키지로 분리
    - org.example.handler: 사용자 명령을 처리하는 핸들러 클래스를 두는 패키지
    - org.example.util: 공통 기능을 수행하는 클래스를 두는 패키지
- 외부 패키지의 클래스에 공개할 메서드를 표시
    - public
    - 참고: 접근 제어를 표시하지 않으면(package member 라 부른다) 같은 패키지의 클래스만 접근 가능

## v7.0: Interface 활용

- 핸들러의 호출 규칙을 정의
    - 향후 핸들러를 일관성 있게 사용할 수 있다.
    - 문법: Interface 
    - 즉 인터페이스 문법은 객체 사용 규칙을 정의하는 문법
    - Handler 인터페이스 정의 
- Handler 인터페이스 구현
    - MemberHandler 클래스
    - BoardHandler 클래스
- 이론
    - GRASP
        - Low Coupling
        - Polymorphism

## v8.0: 파일 I/O 다루기

- 데이터를 파일에 저장하고 읽는다.
    - MemberHandler, BoardHandler 클래스 변경
- 이론:
    - GoF(4인조)의 Design Patterns
        - Factory Method 패턴
    - API 사용
        - FileReader/FileWriter 사용
        - StringBuilder/CharBuffer 사용
    - CSV(Comma-Seperated Value) 포맷

## v8.1: I/O 데코레이터 사용

- 데이터를 읽고 쓸 때 데코레이터의 도움 받기
- 이론:
    - GoF의 Design Patterns
        - Decorator 패턴

## v8.2: GRASP의 Information Expert 적용하기

- 객체의 값을 CSV 포맷 스트링으로 변환하는 기능을 해당 객체로 옮기기
    - Post, User 클래스 변경
    - 생성자 추가
    - 기본 생성자 명시적으로 정의
- 이론:
    - GRASP(객체지향 기본 설계 패턴)
        - Information Expert
            - 데이터를 다루는 메서드는 그 데이터를 갖고 있는 클래스에 두는 것이 유지보수에 좋다.

## v8.3: GoF의 Design Patterns - Factory Method 패턴 적용하기

- 객체 생성을 대행하는 메서드 만들기
    - 객체 생성 과정이 복잡할 경우 객체 생성 코드를 메서드로 감추는 것이 유지보수에 좋다.
    - Post, User 클래스 변경
- 이론: 
    - GoF의 Design Patterns
        - Factory Method 패턴

## v9.0: Persistence 객체 도입

- 데이터를 보관하고 관리하는 일을 하는 객체를 Persistence 객체라 부른다.
    - MemberDao, BoardDao 클래스 생성
    - User, Post 클래스를 핸들러의 멤버가 아닌 패키지 멤버로 전환
- 이론:
    - SOLID의 SRP(Single Responsibility Priciple)

## v9.1: 데이터의 key 중복 검사하기

- 사용자의 이메일 중복 검사 기능을 추가
    - MemberHandler 클래스 변경
- 게시글 번호 중복 검사 기능 추가
    - BoardHandler 클래스 변경

## v9.2: 예외 처리 문법을 이용하여 데이터 중복 검증하기

- 데이터를 입력할 때 key 값이 중복된 경우 예외 발생시키기
    - MemberDao, BoardDao 클래스 변경
- 입력 오류가 발생했을 때 예외 처리하기
    - MemberHandler, BoardHandler 클래스 변경
- 이론:
    - throw 사용법
    - throws 사용법
    - try ~ catch ~ 사용법

## v9.3: 게시글 번호 자동할당하기

- 게시글 입력할 때 번호는 입력 받지 않고 자동으로 할당
    - BoardHandler 클래스 변경
    - BoardDao 클래스 변경

## v10.0: Collection API 사용하기 

- 배열 대신 ArrayList를 사용하여 객체 목록을 다루기
    - BoardDao, MemberDao 클래스 변경
        - 삭제 기능 추가: delete()
    - BoardHandler, MemberHandler 클래스 변경

## v10.1: ArrayList를 직접 구현하기

- ArrayList를 직접 구현한다.
    - SimpleArrayList 클래스 정의
    - MemberDao, BoardDao 적용
    - MemberHandler, BoardHandler 변경
- 이론:
    - 상속, 다형성(다형적 변수), 제네릭 사용법

## v10.2: LinkedList를 직접 구현하기

- LinkedList를 직접 구현한다.
    - SimpleLinkedList 클래스 정의
    - MemberDao, BoardDao 적용
    - MemberHandler, BoardHandler 변경
- 이론:
    - 자료구조
        - LinkedList의 동작 원리 이해 및 구현

## v10.3: 목록을 다루는 객체 사용법을 인터페이스로 정의

- SimpleArrayList와 SimpleLinkedList의 기능을 인터페이스로 정의
    - SimpleList 인터페이스 정의
    - SimpleArrayList, SimpleLinkedList 클래스를 변경
    - MemberDao, BoardDao 적용
    - MemberHandler, BoardHandler 변경
- 이론:
    - GRASP 패턴
        - Low Coupling 구현
            - 인터페이스를 이용하여 의존 객체에 대한 커플링을 약화시킨다.
            - 클래스에 의존하는 것 보다 인터페이스에 의존하는 것이 유지보수에 좋다.
            - 즉 클래스가 추가 되거나 변경 되더라도 인터페이스에 의존하는 경우에는 코드를 변경할 필요가 없다.

## v10.4: 의존 객체 주입 방식 도입

- 의존 객체를 직접 생성하지 않고 외부에서 주입 받기
    - 특정 클래스를 사용하지 않기 때문에 그 클래스와의 결합을 끊을 수 있다.
    - 즉 결합도를 낮출 수 있다.(= Low Coupling)
    - MemberDao, BoardDao 변경
    - MemberHandler, BoardHandler 변경
    - App 클래스 변경
- 이론:
    - SOLID 설계 원칙
        - Dependency Inversion Principle(DIP) 
        - Dependency Injection(DI) 라고도 부른다.
        - Inversion of Control(IoC) 라고도 부른다.

## v10.5: 자바에서 제공하는 Collection API 사용하기

- 직접 만든 클래스 대신 자바에서 제공해주는 ArrayList와 LinkedList 적용하기
    - MemberDao, BoardDao 클래스 변경
    - MemberHandler, BoardHandler 클래스 변경
    - App 클래스 변경
- 이론:
    - Collection API란? 
        - 데이터 목록을 다루는 클래스와 인터페이스를 가리킨다.

