# 관생자치회 통합관리시스템

[소개](#소개)

[프로젝트 설정 가이드](#프로젝트-설정-가이드)

## 소개

강원대학교 기숙사 관생자치회 어드민 페이지입니다.

기존의 복잡하고 분산되어 있는 스프레드시트를 대체하기 위하여 개발하였습니다.

## 아키텍처

프론트엔드 <-> [AWS]-[Docker] 백엔드(Spring Boot) <-> [Local] mysql, [Docker] redis, prometheus, grafana


<img width="250" height="250" alt="제목 없는 다이어그램 drawio" src="https://github.com/user-attachments/assets/f82e3aa0-a94c-44aa-93ce-3929568bb397" />

## 기술스택

Spring [Boot, Security, JPA], Redis, MySQL, Docker, Prometheus, Grafana


## 주요 기능

RestAPI로 동작합니다. ([프론트엔드](https://github.com/hyotatoFrappuccino/dormManagementSystem-front))

only 구글 계정으로 로그인(OAuth2, JWT)으로 구현하였습니다.

* 대시보드 : 현재 냉장고 신청 회차의 건물별 이용자수를 한눈에 파악할 수 있습니다.
![1](https://github.com/user-attachments/assets/0a702a64-96e6-43eb-8036-4518932780b2)
* 냉장고 신청/연장 : 간편하게 관생의 냉장고 신규 신청, 연장 신청을 할 수 있습니다.
    * (* 화면에 표시되는 모든 개인정보는 가상으로 생성된 데이터로, 실제 사용자의 데이터가 아닙니다 *)
      ![2](https://github.com/user-attachments/assets/b0c01b8a-1c82-4e5d-9002-9250e4e38b71)

* 냉장고 관리 : 냉장고 신청 목록을 한눈에 파악할 수 있으며, 필터와 경고 기능을 통해 관리할 수 있습니다.
  ![3](https://github.com/user-attachments/assets/7449e2a4-0a91-4b09-b4f5-b3e91bb6e305)
* 납부자 관리 : 납부자 목록을 한눈에 파악할 수 있으며, 해당 납부자의 사업 참여 여부를 관리할 수 있습니다.
  ![4](https://github.com/user-attachments/assets/059fc8be-f40d-4e93-876f-dcc24254778c)
* 서약서 관리 : 구글 설문지와 연동하여 관생은 편리하게 기존의 설문지를 이용하여 서약서를 제출하고, 불러오기를 통해 실시간으로 연동이 가능합니다.
  ![5](https://github.com/user-attachments/assets/1cf1ef90-726c-4a71-a055-29343a7b422a)
* 설정 : 기본 납부 금액, 서약서 구글 시트 ID, 납부자/서약서 목록 CSV 내보내기, 전체 초기화 기능, [건물, 관리자, 회차, 사업] 관리 기능 제공
  ![6](https://github.com/user-attachments/assets/e4cd9292-9225-447b-809e-ca58c3deaf73)

# 프로젝트 설정 가이드

이 프로젝트는 Spring Boot, Docker, GitHub Actions를 사용하여 CI/CD가 구성되어 있습니다. 전체 설정은 아래와 같은 여러 부분으로 나뉘어 관리됩니다.

### 1. Spring Boot 설정 (`application.yml` 파일들)

-   **`application.yml`**: 모든 환경에서 공통으로 사용되는 설정입니다.
-   **`application-local.yml`**: 로컬 환경에서 사용되는 설정입니다. 파일은 Github에 업로드되지 않고 별도로 관리됩니다.
-   **`application-dev.yml`**: 개발(`dev`) 환경에서만 사용되는 설정입니다.
-   **`application-prod.yml`**: 운영(`main`) 환경에서만 사용되는 설정입니다.

> **Note**: 이 파일들의 실제 내용은 보안을 위해 GitHub Secrets에 Base64로 인코딩되어 저장됩니다. (`application.yml`:`APP_SECRET_COMMON`, 환경별`application-*.yml``APP_SECRET_PROFILE`)

### 2. Docker 설정 (`Dockerfile`, `docker-compose.yml`)

-   **`Dockerfile`**
-   **`docker-compose.yml`**: 배포 서버에서 Spring Boot, Redis 등 서비스들을 실행하는 방법을 정의합니다. 이 파일은 CI/CD 과정에서 서버로 복사됩니다.

### 3. CI/CD 설정 (`.github/workflows`)

-   **`build-image.yml`**: Gradle 빌드 및 Docker 이미지 빌드 후 GHCR(GitHub Container Registry)에 푸시하는 역할을 담당합니다.
-   **`deploy.yml`**: `dev` 브랜치나 `v*.*.*` 태그(`main` 브랜치) 푸시에 실행됩니다. `build-image.yml`을 호출하여 빌드를 수행한 뒤, 적절한 환경(`dev` 또는 `main`)을 선택하여 서버에 배포합니다.

### 4. GitHub Secrets 및 Variables 설정

이 프로젝트가 올바르게 동작하려면 아래의 Secret과 Variable들이 GitHub에 설정되어 있어야 합니다.

| 종류           | 범위                  | 이름                   | 설명                                                         |
|:-------------|:--------------------|:---------------------|:-----------------------------------------------------------|
| **Secret**   | Repository          | `APPLICATION_SECRET` | 공통 `application.yml` 파일의 Base64 인코딩 값                      |
| **Secret**   | Repository          | `GOOGLE_CREDENTIALS` | Google Cloud Console OAuth 2.0 Client ID의 credentials.json |
| **Secret**   | Repository          | `TOKEN_GITHUB`       | Github Container Registry Package 업로드에 사용되는 Github 토큰 값    |
| **Secret**   | Environment (`dev`) | `APP_SECRET_PROFILE` | `application-dev.yml` 파일의 Base64 인코딩 값                     |
| **Secret**   | Environment (`dev`) | `HOST`               | 개발 서버 IP 주소                                                |
| **Secret**   | Environment (`dev`) | `PORT`               | 개발 서버 포트                                                   |
| **Secret**   | Environment (`dev`) | `PASSPHRASE`         | (선택) 개발 서버 PASSPHRASE                                      |
| **Secret**   | Environment (`dev`) | `USERNAME`           | 개발 서버 유저명                                                  |
| **Secret**   | Environment (`dev`) | `KEY`                | 개발 서버 접속용 Private SSH Key                                  |
| **Secret**   | Environment (`dev`) | `KEYSTORE`           | 인증서 keystore.p12 파일의 Base64 인코딩 값(KEY와 별도의 파일임에 유의)        |
| **Secret**   | Environment (`dev`) | `ENV`                | UID=0\nGID=0                                               |
| **Variable** | Environment (`dev`) | `SPRING_PROFILE`     | `dev`                                                      |

main도 dev와 동일하나, SPRING_PROFILE을 prod로 설정.

### 5. 서버 초기 설정

새로운 배포 서버를 설정할 때, CI/CD가 동작하기 전에 아래 작업들이 **최초 1회** 수행되어야 합니다.

1. initial_setup.sh을 backup.sh, create.sql 파일과 동일한 폴더에 둔 후 실행
2. **GHCR 로그인**: `docker login ghcr.io` 명령어를 실행하여 서버가 GHCR에서 이미지를 pull할 수 있도록 인증 정보를 저장합니다.
3. Github Actions을 통해 배포
