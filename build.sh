cd framework 
javac -d build *.java

cd build
jar -cf framework.jar .

cp framework.jar ../../testFrame/build/web/WEB-INF/lib/framework.jar

cd ../../testFrame/build/web/

jar -cf /home/mahery/fianarana/tomcat/webapps/testFrame.war .
