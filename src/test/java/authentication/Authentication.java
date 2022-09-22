package authentication;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class Authentication {
 
	@Test
  public void digest_authentication() {
		 given()
		  	.baseUri("http://postman-echo.com")
		  	.auth().digest("postman", "password"). 	//provide credentials, username password
		  when()
		  	.get("/digest-auth").
		  then()
		  	.log().all()
		  	.statusCode(200);  
  }
	
	@Test
	public void basic_authentication() {
		 given()
		  	.baseUri("http://postman-echo.com")
		  	.auth().basic("postman", "password"). 	//provide credentials, username password
		  	//.auth().preemptive().basic("postman", "password").  // if we want to use "Preemptive" is necessary specify it, if not by default is "Challenged"
		  when()
		  	.get("/digest-auth").
		  then()
		  	.log().all()
		  	.statusCode(200);  
 }
	
	@Test
	public void OAuth1() {
		
		String tweet = "This is a test in twitter from Rest Assured";
		
		 given()
		  	.baseUri("https://api.twitter.com/1.1")
		  	.auth().oauth(
		  			"uJ6haiHUTq0LEqpw",	//consumerkey
		  			"lsJDd54SD23AS",	//consumerSecret
		  			"l3ksd6534sfnf21",	//accessToken
		  			"24s5g4hjsnfg48f")	//secretToken
		  	.param("status",tweet).
		  when()
		  	.get("/statuses/update.json").
		  then()
		  	.log().all()
		  	.statusCode(200);  
 }
	
	@Test
	public void OAuth2() {
		
		//testing OAuth2 with Paypal API
		
		//first request is to get the access token
		 String accessToken = given()
		  	.baseUri("https://api.sandbox.paypal.com/v1")
		  	.contentType("application/x-www-form-urlencoded;charset=UTF-8") //always specify the encoding standard in this case the "charset=UTF-8"
		  	.header("Accept-Language","en_US")						// requested by api paypal
		  	.param("grant_type","client_credentials")				// requested by api paypal
		  	.auth().preemptive().basic("Myusername", "Mypassword").		//for Oauth2 is needed the "preemptive" that way it will send credentials before paypal requested		  	
		  when()
		  	.post("/oauth2/token"). //we use post
		  then()
		  	.log().all()			//the access token will be part of the response 
		  	.statusCode(200)
		  	.extract().path("access_token");  //extracting the access token
		 
		//in remaining requests we can use the access token we received, example create paypay invoice
		 given()
		  	.baseUri("https://api.sandbox.paypal.com/v2")
		  	.contentType("application/json")
		  	.auth().oauth2(accessToken).		  	
		  when()
		  	.post("/invoicing/generate-next-incoice-number"). 
		  then()
		  	.log().all()			 
		  	.statusCode(200);
 }

	
	
}
