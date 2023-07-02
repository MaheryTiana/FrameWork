cd framework 
javac -d build *.java

cd build
jar -cf framework.jar .

cp framework.jar ../../test_Framework/build/web/WEB-INF/lib/framework.jar

cd ../../test_Framework/build/web/

jar -cf /home/mahery/fianarana/tomcat/webapps/test_Framework.war .
