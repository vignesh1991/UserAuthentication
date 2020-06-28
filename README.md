# Token-based API authentication with Spring and JWT

Spring Boot with h2. 

####Testing our API using postman
Once we have implemented the authentication and authorization logic, we will retest our API.

To do so, we can use Postman, a simple Chrome extension that allows us to execute and monitor requests.

We start our server by executing the command “mvn spring-boot:run”

1.From Postman, we make a GET request to /service/ and verify that it gives us a 403, since the resource is protected

2.From Postman, we make a POST request to /user to authenticate, including username and password, and we obtain an access token:

3.We make the GET request again from step 1, including an Authorization with the token generated in step 3
