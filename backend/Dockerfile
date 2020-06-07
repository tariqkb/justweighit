FROM java:13 AS builder

RUN ./gradlew installDist

RUN ./b

FROM java:13

COPY --from=builder backend/build /server

CMD ./backend