FROM openjdk:8
EXPOSE 8080
ADD target/devsecopsapplication.jar devsecopsapplication.jar
ENTRYPOINT ["java","-jar","/devsecopsapplication.jar"]
