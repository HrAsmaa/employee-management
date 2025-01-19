FROM openjdk:17-oracle
COPY target/employeemanagement-0.0.1-SNAPSHOT.jar employeemanagement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/employeemanagement-0.0.1-SNAPSHOT.jar"]