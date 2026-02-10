FROM node:22-alpine

LABEL maintainer="Boby"
LABEL version="1.0"
LABEL description="Basilisk Web Client-Side"

WORKDIR /app

COPY dist/ dist/

RUN npm install -g http-server

EXPOSE 8080

ENTRYPOINT [ "npx", "http-server", "dist" ]