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
- [x] 정적 html 파일 응답
    - http://localhost:8080/{문서_이름} 접속 시 `src/main/resource/static` 디렉토리의 문서 이름을 가진 문서를 반환한다
    - 찾고자 하는 파일이 없을 경우 : default 404 페이지를 반환한다
- [x] Http Request 내용 출력
  - Http Request 구조 분리
    - [x] Http RequestLine : HttpMethod + RequestUrl + Http Version
      - ex) GET /index.html HTTP/1.1
    - [x] Http RequestHeader 분리
    - [x] Http RequestBody 분리
    - 서버로 들어오는 Http Request 내용을 적절하게 파싱하여 로거(log.debug)를 활용해 출력한다
- [x] Java Thread 기반 프로젝트를 Concurrent 패키지를 활용하도록 변경한다
- [x] 단위 테스트를 만들어 구현 내용을 검증한다.


## Step2
- [x] Response Header의 다양한 Contet-Type 지원하기 

## Step3
- [x] `회원 가입` 메뉴 클릭 시 ` http://localhost:8080/register.html`로 이동한다.
- [x] 회원 가입 버튼을 클릭하면 쿼리 파라미터로 가입자 정보(userId, password, name, email)가 전달된다.
- [x] URL을 비교하고 사용자가 입력한 값을 파싱하여 model.User 클래스에 저장한다.
- [ ] Junit & AssertJ를 활용한 테스트 코드를 작성한다.

## Step4
- [x] 로그인을 POST API로 변경한다.
- [x] 회원 가입 완료 후 /index.html 페이지로 이동한다

## Step5
- [x] 로그인 
  - [x] GET /login 요청 시 로그인 페이지를 반환한다.
  - [x] 로그인이 성공하면 /index.html로 이동한다.
  - [x] 로그인이 실패하면 사용자에게 알려주고 로그인 화면에 머무른다.
- [x] 로그아웃
  - [x] 로그인인 상태의 유저만 로그아웃을 시도할 수 있다.
  - [x] 로그아웃에 성공하면 index.html로 이동한다.

## Step6
- [x] 유저가 로그인 상태일 경우 /index.html에서 유저 이름을 표기해준다.
- [x] 유저가 로그인 상태가 아닌 경우에는 /index.html에서 `로그인` 버튼을 표시해준다
- [x] 유저 개인정보 수정 페이지
  - [x] 로그인 상태에서 사용자 이름을 클릭하는 경우 이동가능하다
  - [x] 로그인 상태가 아닐 경우 메인 페이지로 리다이렉션한다
- [x] ExceptionHandler를 통해 한곳에서 에러를 통합관리한다.

## Step7

### 글쓰기 기능 구현
- [x] index.html 하단에 글쓰기 버튼을 추가한다.
- [x] 로그인한 사용자가 글쓰기 버튼을 클릭하면 write.html 로 이동한다.
- [x] 로그인하지 않은 사용자가 글쓰기 버튼을 클릭하면 로그인 페이지로 이동한다.
- [x] write.html에서는 글의 본문을 입력할 수 있는 폼을 표시한다.
- [x] 작성한 글의 제목을 입력할 수 있는 칸을 넣는다
- [ ] 작성한 글의 제목을 index.html에서 보여준다.

### 글쓰기에 이미지 업로드 기능 추가
- [ ] 글쓰기 페이지에 이미지 파일 업로드 기능을 추가한다

### 이미지 보여주기 기능
- [ ] 메인 페이지에서 이미지를 보여주는 기능을 추가한다.

### my page에 프로필 사진 변경 기능 추가
- [ ] 로그인 상태에서 헤더의 사용자 id를 누를 경우 /mypage로 연결 > 이 때 기존에 등록된 기본 이미지를 변경해서 업로드
- [ ] 변경된 프로필 이미지는 메인 화면에서 볼 수 있어야 한다.

### 404 에러 처리 페이지 구현
- [x] 존재하지 않는 요청에 대해 404 페이지로 이동하도록 구현
- [ ] 기타 처리할 수 없는 요청에도 적절한 에러 페이지로 응답하도록 한다.
- [ ] 기타 발생한 서버 오류를 확인할 수 있는 에러 페이지를 구현한다.

### DB 연동
- [ ] H2와 Jdbc를 사용하여 회원정보와 게시글을 저장하도록 구현한다

### POST 파일 업로드
- [ ] 이미지 업로드가 정상 동작하기 위해서 사용자가 업로드한 이미지 파일을 POST로 처리할 수 있도록 구현

### Javadoc 생성
- [ ] 클래스와 퍼블릭 메서드에 javadoc 형식의 주석 작성
- [ ] 작성 주석을 기반으로 javadoc 생성
- [ ] 생성된 웹페이지 문서를 Github Page 기능을 활용해 업로드 및 주소를 댓글에 작성



# 학습 키워드

## Step1
- 자바 스레드 모델과 버전별 변경점
- 자바 Concurrent 패키지

# Step2
- Response Content-Type

# Step4
- Redirect : 301 vs 302 vs 303

# Step5
- Cookie : Path, Secure 옵션


# 고민한 부분
- InputStreamReader vs BufferedReader vs Scanner
- [Concurrent 패키지 뜯어보기 : 30명 동시요청 시나리오를 통한 스레드풀 의의 살펴보기](https://cheddar-parade-d79.notion.site/Concurrent-2d93b0d6b35580bea095e4fa91759e8d?source=copy_link)
- [Http Keep Alive로 인한 Request Input Stream 단말 지점 판단 기준](https://www.notion.so/HTTP-InputStream-2d93b0d6b35580549527c3b9172c2334?source=copy_link)
  - 스트림 전체 읽기 vs ready vs Connection : close

# 고민할 부분

## Step1
- nio vs io
- nio 패키지를 쓰지 않고 파일 읽는 법
- BufferedReader가 스트림의 끝을 어떻게 판단하는가 : 스트림이 모두 소모되면 null or empty 반환
- BufferedReader vs InputStreamReader

## Step3
- 의존성을 어떻게 분리할까? - WebServer vs Application
