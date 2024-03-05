IMAGE_FILE_PATH="/home/ubuntu/deploy/image.txt"
IMAGE_NAME=$(cat "$IMAGE_FILE_PATH")
CONTAINER_ENV_PATH="/home/ubuntu/trip.env"
SERVICE_NAME=tweaver

# Docker Compose YAML을 새로운 도커 버전으로 작성해서 저장
echo "version: '3.9'
services:

  tweaver-app:
    build:
      context: .
      dockerfile: Dockerfile
    environment:
      - TZ=Asia/Seoul
    ports:
      - 8080:8080
    image: ${IMAGE_NAME}
    env_file:
      - ${CONTAINER_ENV_PATH}
    depends_on:
      - redis

  redis:
    image: redis
    command: redis-server --port 6379
    container_name: tweaver-redis
    hostname: tweaver-redis
    labels:
      - "name=tweaver-redis"
      - "mode=standalone"
    ports:
      - 6379:6379

networks:
  default:
    external: true
    name: tweaver-network" > docker-compose.yml


# 새로운 도커 컨테이너 실행
echo "IMAGE_NAME: $IMAGE_NAME 도커 실행"
docker-compose up -d $SERVICE_NAME