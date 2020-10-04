FROM openjdk:14-jdk AS builder

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon backend:installDist

FROM openjdk:14-jdk

COPY --from=builder /src/backend/build/install/justweighit /jwi

WORKDIR jwi
CMD ./bin/backend server config.yml