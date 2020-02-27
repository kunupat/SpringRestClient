FROM amazoncorretto:8
ARG DEPENDENCY=target/dependency

COPY ./main /app/bin/
RUN chmod 777 /app/bin/main 

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.test.Main"]
