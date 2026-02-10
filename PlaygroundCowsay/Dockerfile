FROM ubuntu:latest
LABEL maintainer="boby <boby.widjaja@gmail.com>"
RUN apt-get update && apt-get install -y cowsay
RUN rm -Rf /var/lib/apt/lists/*
ENTRYPOINT [ "/usr/games/cowsay", "-f", "tux" ]
CMD ["Halo, Ini Dockerfile pertamaku"]