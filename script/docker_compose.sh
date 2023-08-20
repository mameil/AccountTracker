# making jar
./gradlew clean build

# create image
docker build --platform linux/amd64 --tag=myapp:latest .

# run container - 외부에서 10002로 들어가면 docker 내에 10001에 접근
docker run -d --name KD-acb --network kd-network -p 10002:10001 myapp

# delete container
docker rm KD-acb

# image delete
docker rmi myapp
