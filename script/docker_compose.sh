# stop current container
docker stop KD-acb

# delete container
docker rm KD-acb

# image delete
docker rmi myapp

# make new jar
./gradlew clean build

# create new image from recent jar
docker build --platform linux/amd64 --tag=myapp:latest .

# run container - 외부에서 10002로 들어가면 docker 내에 10001에 접근
docker run -d --name KD-acb --network kd-network -p 10002:10001 myapp

# check created container
docker ps

# check health UP
curl -X GET localhost:10002/acb/actuator/health -v

# check latest git commit
curl -X GET localhost:10002/acb/actuator/git -v
