FROM ubuntu:latest
LABEL maintainer="boby <boby.widjaja@gmail.com>"
RUN apt-get update && apt-get install -y cowsay fortune
RUN rm -Rf /var/lib/apt/lists/*
CMD /usr/games/cowsay -f dragon $(/usr/games/fortune)