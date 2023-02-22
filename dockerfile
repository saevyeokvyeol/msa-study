# 도커 이미지 중 openjdk 11 버전이 설치된 도커 이미지 사용
FROM adoptopenjdk/openjdk11:latest

# root 디렉토리에 logs 폴더 생성
RUN mkdir -p /logs

# JAR_FILE 변수를 선언하고 dockerfile을 실행할 서버의 target 디렉토리에 있는 모든 jar 파일을 선언 -> 도커 이미지 파일로 복사
ARG JAR_FILE=target/*.jar
# 위에서 선언한 jar 파일을 도커 이미지 파일로 복사
# 복사된 파일 이름은 app.jar으로 명명
COPY ${JAR_FILE} app.jar
# 복사된 app.jar를 실행하는 명령어
ENTRYPOINT ["java", "-Dspring.profiles.active=dev,email","-jar","/app.jar"]

# 해당 파일(dockerfile)이 있는 위치에서 아래 명령어 사용 시 해당 파일을 이용해 도커 이미지 생성
# docker build --tag (이미지 이름):(태그 이름) .

# 생성된 도커 이미지 정보 조회 명령어
# docker images

# 도커 이미지 파일 실행
# 도커 컨테이터를 백그라운드에서 실행(-d)한 뒤, 종료하면 이미지 삭제(--rm)
# 도커 포트 번호와 호스트 서버 포트 번호 연결
# 작성한 이미지 이름과 태그 이름에 일치하는 도커 이미지 실행
# docker run -d --rm -p (도커 포트 번호):(호스트 서버 포트 번호) (이미지 이름):(태그 이름)

# 도커 파일에서 사용할 수 있는 명령어
#FROM: 생성할 도커 이미지의 기본 이미지 설정
#      해당 이미지 설정으로 서버를 설정
#LABEL: 이미지에 메타 데이터 설정
#RUN: 도커 컨테이너 내부에서 실행할 명령어를 정의
#COPY: 도커 파일이 실행되는 호스트 서버 파일을 도커 컨테이너로 복사함
#ARG: 도커 파일 변수 설정
#ADD: host 파일 및 디렉토리를 도커 컨테이너에 추가
#     복사 대상이 압축 파일이면 압축 해제되어 복사(COPY와의 차이점)
#VOLUME: 도커 컨테이너에 연결할 host 디렉토리
#EXPOSE: 외부 노출 포트 설정
#CMD: 도커 컨테이너에서 실행할 프로세스 설정
#     한 번만 실행 가능(RUN과의 차이점)
#ENTRYPOINT: 도커 컨테이너에서 실행할 프로세스 지정