FROM ubuntu:latest
LABEL maintainer="boby <boby.widjaja@gmail.com>"
RUN apt-get update && apt-get install -y cowsay fortune
RUN rm -Rf /var/lib/apt/lists/*
RUN rm -Rf /usr/share/games/fortunes/*
COPY pesanku /usr/share/games/fortunes/pesanku
RUN chmod 544 /usr/share/games/fortunes/pesanku
RUN strfile /usr/share/games/fortunes/pesanku
CMD /usr/games/cowsay $(/usr/games/fortune)