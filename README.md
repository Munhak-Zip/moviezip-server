
<p align="center">
  <img src="https://github.com/Munhak-Zip/Moviezip_Back/assets/110006845/e8d64401-4e07-45d5-abd1-923abf6a17d4" alt="화면 캡처 2024-07-08 231353">
</p>
<br>

## [💻 WEB] 영화 리뷰 모음, 예매 및 추천 웹사이트, MOVIE.ZIP (2024.03 ~ 2024.06)
<p align="center">
  <img src="https://github.com/Munhak-Zip/Moviezip_Back/assets/110006845/8f2ead82-be3e-4fb0-a2ec-04430d1a8cf5">
</p>
<br>

| ![영화 예매 설정 화면](https://github.com/user-attachments/assets/e3080f6e-82c9-4fe0-ab25-55e128e0a4b5) | ![영화 좌석 예매 화면](https://github.com/user-attachments/assets/2926e821-97ce-4103-b568-29c8af55967d) | ![영화 상세 리뷰 화면](https://github.com/user-attachments/assets/ee3582b9-afdf-48ba-8d2a-c5ba1b4b5641) |
| :---: | :---: | :---: |
| **영화 예매 설정 화면** | **영화 좌석 예매 화면** | **영화 상세 리뷰 화면** |
| ![채팅방 화면](https://github.com/user-attachments/assets/ccebfa2e-184d-484f-b788-2515307da38f) | ![채팅 내역 화면](https://github.com/user-attachments/assets/9e52b7b0-c7bb-4d9c-a060-97cbc7f86502) | ![영화 리뷰 화면](https://github.com/user-attachments/assets/17beb7db-72dd-44b3-a3d7-e6d1843b347f) |
| **채팅방 화면** | **채팅 내역 화면** | **영화 리뷰 화면** |
| ![아이디 찾기 화면](https://github.com/user-attachments/assets/2789237d-13d2-4b98-b357-dd4b8b28f16a) | ![비밀번호 찾기 화면](https://github.com/user-attachments/assets/395dcb43-25e9-4ff5-a900-3c3c3177f416) | ![마이 페이지 화면](https://github.com/user-attachments/assets/fa6c9663-6045-4392-866f-e8a08fd8e2b9) |
| **아이디 찾기 화면** | **비밀번호 찾기 화면** | **마이 페이지 화면** |
| ![결제 화면](https://github.com/user-attachments/assets/5f7e837b-b67b-45f9-9ba4-3e9d9f322411) |
| **결제 화면** |





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

