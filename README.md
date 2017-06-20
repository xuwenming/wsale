# bshoot
#test
#把本地jar包添加进maven
mvn install:install-file -Dfile=CCP_REST_SDK_JAVA_v2.7r.jar -DgroupId=com.implus.ccp -DartifactId=ccp-rest-sdk-java -Dversion=v2.7r -Dpackaging=jar
mvn install:install-file -Dfile=dbay-apns4j-1.0.jar -DgroupId=com.dbay.apns4j -DartifactId=dbay-apns4j -Dversion=1.0 -Dpackaging=jar