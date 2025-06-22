
<p align="center">
  <img src="https://github.com/Munhak-Zip/Moviezip_Back/assets/110006845/e8d64401-4e07-45d5-abd1-923abf6a17d4" alt="화면 캡처 2024-07-08 231353">
</p>
<br>

## [💻 WEB] 영화 리뷰 모음, 예매 및 추천 웹사이트, MOVIE.ZIP (2024.03 ~ 2024.06)
<p align="center">
  <img src="https://github.com/Munhak-Zip/Moviezip_Back/assets/110006845/8f2ead82-be3e-4fb0-a2ec-04430d1a8cf5">
</p>

<br>

| 회원가입 | 로그인 | 취향 선택  |
|:-:|:-:|:-:|
| <img src="https://github.com/user-attachments/assets/5a32011f-1dd4-4dd9-ac99-13b8edf4ad8c" alt="회원가입" width="450"/> | <img src="https://github.com/user-attachments/assets/8e81fe3e-56b8-4f6f-a9d0-bba771d12118" alt="로그인" width="450"/> | <img src="https://github.com/user-attachments/assets/c6433997-3e3e-4a57-adac-05dcb0d34660" alt="취향선택" width="450"/>

| 메인화면 최신영화 | 메인화면 추천영화 | 영화 검색  |
|:-:|:-:|:-:|
| <img src="https://github.com/user-attachments/assets/c474ea04-9c53-4015-a41b-f177336766b2" alt="최신영화" width="450"/> | <img src="https://github.com/user-attachments/assets/7ee5c7b3-a962-4d32-911c-4c97bdf1131b" alt="추천영화" width="450"/> | <img src="https://github.com/user-attachments/assets/1d069c69-9854-4832-baff-c3a400caca1c" alt="영화검색" width="450"/>

| 영화 상세조회 | 리뷰 작성 | 영화 스크랩  |
|:-:|:-:|:-:|
| <img src="https://github.com/user-attachments/assets/440e360b-8db0-46f3-9cf2-c2e4e0c4ecee" alt="영화상세보기" width="450"/> | <img src="https://github.com/user-attachments/assets/9c7b0390-47a8-4b18-9c6b-95faea3af083" alt="리뷰작성" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/4226bd46-3477-4651-b204-91d4e1572398" alt="영화스크랩" width="450" height="190"/>

| 내가 좋아한 영화 | 내가 작성한 리뷰   | 리뷰 수정 및 삭제  |
|:-:|:-:|:-:|
| <img src="https://github.com/user-attachments/assets/f8c1fb57-10ff-445b-8806-3fa2eaf79b89" alt="내가좋아한영화" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/bfe5b9ee-68f5-4470-9853-1b585896ac54" alt="리뷰리스트" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/7c1d32e1-5fd8-491b-b815-bf786b3101e4" alt="리뷰 수정" width="450" height="190"/>


| 영화관 선택 화면 | 영화 좌석 예매 화면   | 결제화면  |
|:-:|:-:|:-:|
| <img src="https://github.com/user-attachments/assets/4719f0dc-9441-4786-bfb5-d66e7d557d4b" alt="영화관선택화면" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/bf4d9a2a-0237-4500-8945-d6b472161f07" alt="영화좌석예매화면" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/fda9fa59-9c59-45aa-90bd-70d1e02b53eb" alt="결제화면" width="450" height="190"/>

| 마이페이지 | 비밀번호 변경  | 아이디 찾기  |
|:-:|:-:|:-:|
| <img src="https://github.com/user-attachments/assets/541a1bc7-74b7-40b3-921a-f1dc0c078514" alt="마이페이지" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/db1502f1-b064-49b2-b622-7d23cb87f9a1" alt="비밀번호 변경" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/2789237d-13d2-4b98-b357-dd4b8b28f16a" alt="아이디찾기" width="450" height="190"/>


| 채팅방 리스트 | 채팅 화면  | 로그아웃  |
|:-:|:-:|:-:|
| <img src="https://github.com/user-attachments/assets/ccebfa2e-184d-484f-b788-2515307da38f" alt="채팅방리스트" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/9e52b7b0-c7bb-4d9c-a060-97cbc7f86502" alt="채팅화면" width="450" height="190"/> | <img src="https://github.com/user-attachments/assets/699278da-856c-4ca9-a9c0-c932ec330c99" alt="로그아웃" width="450" height="190"/>

## 🧱 ERD

### 📌1차 ERD

<p align="center">
  <img src="https://github.com/user-attachments/assets/5548e8b7-9ac7-49e0-a744-4455d42d995e">
</p>

### 📌2차 ERD - 리뷰 테이블 구조 개선 및 스크랩 기능 분리

- 기존에는 유사한 구조의 리뷰 관련 테이블이 2개로 나뉘어 있었으나, 중복을 줄이고 유지 보수를 용이하게 하기 위해 하나의 테이블로 통합하였습니다.

- 또한, 영화 스크랩과 리뷰 스크랩은 다른 도메인이므로 구분하여 관리할 수 있도록 별도의 테이블로 분리하였습니다.

- MovieScrap: 사용자(User)와 영화(Movie) 간의 스크랩 관계 (User 1:N MovieScrap, Movie 1:N MovieScrap)

- ReviewScrap: 사용자(User)와 리뷰(Review) 간의 스크랩 관계 (User 1:N ReviewScrap, Review 1:N ReviewScrap)

<p align="center">
  <img src="https://github.com/user-attachments/assets/97673c66-7f88-499a-88e0-282cd15e0113">
</p>



### 🎟️ 3차 ERD  영화 예매, 문의 채팅 기능 설계 및 테이블 구조 개선

#### 🎬 예매 기능 개선

기존의 예매 테이블은 단순히 날짜 및 좌석 정보만 저장하고 있어, 실제 영화 예매 시스템의 흐름을 반영하기 어려웠습니다.
이에 따라 일반적인 영화 예매 플로우를 기준으로 필요한 테이블들을 재정의하였습니다.

#### 💬 1:1 고객센터 채팅 기능

사용자와 관리자가 실시간으로 소통할 수 있는 1:1 채팅 기능을 구현하였습니다.
MongoDB를 사용해 비관계형 데이터 구조로 채팅 데이터를 저장하고 있으며, 가독성을 위해 ERD 형식으로 시각화하였습니다.
Oracle 기반으로 기존 설계되었던 데이터베이스와의 직접적인 연동은 없습니다.

#### 🔐 인증 방식 개선 (Session → JWT)

기존에는 Spring Security의 세션 기반 인증을 사용했으나, 다음과 같은 이유로 JWT 기반 인증 방식으로 전환하였습니다.

JWT는 클라이언트 측에 토큰을 저장하고 요청 시 Authorization 헤더를 통해 인증이 이루어집니다.
이를 통해 서버는 세션을 저장하지 않고도 인증 처리가 가능합니다.

### 
<p align="center">
  <img src="https://github.com/user-attachments/assets/a3cc4810-ff4c-48a0-b5f1-60f983a3b757">
</p>

<br>

## 트러블 슈팅

### ✅ 트러블슈팅 1 - @Transactional 누락으로 인한 DB 반영 실패

#### ❗ 문제 상황

엔티티의 필드를 수정했지만, DB에 반영되지 않는 현상 발생

#### 🔍 원인 분석

- JPA의 **변경 감지 기능은 트랜잭션 안에서만 작동**한다는 점을 간과
- `@Transactional` 어노테이션이 누락되어 영속성 컨텍스트가 flush되지 않았음

#### 🛠 해결 방법

- 해당 서비스 메서드에 `@Transactional` 어노테이션 추가

#### ✅ 결과

- 엔티티의 필드 변경이 정상적으로 DB에 반영됨

#### 🧠 인사이트

- 변경 감지는 자동이지만, 트랜잭션 없이는 반영되지 않음
- 서비스 계층에서 명확한 트랜잭션 범위 설정이 중요함

---

### ✅ 트러블슈팅 2 - NullPointerException 발생 (DAO 테스트 환경)

#### ❗ 문제 상황

DAO 테스트 클래스 실행 시, 주입받은 Mapper에서 `NullPointerException` 발생

#### 🔍 원인 분석

- `@Autowired` 어노테이션이 누락되어 **의존성 주입이 되지 않음**

#### 🛠 해결 방법

- `@Autowired` 명시적으로 선언하여 Mapper 주입

#### ✅ 결과

- 테스트 코드가 정상적으로 실행되며 Mapper 주입 성공

#### 🧠 인사이트

- 테스트 코드도 스프링 컨텍스트 내에서 실행되어야 의존성 주입이 가능
- `@Autowired` 누락은 단순하지만 치명적인 실수

---

### ✅ 트러블슈팅 3 - antMatchers 에러 (Spring Security 5.7 이후 DSL 변경)

#### ❗ 문제 상황

```java
http.authorizeRequests()
    .antMatchers("/login").permitAll(); // 컴파일 에러

```

IDE에서 `Cannot resolve method 'antMatchers'` 에러 발생

#### 🔍 원인 분석

- Spring Security **5.7 이상부터 DSL 문법이 변경됨**
- 기존 `authorizeRequests` / `antMatchers` → `authorizeHttpRequests` / `requestMatchers`로 변경됨

#### 🛠 해결 방법

```java
http
    .authorizeHttpRequests((requests) -> requests
        .requestMatchers("/login", "/signup").permitAll()
        .anyRequest().authenticated()
    );

```

#### ✅ 결과

- 빌드 및 실행 성공, 모든 요청 인증 설정 정상 작동

#### 🧠 인사이트

- Spring Security는 버전 업에 따라 DSL 구조가 크게 바뀌므로 **공식 문서 확인이 필수**
- 람다 기반 DSL에 익숙해지는 것이 향후 유지보수에 유리

---

###  ✅ 트러블슈팅 4 - IntelliJ에서 `Cannot resolve class/package 'mysql'` 에러

#### ❗ 문제 상황

의존성을 추가했음에도 불구하고 import 에러 발생

#### 🔍 원인 분석

- IntelliJ의 캐시가 꼬여서 **Maven 의존성 인식을 못하는 상태**

#### 🛠 해결 방법

- `File > Invalidate Caches / Restart...` 실행 후 재시작

#### ✅ 결과

- 캐시 초기화 후, 의존성 정상 인식 및 import 성공

#### 🧠 인사이트

- 환경 문제는 코드가 아닌 도구 설정 이슈일 수 있음
---

###  ✅ 트러블슈팅 5 - `java.lang.ClassNotFoundException: oracle.jdbc.driver.OracleDriver`

#### ❗ 문제 상황

Oracle 드라이버 클래스를 찾을 수 없음

#### 🔍 원인 분석

- Oracle JDBC 드라이버에 대한 **의존성 누락**

#### 🛠 해결 방법

`pom.xml`에 다음 의존성 추가:

```xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc8</artifactId>
    <version>19.8.0.0</version>
</dependency>

```

#### ✅ 결과

- 빌드 성공 및 드라이버 정상 인식

#### 🧠 인사이트

- 외부 드라이버 의존성은 직접 명시해주지 않으면 자동 포함되지 않음
- Maven 프로젝트는 **`Reload All`*을 수시로 확인


## 📦 주요 기술 스택

| 분야                | 기술                                                        |
|---------------------|-------------------------------------------------------------|
| **Language**        | Java 17                                                     |
| **Framework**       | Spring Boot 2.7.18                                          |
| **ORM**             | MyBatis                                                |
| **Database**        | OracleDB, MongoDB                                           |
| **Security**        | Spring Security, JWT (JJWT 0.11.5)                          |
| **WebSocket**       | Spring WebSocket                                            |
| **Data Store**      | Redis                                                       |                 |
| **ML/추천엔진**     | Apache Spark (spark-core, spark-mllib)                      |
| **Validation**      | Hibernate Validator                                         |
| **Test**            | JUnit, Spring Security Test                                 |
| **Build Tool**      | Maven                                                       |

<br>

## ✨ Member
<div align="center">
  <table>
    <tr>
      <td align="center"><img src="https://avatars.githubusercontent.com/ldayun" width="100" height="100" /></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/somflower" width="100" height="100" /></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/yjin-jo" width="100" height="100" /></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/MinCodeHub" width="100" height="100" /></td>
    </tr>
    <tr>
      <td align="center"><a href="https://github.com/ldayun">이다윤</a></td>
      <td align="center"><a href="https://github.com/somflower">김미현</a></td>
      <td align="center"><a href="https://github.com/yjin-jo">조예진</a></td>
      <td align="center"><a href="https://github.com/MinCodeHub">허민영</a></td>
    </tr>
  </table>
</div>

