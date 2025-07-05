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
