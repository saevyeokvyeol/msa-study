# 레디스 도커 설정

```docker pull redis```
- 레디스 최신 버전 이미지를 로컬 호스트에 내려받음

```docker run --name spring-tour-redis -p 6379:6379 redis```
- 컨테이너 실행
- 이 때 이름은 spring-tour-redis이고, 레디스 컨테이너의 6379번 포트와 로컬 호스트의 6379번 포트를 연결
- 어플리케이션에서 6379를 사용하면 도커 레디스와 연결 가능

```docker start spring-tour-redis```
- spring-tour-redis 실행

```docker exec -it spring-tour-redis /bin/bash```
- 백그라운드에 실행되고 있는 컨테이너(spring-tour-redis) 내부에 접속

```root@5728934be4a9:/data# redis-cli -h 127.0.0.1```
- 도커 내부에서 레디스 커맨드 모드로 접속하는 명령어
- 127.0.0.1 호스트로 접속

```127.0.0.1:6379> SET hotel:billing-code:1 "123456789"```
- hotel:billing-code:1 키에 "123456789" 밸류값을 저장

```127.0.0.1:6379> GET hotel:billing-code:1```
- hotel:billing-code:1 키값의 밸류인 "123456789" 출력

```127.0.0.1:6379> KEYS *```
- 저장된 모든 키값을 출력함

```127.0.0.1:6379> quit```
- 레디스 커맨드 모드 나가기