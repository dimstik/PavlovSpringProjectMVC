# Используем официальный образ Tomcat
FROM tomcat:9.0-jdk8-openjdk

# Копируем WAR-файл в директорию развертывания Tomcat
COPY target/spring-mvc-app1.war /usr/local/tomcat/webapps/
