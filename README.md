Launch<br>
Edit Configuration -> + -> Spring Boot<br>
В конфигах MainClass:com.net.spring.SpringApplicationSimple<br>

Routes<br>
hosts:port/testDeploySpring/<br>
hosts:port/test_queue/ - When the application starts Producer send 2 message. Visited route /test_queue/ give 1 message<br>
in queue<br>

Deploy<br>
mvn clean install<br>

Location<br>
/var/lib/tomcat/webapps/<br>

---------------------------------------------------------------------<br>
*) sudo vi /opt/apache-tomcat-9.0.0.M21/conf/tomcat-users.xml<br>

<tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">
        <user username="*" password="*" roles="manager-gui,admin-gui"/>

*) sudo systemctl restart tomcat<br>

*) sudo tail -n 50 /opt/tomcat-latest/logs/catalina.out<br>

*) change port<br>
sudo vi /opt/tomcat-latest/conf/server.xml<br>


*) arxemond 

/var/lib/tomcat/webaaps
/etc/tomcat

