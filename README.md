# Instructions to run the Application
1.	Unzip the .rar file and open/import the project to IntelliJ IDEA or eclipse.
2.	cd spring-boot-rest-api-user-details\spring-boot-rest-api-user-details to go to the inner project folder.
3.	Import maven dependencies and setup JDK as required.
4.	Run below command to start the application
mvn spring-boot:run
5.	The server is running at port 8089
You can access h2 database at http://localhost:8089/h2/
with username: sa and password:sa
6.	You can access hystrix dashboard at http://localhost:8089/hystrix/

Then enter http://localhost:8089/actuator/hystrix.stream at the url location and click Monitor Stream.
 

7.	Import the User Details.postman_collection.json to Postman
 
8.	Run Authentication Failure end point first to check the authentication service which will provide authentication error details.
9.	Then run Create Users to add users to h2 database, 5 users added, you can add more if you want.
10.	Then you can run Get Users to get the user details.
11.	Then run Update User to update the user.
12.	Then run User Id Validation to validate User ID
13.	Then run Exception Handling to see error details for any error in the application.
14.	Change below properties in the application.properties to check for Circuit Breaker pattern which will return default user details.
#Comment below line to check circuit breaker
spring.jpa.hibernate.ddl-auto=create-drop

#Uncomment below line to check circuit breaker
#spring.jpa.hibernate.ddl-auto=none

Ad devtools implemented so the application will auto-start then run Get Users Circuit Breaker to get the default user details.
15.	You can run mvn clean test  and check the console for Pact to be passed and Junits to run successfully.
16.	The Consumer Pact json is in target/classes/pacts/user_details_consumer-user_details_provider.json

To generate the consumer pact json you can run the UserDetailsServiceConsumerPactTest to generate the json file and verify.
17.	For Circuit Breaker access
 
