echo >> stop current container
docker stop KD-acb

echo >> delete current container
docker rm KD-acb

echo >> delete old image
docker rmi myapp

echo >> create new jar
./gradlew clean build

echo >> create new image from recent jar
docker build --platform linux/amd64 --tag=myapp:latest .

echo >> run new container by new image
## - 외부에서 10002로 들어가면 docker 내에 10001에 접근
docker run -d --name KD-acb --network kd-network -p 10002:10001 myapp

## 디폴트로 만들어진 리눅스는 /etc/os-release 을 통해서 확인해보면 Alpine 리눅스 버전이고 해당 버전에서는 apk install ~~ 이러한 문법을 통해서 원하는걸 다운받는 것이 가능ㅎ

echo >> check created container
docker ps

echo >> check new health of container UP
curl -X GET localhost:10002/acb/actuator/health -v

echo >> check latest git commit
curl -X GET localhost:10002/acb/actuator/git -v
