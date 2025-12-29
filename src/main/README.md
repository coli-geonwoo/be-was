# 학습 계획
- 1단계 : 스텝별 요구사항 정리 및 1차 구현
- 2단계 : 책 `자바 웹 프로그래밍 Next Step` 읽고 리팩터링
- 3단계 : Tomcat 내부 살펴보기

# 요구사항 정리

## 컨벤션 요구사항
- build.gradle에 초기에 주어진 패키지 외 외부 패키지 의존성을 최소화한다.
- JDK의 nio는 사용하지 않는다.
- Lombok은 사용하지 않는다.
- MVC와 관련된 Naming은 사용하지 않는다(Controller, Service, DTO, Repository 등)

## Step1
- [ ] 정적 html 파일 응답
  - http://localhost:8080/{문서_이름} 접속 시 `src/main/resource/static` 디렉토리의 문서 이름을 가진 문서를 반환한다
  - 찾고자 하는 파일이 없을 경우 : default 404 페이지를 반환한다
- [ ] Http Request 내용 출력
  - 서버로 들어오는 Http Request 내용을 적절하게 파싱하여 로거(log.debug)를 활용해 출력한다
- [ ] Java Thread 기반 프로젝트를 Concurrent 패키지를 활용하도록 변경한다
- [ ] 단위 테스트를 만들어 구현 내용을 검증한다.

# 학습 키워드

## Step1
- 자바 스레드 모델과 버전별 변경점
- 자바 Concurrent 패키지


# 고민한 부분

## Step1
- nio vs io
- nio 패키지를 쓰지 않고 파일 읽는 법
- 
