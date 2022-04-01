FROM openjdk:8-jdk-alpine
COPY . .
#./gradlew bootBuildImage --imageName=filipiakp/warehouse-spring
RUN ./gradlew build
COPY build/libs/*.jar app.jar
EXPOSE 8090
#ENTRYPOINT ["java","-jar","/app.jar"]
CMD ["java","-jar","/app.jar"]