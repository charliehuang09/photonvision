chmod -R +x .gradle
docker run --rm -it --privileged=true -v /Users/charlie/Documents/photonvision:/photonvision:Z photonvision_build:1.0 ./gradlew shadowJar
