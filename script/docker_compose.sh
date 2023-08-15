# making jar
./gradlew clean build

# create image
docker build --platform linux/amd64 --tag=myapp:latest .

# run container
#~~

# delete container
docker rm KD-acb

# image delete
docker rmi myapp