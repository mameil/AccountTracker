echo >> stop current container
docker stop KD-acb
sleep 1

echo >> delete current container
docker rm KD-acb
sleep 1

echo >> delete old image
docker rmi myapp
sleep 1

cd ../

echo >> create new jar
./gradlew clean build
sleep 1

echo >> create new image from recent jar
docker build --platform linux/amd64 --tag=myapp:latest .
sleep 1

echo >> run new container by new image
## - 외부에서 10002로 들어가면 docker 내에 10001에 접근
docker run -d --name KD-acb --network kd-network -p 10002:10001 -v /Users/kyudoshim/IdeaProjects/AccountTracker/src/main/resources/docker:/config myapp
sleep 10

## 디폴트로 만들어진 리눅스는 /etc/os-release 을 통해서 확인해보면 Alpine 리눅스 버전이고
## 해당 리눅스에서는 apk 명령어를 통해서 패키지를 설치할 수 있다.
## apk info 를 통해서 설치되어있는 패키지를 확인
## apk add ~~ 을 통해서 원하는 패키지를 설정해줘야함

echo >> check created container
docker ps

echo >> check new health of container UP
curl -X GET localhost:10002/acb/actuator/health -v

echo >> check latest git commit
curl -X GET localhost:10002/acb/actuator/git -v
