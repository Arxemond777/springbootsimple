Launch
Edit Configuration -> + -> Spring Boot
В конфигах MainClass:com.net.spring.SpringApplicationSimple

Routes
hosts:port/testDeploySpring/

Deploy
mvn clean install

Location
/var/lib/tomcat/webapps/

---------------------------------------------------------------------
*) sudo vi /opt/apache-tomcat-9.0.0.M21/conf/tomcat-users.xml

<tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">
        <user username="*" password="*" roles="manager-gui,admin-gui"/>

*) sudo systemctl restart tomcat

*) sudo tail -n 50 /opt/tomcat-latest/logs/catalina.out

*) change port
sudo vi /opt/tomcat-latest/conf/server.xml
