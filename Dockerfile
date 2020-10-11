FROM openjdk:14-jdk AS builder

COPY .gradle /src/.gradle
COPY gradle /src/gradle
COPY gradlew /src/gradlew
WORKDIR /src
RUN ./gradlew --no-daemon

COPY . /src
RUN ./gradlew --no-daemon backend:installDist

FROM openjdk:14-jdk

COPY --from=builder /src/backend/build/install/justweighit /jwi

WORKDIR jwi
CMD ./bin/backend server config.yml