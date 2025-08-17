docker build --platform=linux/arm64 -f docker/Dockerfile.prod -t photonvision:latest .
docker save -o img.tar photonvision:latest
