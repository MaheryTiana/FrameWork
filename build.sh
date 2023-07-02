cd framework 
javac -d build *.java

cd build
jar -cf framework.jar .

cp framework.jar ../../testFramework/build/web/WEB-INF/lib/framework.jar

cd ../../testFramework/build/web/

jar -cf /home/mahery/fianarana/tomcat/webapps/testFramework.war .
