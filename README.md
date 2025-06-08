
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
<p align="center">
  <img src="https://github.com/user-attachments/assets/a3cc4810-ff4c-48a0-b5f1-60f983a3b757">
</p>

채팅 기능은 Mongo DB를 사용했지만, 가독성을 위해 ERD 형식으로 시각화하였습니다.<br/>
Oracle 기반으로 기존 설계되었던 데이터베이스와의 직접적인 연동은 없습니다.

<br>

### 검색 성능 개선 시도

| 구분           | 인덱스 적용 전 | 인덱스 적용 후 |
| -------------- | :-----------: | :-----------: |
| 실행 시간      |    0.14초     |    0.09초     |
| 실행 결과 이미지 | ![image (5)](https://github.com/user-attachments/assets/7119df66-3973-4420-9cab-fcbffd9fe28a)| ![image (6)](https://github.com/user-attachments/assets/ddce3e5e-6e98-406f-a1ba-9f3ef5b47ce8) |

검색 쿼리의 성능을 개선하고자 오라클 기본 인덱스를 적용해보았습니다.  
약 8만 건의 데이터 중 80건을 검색하는 쿼리에서, 인덱스 적용 전 0.14초, 적용 후 0.09초로 약 36%의 속도 개선이 있었으나 실제 사용자 입장에서는 큰 체감이 어려운 수준이었습니다.


### 적용 과정에서의 문제 인식 및 원인 분석

#### 인덱스 적용과 옵티마이저 동작 분석

인덱스를 적용했음에도 불구하고, 옵티마이저가 풀 테이블 스캔을 선택한 이유를 분석했습니다.

검색 조건의 선택도(카디널리티)가 5% 이상으로 높아, 옵티마이저가 비용 기반 판단에 따라 인덱스 대신 풀스캔을 선택한 것을 확인했습니다.

### 해결 시도 및 결과

검색 조건을 조정하여 선택도를 0.1% 이하로 낮추자, 옵티마이저가 인덱스 스캔을 사용함을 확인할 수 있었습니다.

이때 쿼리 실행 시간이 0.09초로 개선되는 것을 확인하며, 실제 데이터와 조건에 따라 인덱스 효율이 크게 달라질 수 있음을 체감했습니다.


### 이번 경험을 통해  
- 인덱스가 대용량 데이터나 특정 조건에서 더 큰 효과를 보인다는 점  
- 옵티마이저가 선택도를 고려해 인덱스 사용 여부를 결정한다는 점  
등 데이터베이스 성능 최적화의 원리를 직접 실험하며 배울 수 있었습니다.


### 📦 주요 기술 스택

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

