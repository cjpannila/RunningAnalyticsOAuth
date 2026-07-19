# Running Analytics OAuth
Auth code extraction for running analytics system

Authorization URL
https://www.strava.com/oauth/authorize?client_id=218954&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=read,activity:read_all

Build with maven
<code>
mvn clean install
</code>

How to package
<code>
mvn clean package
run the application with the following command:
java -jar target/runanalytics-oauth-0.0.1-SNAPSHOT.jar
</code>

Docker build and run
<code>
docker build -t runanalytics-oauth .
docker run -p 8080:8080 runanalytics-oauth
</code>

Rebuild after code change
<code>
docker build --no-cache -t runanalytics-oauth .
</code>

Stop old container
<code>
docker ps
docker stop <container_id>
docker run --rm -p 8080:8080 runanalytics-oauth .
</code>

