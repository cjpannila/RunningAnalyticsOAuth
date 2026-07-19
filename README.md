# Running Analytics OAuth
Auth code extraction for running analytics system

Authorization URL
https://www.strava.com/oauth/authorize?client_id=218954&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=read,activity:read_all

Build with maven
mvn clean install

How to package
mvn clean package
run the application with the following command:
java -jar target/runanalytics-oauth-0.0.1-SNAPSHOT.jar
