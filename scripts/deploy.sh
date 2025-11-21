#!/bin/bash
set -e

# 설정 변수
DOCKER_COMPOSE_DIR="/home/ubuntu/dorm"
NGINX_HOST_CONF_PATH="$DOCKER_COMPOSE_DIR/configs/nginx/nginx.conf"
MAX_RETRIES=10
RETRY_INTERVAL=10

# --- 1. 현재 Active 서버 확인 및 Target 서버 결정 ---

# nginx 호스트 설정 파일에서 현재 Active 상태인 서버를 찾습니다.
CURRENT_SERVER=$(grep -Eo 'spring-(blue|green)' $NGINX_HOST_CONF_PATH | head -1 || echo "spring-blue")
echo "--- 1. 서버 상태 확인 ---"
echo "현재 Active 서버: ${CURRENT_SERVER}"

if [ "$CURRENT_SERVER" == "spring-blue" ]; then
    TARGET_SERVER="spring-green"
    CURRENT_PORT="8080"
    TARGET_PORT="8081"
else
    TARGET_SERVER="spring-blue"
    CURRENT_PORT="8081"
    TARGET_PORT="8080"
fi

echo "배포할 Target 서버: $TARGET_SERVER (호스트 포트 $TARGET_PORT)"

# --- 2. Target 서버 배포 (새 이미지로 교체) ---

echo "--- 2. ${TARGET_SERVER} 서버 재배포 및 이미지 pull ---"

# docker-compose 실행 경로 변경
cd $DOCKER_COMPOSE_DIR

# Target 서버만 재배포
docker compose up -d --pull always $TARGET_SERVER

# --- 3. Target 서버 헬스 체크 ---

echo "--- 3. ${TARGET_SERVER} 헬스 체크 시작 (최대 ${MAX_RETRIES}회) ---"

for i in {1..$MAX_RETRIES}; do
    # 호스트 머신의 TARGET_PORT를 사용하여 새 컨테이너의 헬스 체크 엔드포인트에 접근
    STATUS_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:${TARGET_PORT}/health)

    if [[ "$STATUS_CODE" == "200" || "$STATUS_CODE" == "401" ]]; then
        echo "헬스 체크 성공 (${TARGET_SERVER}): HTTP $STATUS_CODE"
        break
    else
        echo "헬스 체크 실패: HTTP $STATUS_CODE. ${RETRY_INTERVAL}초 후 재시도 ($i/${MAX_RETRIES})"
        if [ $i -eq $MAX_RETRIES ]; then
            echo "ERROR: ${TARGET_SERVER} 서버가 시작되지 않았습니다. 배포를 중단합니다."
            docker compose logs $TARGET_SERVER
            exit 1
        fi
        sleep $RETRY_INTERVAL
    fi
done

# --- 4. Nginx 설정 파일 교체 및 리로드 ---

echo "--- 4. Nginx 설정 파일 교체 및 리로드 ---"

# 호스트에 있는 원본 Nginx 설정 파일을 Target 서버로 수정
# 'spring-blue:8080;' 또는 'spring-green:8080;' 패턴을 TARGET_SERVER로 변경
sed -i "s/server spring-.\{1,5\}:8080;/server $TARGET_SERVER:8080;/" $NGINX_HOST_CONF_PATH

# Nginx 설정 리로드 (컨테이너 내부에 명령 전달)
docker exec nginx nginx -s reload

echo "트래픽이 $TARGET_SERVER 로 성공적으로 전환되었습니다."

# --- 5. 이전 Active 서버 중지 (선택 사항) ---

echo "--- 5. 이전 Active 서버 (${CURRENT_SERVER}) 정리 ---"

if [ "$CURRENT_SERVER" != "NONE" ]; then
    docker compose stop $CURRENT_SERVER
    # docker compose rm -f $CURRENT_SERVER # 필요하다면 제거
    echo "이전 서버 ${CURRENT_SERVER} 중지 완료."
fi

echo "--- 배포 완료: 현재 Active 서버는 $TARGET_SERVER 입니다 ---"