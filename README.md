# 관생자치회 통합관리시스템

## 소개

강원대학교 기숙사 관생자치회 어드민페이지입니다.

기존의 복잡하고 분산되어 있는 스프레드시트를 대체하기 위하여 개발하였습니다.

학습용 1인 개발 프로젝트로 부족한 부분이 많습니다 ㅠ

## 주요 기능

RestAPI로 동작합니다. ([프론트엔드](https://github.com/hyotatoFrappuccino/dormManagementSystem-front))

only 구글 계정으로 로그인(OAuth2, JWT)으로 구현하였습니다.

* 대시보드 : 현재 냉장고 신청 회차의 건물별 이용자수를 한눈에 파악할 수 있습니다.
  ![](https://raw.githubusercontent.com/hyotatoFrappuccino/image/refs/heads/main/1.jpg?token=GHSAT0AAAAAADGOCGBWVW4B3I6DXLKAED4G2C73P3A)
* 냉장고 신청/연장 : 간편하게 관생의 냉장고 신규 신청, 연장 신청을 할 수 있습니다.
  * (* 화면에 표시되는 모든 개인정보는 가상으로 생성된 데이터로, 실제 사용자의 데이터가 아닙니다 *)
  ![](https://raw.githubusercontent.com/hyotatoFrappuccino/image/refs/heads/main/2.jpg?token=GHSAT0AAAAAADGOCGBXWTOIR55G6IHTYUMQ2C73Q3Q)
* 냉장고 관리 : 냉장고 신청 목록을 한눈에 파악할 수 있으며, 필터와 경고 기능을 통해 관리할 수 있습니다.
  ![](https://raw.githubusercontent.com/hyotatoFrappuccino/image/refs/heads/main/3.jpg?token=GHSAT0AAAAAADGOCGBX6NU3P3GY7X7PGU7W2C73RQA)
* 납부자 관리 : 납부자 목록을 한눈에 파악할 수 있으며, 해당 납부자의 사업 참여 여부를 관리할 수 있습니다.
  ![](https://raw.githubusercontent.com/hyotatoFrappuccino/image/refs/heads/main/4.jpg?token=GHSAT0AAAAAADGOCGBW7NGT4Y7BQY6UIXYW2C73RYA)
* 서약서 관리 : 구글 설문지와 연동하여 관생은 편리하게 기존의 설문지를 이용하여 서약서를 제출하고, 불러오기를 통해 실시간으로 연동이 가능합니다.
  ![](https://raw.githubusercontent.com/hyotatoFrappuccino/image/refs/heads/main/5.jpg?token=GHSAT0AAAAAADGOCGBWXKX47NOSN6EEFJBE2C73R7A)
* 설정 : 기본 납부 금액, 서약서 구글 시트 ID, 납부자/서약서 목록 CSV 내보내기, 전체 초기화 기능, [건물, 관리자, 회차, 사업] 관리 기능 제공
  ![](https://raw.githubusercontent.com/hyotatoFrappuccino/image/refs/heads/main/6.jpg?token=GHSAT0AAAAAADGOCGBWC3JMFH4ZOYGELHBM2C73SJA)

## 아키텍처

프론트엔드 <-> [Docker] 백엔드(Spring Boot) <-> [Local] mysql, [Docker] redis, prometheus, grafana

테스트코드, CI/CD 추가 예정

## 기술스택

Spring [Boot, Security, JPA], Redis, MySQL, Docker, Prometheus, Grafana
