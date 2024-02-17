# Build Stage
FROM maven:3.8.4-openjdk-17-slim as online-bakery-store-backend
# FROM maven:3.8.4-openjdk-17-slim 
WORKDIR /usr/online-bakery-store-backend
COPY . .
RUN pwd
RUN ls
RUN mvn clean install -DskipTests

# Run Stage
FROM openjdk:17-slim
WORKDIR /usr/online-bakery-store-backend/target
COPY --from=online-bakery-store-backend /usr/online-bakery-store-backend/target/Online-Bakery-Store-Backend-0.0.1.jar /usr/online-bakery-store-backend/target
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/online-bakery-store-backend/target/Online-Bakery-Store-Backend-0.0.1.jar"]

# FROM maven:3.8.4-openjdk-17-slim
# EXPOSE 8080
# ADD target/Online-Bakery-Store-Backend-0.0.1.jar Online-Bakery-Store-Backend-0.0.1.jar
# ENTRYPOINT ["java","-jar","/Online-Bakery-Store-Backend-0.0.1.jar"]
# CMD [ "ls" ]